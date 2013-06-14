/*********************************\
| Engine to control level select. |
|                                 |
| @author David Saxon            |
\*********************************/

package nz.co.withfire.diecubesdie.engine.level_select;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.entities.gui.GUIText;
import nz.co.withfire.diecubesdie.entities.gui.Overlay;
import nz.co.withfire.diecubesdie.entities.level_select.Background;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.gesture.GestureWatcher;
import nz.co.withfire.diecubesdie.gesture.gestures.Gesture;
import nz.co.withfire.diecubesdie.gesture.gestures.Tap;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.renderer.text.Text;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.touch_control.TouchTracker;
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
    
    //the next state to move to once completed
    private Engine nextState = null;
    //is true once the menu is complete
    private boolean complete = false;
    
    //the level selector
    private LevelSelector selector;
    
    //the information text
    private GUIText infoText;
    
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

        //check to see what kind of gesture this is
        if (gesture instanceof Tap) {
            
            Log.v(ValuesUtil.TAG, "tap at " + ((Tap) gesture).getPos());
        }
    }
    
    /**Adds the initial objects*/
    private void addInitObjects() {
        
        //add the background
        entities.add(new Background(
            resources.getShape("level_select_background")));
        
        //create a new level selector
        selector = new LevelSelector(resources, entities);
        
        //add the title
        float titleSize = TransformationsUtil.scaleToScreen(0.13f);
        Vector2d titlePos = new Vector2d(
            (TransformationsUtil.getOpenGLDim().getX() +
            TransformationsUtil.scaleToScreen(0.02f)),
            -TransformationsUtil.getOpenGLDim().getY() - titleSize -
            TransformationsUtil.scaleToScreen(0.1f));
        entities.add(new GUIText(titlePos, titleSize,
            Text.Align.RIGHT, context.getString(R.string.level_select_title)));
        
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
        
        //the fade in overlay
        entities.add(new Overlay(resources.getShape("fade_overlay"),
            new Vector2d(), null, true));
    }
}
