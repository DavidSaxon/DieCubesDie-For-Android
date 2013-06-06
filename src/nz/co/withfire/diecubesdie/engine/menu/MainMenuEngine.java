package nz.co.withfire.diecubesdie.engine.menu;

import java.util.ArrayList;
import java.util.List;

import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.link.RevMobLink;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.level.LevelEngine;
import nz.co.withfire.diecubesdie.entities.gui.Button;
import nz.co.withfire.diecubesdie.entities.gui.Overlay;
import nz.co.withfire.diecubesdie.entities.gui.TouchPoint;
import nz.co.withfire.diecubesdie.entities.main_menu.back_ground.MenuCube;
import nz.co.withfire.diecubesdie.entities.main_menu.back_ground.MenuGround;
import nz.co.withfire.diecubesdie.entities.main_menu.main.MainMenuTitle;
import nz.co.withfire.diecubesdie.entities.main_menu.transitions.MenuSpikes;
import nz.co.withfire.diecubesdie.entities.main_menu.transitions.MenuTransition;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.utilities.CollisionUtil;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class MainMenuEngine implements Engine {
    
    //VARIABLES
    //the standard rgb values for the background colour
    private final float STD_COLOUR_VALUE = 0.2f;
    //the limit of background colour change
    private final float COLOUR_CHANGE_LIMIT = 0.6f;
    //the rate at which the colour changes
    private final float COLOUR_CHANGE_RATE = 0.005f;
    
    //the android context
    private final Context context;
    
    //the resource manager
    private ResourceManager resources;
    
    //The list of all entities
    private EntityList entities = new EntityList();
    
    //the rev mob link
    public static RevMobLink link = null;
    
    //the next state to move to once completed
    private Engine nextState = null;
    //is true once the menu is complete
    private boolean complete = false;
    //is true if the menu is paused
    private boolean paused = false;
    //is true if the app has just been resumed
    public static boolean resume = false;
    
    //is true if a touch point should be added
    private volatile boolean addTouchPoint = false;
    //the co-ordinates of the current touch point
    private Vector2d touchPos = new Vector2d();
    
    //the back ground colour
    private Vector4d backgroundCol =
        new Vector4d(STD_COLOUR_VALUE, STD_COLOUR_VALUE, STD_COLOUR_VALUE, 1.0f);
    //the stage we are at changing the background colour
    private int colourChangeStage = 0;
    //the change in back ground colour
    private float colourChange = 0.0f;
    
    //a list of all the buttons
    private ArrayList<Button> buttons =
        new ArrayList<Button>();
    //the overlay when paused
    private Overlay pauseOverlay;
    //the current transition
    private MenuTransition transition = null;
    
    //CONSTRUCTOR
    /**Creates a new main menu engine
    @param context the android context
    @param resources the resource manager*/
    public MainMenuEngine(Context context, ResourceManager resources) {
        
        this.context = context;
        this.resources = resources;
    }
    
    @Override
    public void init() {

        //set the colour of the renderer
        GLRenderer.setClearColour(backgroundCol);
        
        //create the background entities
        initBackground();
        
        //add the main menu entities
        initMainMenu();
    }

    @Override
    public boolean execute() {
        
        //resume the menu
        if (resume) {
            
            //remove the overlay
            entities.remove(pauseOverlay);
            
            //unpause
            paused = false;
            
            resume = false;
        }
        
        if (!paused) {
        
            //change the background colour
            changeColour();
            
            //process any touch events
            processTouch();
            
            //check if there is a transition running and if it's done
            if (transition != null && transition.complete()) {
                
                complete = true;
            }
            
            //update the entities
            entities.update();
        }
        
        return complete;
    }

    @Override
    public void applyCamera(float[] viewMatrix) {

        //do nothing
    }
    
    @Override
    public void touchEvent(int event, Vector2d touchPos) {
        
        //if we are paused ignore
        if (!paused) {
        
            switch (event) {
                
                //the user has pressed down
                case MotionEvent.ACTION_DOWN: {
                    
                    //request for a touch point to be added add a
                    //touch point to the menu
                    addTouchPoint = true;
                    
                    //set the co-ordantes from the event
                    this.touchPos.copy(touchPos);
                    break;
                }
            }
        }
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
    /**Process a touch point
    @param viewMatrix the view Matrix*/
    void processTouch() {
        
        //add a touch point if we need too
        if (addTouchPoint) {
            
            //add the touch point
            TouchPoint touchPoint;
            
            //add a debug touch point
            if (DebugUtil.DEBUG) {
                
                 touchPoint = new TouchPoint(
                     resources.getShape("debug_touchpoint"),
                     touchPos, resources.getBounding("gui_touch_point"));
            }
            //add a normal touch point
            else {
                
                touchPoint = new TouchPoint(touchPos,
                    resources.getBounding("gui_touch_point"));
            }
            
            //process any button presses
            processButtonPress(
                CollisionUtil.checkButtonCollisions(touchPoint, buttons));
            
            addTouchPoint = false;
        }
    }
    
    /**Processes buttons presses
    @param type the button type that has been pressed*/
    private void processButtonPress(ValuesUtil.ButtonType type) {
        
        switch(type) {
        
            case PLAY: {
                
                //got to the level
                nextState = new LevelEngine(context, resources);
                //add the spike transition
                transition = new MenuSpikes(resources.getShape("menu_spikes"));
                entities.add(transition);
                break;
            }
            case MORE: {
                
                //pause the menu
                paused = true;
                
                //add an overlay
                pauseOverlay = new Overlay(resources.getShape("overlay"),
                    new Vector2d(), "PLEASE WAIT", false);
                entities.add(pauseOverlay);
                
                //open the rev mob link
                link.open();
                break;
            }
        }
    }

    /**Updates the background colour*/
    private void changeColour() {
        
        colourChange += COLOUR_CHANGE_RATE * FpsManager.getTimeScale();
        
        if (colourChange >= COLOUR_CHANGE_LIMIT) {
            
            colourChange = 0.0f;
            
            colourChangeStage = (colourChangeStage + 1) % 3;
        }
        
        switch (colourChangeStage) {
        
            case 0: {
                
                backgroundCol.setX(STD_COLOUR_VALUE + colourChange);
                backgroundCol.setZ(STD_COLOUR_VALUE +
                    COLOUR_CHANGE_LIMIT - colourChange);
                break; 
            }
            case 1: {
                
                backgroundCol.setY(STD_COLOUR_VALUE + colourChange);
                backgroundCol.setX(STD_COLOUR_VALUE +
                    COLOUR_CHANGE_LIMIT - colourChange);
                break; 
            }
            case 2: {
                
                backgroundCol.setZ(STD_COLOUR_VALUE + colourChange);
                backgroundCol.setY(STD_COLOUR_VALUE +
                    COLOUR_CHANGE_LIMIT - colourChange);
                break; 
            }
        }
        
            //set the colour of the renderer
            GLRenderer.setClearColour(backgroundCol);   
    }
    
    /**Creates and adds the entities needed for the background*/
    private void initBackground() {
        
        //ground
        MenuGround ground = new MenuGround(resources.getShape("menu_ground"));
        entities.add(ground);
        //cube
        MenuCube cube = new MenuCube(resources.getShape("menu_cube"));
        entities.add(cube);
    }
    
    /**Creates and adds the entities needed for the main menu*/
    private void initMainMenu() {
        
        //title
        MainMenuTitle title =
            new MainMenuTitle(resources.getShape("main_title"));
        entities.add(title);
        
        //calculate the x position of buttons
        float buttonXPos = TransformationsUtil.getOpenGLDim().getX() + 0.6f;
        
        //play button
        Button playButton = new Button(
            resources.getShape("main_menu_play_button"),
            new Vector2d(buttonXPos, 0.65f),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.PLAY);
        entities.add(playButton);
        buttons.add(playButton);
        //store button
        Button storeButton = new Button(
            resources.getShape("main_menu_store_button"),
            new Vector2d(buttonXPos, 0.4f),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.STORE);
        entities.add(storeButton);
        buttons.add(storeButton);
        //options button
        Button optionsButton = new Button(
            resources.getShape("main_menu_options_button"),
            new Vector2d(buttonXPos, 0.15f),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.OPTIONS);
        entities.add(optionsButton);
        buttons.add(optionsButton);
        //more button
        Button moreButton = new Button(
            resources.getShape("main_menu_more_button"),
            new Vector2d(buttonXPos, -0.10f),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.MORE);
        entities.add(moreButton);
        buttons.add(moreButton);
        
        //calculate the social buttons base x position
        float socialXPos = -TransformationsUtil.getOpenGLDim().getX() - 0.37f;
        
        //facebook button
        Button facebookButton = new Button(
            resources.getShape("main_menu_facebook_button"),
            new Vector2d(socialXPos, -0.7f),
            resources.getBounding("menu_social_button"),
            ValuesUtil.ButtonType.FACEBOOK);
        entities.add(facebookButton);
        buttons.add(facebookButton);
        //google plus button
        Button googleplusButton = new Button(
            resources.getShape("main_menu_googleplus_button"),
            new Vector2d(socialXPos - 0.4f, -0.7f),
            resources.getBounding("menu_social_button"),
            ValuesUtil.ButtonType.GOOGLEPLUS);
        entities.add(googleplusButton);
        buttons.add(googleplusButton);
        //with fire button
        Button withfireButton = new Button(
            resources.getShape("main_menu_withfire_button"),
            new Vector2d(-socialXPos + 0.4f, -0.7f),
            resources.getBounding("menu_social_button"),
            ValuesUtil.ButtonType.WITH_FIRE);
        entities.add(withfireButton);
        buttons.add(withfireButton);
        //the fade in overlay
        entities.add(new Overlay(resources.getShape("fade_overlay"),
            new Vector2d(), null, true));
    }
}
