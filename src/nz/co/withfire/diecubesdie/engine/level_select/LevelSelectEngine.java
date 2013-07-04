/*********************************\
| Engine to control level select. |
|                                 |
| @author David Saxon            |
\*********************************/

package nz.co.withfire.diecubesdie.engine.level_select;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.level.LevelEngine;
import nz.co.withfire.diecubesdie.engine.level_load.LevelLoadEngine;
import nz.co.withfire.diecubesdie.entities.gui.GUIText;
import nz.co.withfire.diecubesdie.entities.gui.Overlay;
import nz.co.withfire.diecubesdie.entities.gui.TapPoint;
import nz.co.withfire.diecubesdie.entities.gui.button.Button;
import nz.co.withfire.diecubesdie.entities.gui.button.TextImageButton;
import nz.co.withfire.diecubesdie.entities.level_select.Background;
import nz.co.withfire.diecubesdie.entities.level_select.LevelSelector;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.gesture.GestureWatcher;
import nz.co.withfire.diecubesdie.gesture.gestures.Gesture;
import nz.co.withfire.diecubesdie.gesture.gestures.Swipe;
import nz.co.withfire.diecubesdie.gesture.gestures.Tap;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.renderer.text.Text;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.CollisionUtil;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;
import android.content.Context;
import android.opengl.Matrix;
import android.util.Log;

public class LevelSelectEngine implements Engine {
    
    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager
    private ResourceManager resources;
    
    //the list of all entities
    private EntityList entities = new EntityList();
    
    //the gesture watcher
    private GestureWatcher gestureWatcher = new GestureWatcher();
    
    //the level manager
    private LevelManager manager;
    //the level selector
    private LevelSelector selector;
    
    //the next state to move to once completed
    private Engine nextState = null;
    //is true once the menu is complete
    private boolean complete = false;
    
    //the last swipe point
    private Vector2d lastSwipe;
    //the last rotation state of the world
    private boolean lastRotState = false;
    
    //the area text
    private GUIText areaText;
    //the information text
    private GUIText infoText;
    
    //the begin button
    private Button beginButton;
    
    //CONSTRUCTOR
    /**Creates a new level select engine
    @param context the android context
    @param resources the resources manager*/
    public LevelSelectEngine(Context context, ResourceManager resources) {
        
        //set variables
        this.context = context;
        this.resources = resources;
    }
    
    //PUBLIC METHODS
    @Override
    public void init() {
        
        //set the clear colour of the renderer
        GLRenderer.setClearColour(new Vector4d(0, 0, 0, 1));
        
        //load the level select resources
        resources.loadGroup(ResourceGroup.LEVEL_SELECT);
        
        addInitObjects();
    }
    
    @Override
    public boolean execute() {
        
        //update the level selector
        selector.update();
        
        //check if the we need to change the grid display state
        if (selector.isRotating() != lastRotState) {
            
            lastRotState = selector.isRotating();
            manager.displayGrid(!lastRotState);
        }
        
        //update the entities
        entities.update();
        
        //read the touch points
        processTouch();
        
        return complete;
    }

    @Override
    public void applyCamera(float[] viewMatrix) {

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(viewMatrix, 0, 0.0f, 0.0f, 2.0f);
    }

    @Override
    public void touchEvent(int event, int index, Vector2d touchPos) {

        gestureWatcher.inputEvent(event, index, touchPos);
    }

    @Override
    public EntityList getEntities() {

        return entities;
    }

    @Override
    public Engine nextState() {

        return nextState;
    }
    
    //PRIVATE METHODS
    /**Process touch input*/
    private void processTouch() {
        
        //update the gesture watcher
        Gesture gesture = gestureWatcher.getGesture();

        //remove the last swipe point (only a swipe can save it!)
        Vector2d lastSwipeStore = lastSwipe;
        lastSwipe = null;
        
        //check to see what kind of gesture this is
        if (gesture instanceof Tap) {
            
            Tap tap = (Tap) gesture;
            
            //create the the tap point
            TapPoint tapPoint;
            
            //add a debug tap point
            if (DebugUtil.DEBUG) {
                
                 tapPoint = new TapPoint(
                     resources.getShape("debug_touchpoint"),
                     tap.getPos(), resources.getBounding("gui_touch_point"));
            }
            //add a normal tap point
            else {
                
                tapPoint = new TapPoint(tap.getPos(),
                    resources.getBounding("gui_touch_point"));
            }
            
            //pass the tap point to the level manager
            manager.tapEvent(tapPoint);
            
            //check if the begin button is being pressed
            if (CollisionUtil.checkButtonCollision(tapPoint, beginButton)) {
                
                //TODO: make sure the button is not locked
                
                //go to the level
                nextState = new LevelLoadEngine(context,
                    resources, "test_level");
                complete = true;
            }
        }
        else if (gesture instanceof Swipe) {
            
            Swipe swipe = (Swipe) gesture;
            
            //make sure it is a horizontal swipe
            if (Math.abs(swipe.getPos().getX() -
                swipe.getOriginalPos().getX()) >
                Math.abs(swipe.getPos().getY() -
                swipe.getOriginalPos().getY())) {
            
                if (lastSwipeStore == null) {
                    
                    lastSwipeStore = new Vector2d(swipe.getPos());
                    
                    if (!selector.isRotating()) {
                        
                        //rotate the level selector
                        if (swipe.getPos().getX() -
                            swipe.getOriginalPos().getX() > 0) {
                            
                            selector.rotate(false);
                            manager.decrementArea();
                        }
                        else {
                            
                            selector.rotate(true);
                            manager.incrementArea();
                        }
                        
                        //set the new text for the area
                        areaText.setString(manager.getAreaName());
                    }
                }
            }
            
            //restore the last swipe if we havn't finished
            if (!swipe.finished()) {
                
                lastSwipe = swipe.getPos();
            }
            
        }
    }
    
    /**Adds the initial objects*/
    private void addInitObjects() {
        
        //add the background
        entities.add(new Background(
            resources.getShape("level_select_background")));
        
        //create the position of the world
        Vector2d worldPos = new Vector2d(
            -TransformationsUtil.getOpenGLDim().getX() / 1.45f, 0.0f);
        
        //create a new level manager
        manager = new LevelManager(context, resources, entities, worldPos);
        
        //create a new level selector
        selector = new LevelSelector(
                resources.getShape("level_select_world"), worldPos);
        entities.add(selector);
        
        //add the title
        float titleSize = TransformationsUtil.scaleToScreen(0.13f);
        Vector2d titlePos = new Vector2d(
            (TransformationsUtil.getOpenGLDim().getX() +
            TransformationsUtil.scaleToScreen(0.02f)),
            -TransformationsUtil.getOpenGLDim().getY() - titleSize -
            TransformationsUtil.scaleToScreen(0.1f));
        entities.add(new GUIText(titlePos, titleSize,
            Text.Align.RIGHT, context.getString(R.string.level_select_title)));
        
        //add the area text
        float areaSize = TransformationsUtil.scaleToScreen(0.085f);
        Vector2d areaPos = new Vector2d(
                titlePos.getX() +
                TransformationsUtil.scaleToScreen(0.05f),
                titlePos.getY() - areaSize -
                TransformationsUtil.scaleToScreen(0.1f));
        areaText = new GUIText(areaPos, areaSize, Text.Align.RIGHT,
                manager.getAreaName());
        entities.add(areaText);
        
        //add the info text
        float infoSize = TransformationsUtil.scaleToScreen(0.07f);
        Vector2d infoPos = new Vector2d(
            (TransformationsUtil.getOpenGLDim().getX() +
            TransformationsUtil.scaleToScreen(0.08f)),
            TransformationsUtil.scaleToScreen(0.25f));
        infoText = new GUIText(infoPos, infoSize, Text.Align.RIGHT,
            context.getString(R.string.level_select_info));
        infoText.setFragmentShader(
            resources.getShader("texture_quater_dim_fragment"));
        entities.add(infoText);
        
        //the begin button
        Vector2d beginPos = new Vector2d(
            TransformationsUtil.getOpenGLDim().getX() / 2.0f,
            (TransformationsUtil.getOpenGLDim().getY() +
            TransformationsUtil.scaleToScreen(0.25f)));
        beginButton = new TextImageButton(
            new Text(beginPos, 0.10f, Text.Align.CENTRE,
                context.getString(R.string.level_select_begin)),
            resources.getShape("text_image_button_bwt"),
            resources.getBounding("button_text_image"),
            ValuesUtil.ButtonType.BEGIN);
        entities.add(beginButton);
        
        //the fade in overlay
        entities.add(new Overlay(resources.getShape("fade_overlay"),
            new Vector2d(), null, true));
    }
}
