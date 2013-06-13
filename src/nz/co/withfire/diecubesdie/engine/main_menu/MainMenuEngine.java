package nz.co.withfire.diecubesdie.engine.main_menu;

import java.util.ArrayList;
import java.util.List;

import com.revmob.RevMob;
import com.revmob.RevMobAdsListener;
import com.revmob.ads.link.RevMobLink;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.level.LevelEngine;
import nz.co.withfire.diecubesdie.engine.level_select.LevelSelectEngine;
import nz.co.withfire.diecubesdie.entities.gui.Overlay;
import nz.co.withfire.diecubesdie.entities.gui.TapPoint;
import nz.co.withfire.diecubesdie.entities.gui.button.Button;
import nz.co.withfire.diecubesdie.entities.gui.button.ImageButton;
import nz.co.withfire.diecubesdie.entities.gui.button.TextButton;
import nz.co.withfire.diecubesdie.entities.main_menu.back_ground.MenuCube;
import nz.co.withfire.diecubesdie.entities.main_menu.back_ground.MenuGround;
import nz.co.withfire.diecubesdie.entities.main_menu.main.MainMenuTitle;
import nz.co.withfire.diecubesdie.entities.main_menu.transitions.MenuSpikes;
import nz.co.withfire.diecubesdie.entities.main_menu.transitions.MenuTransition;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.renderer.text.Text;
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
    
    //the amount of time a connection time out message is displayed for
    private final int TIME_OUT_DISPLAY_DURATION = 3000;
    
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
    //is true if we should complete next cycle
    private boolean completeNext = false;
    //is true if the menu is paused
    private boolean paused = false;
    //is true if the app has just been resumed
    public static boolean resume = false;
    
    //is true if we are attempting an internet connection
    private boolean attemptingConnection = false;
    //the time we started the current connection attempt
    private long connectionTime = 0;
    //is true if we are displaying the connection timed out message
    private boolean timedOut = false;
    //the when the connection display message was first being displayed
    private long timeOutStart = 0;
    
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
        
        //engine is complete
        if (completeNext) {
            
            complete = true;
        }
        
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
            if (transition != null && transition.complete()
                && !completeNext) {
                
                //add the loading overlay
                entities.add(new Overlay(resources.getShape("overlay"),
                        new Vector2d(), "LOADING", false));
                
                completeNext = true;
            }
            
            //update the entities
            entities.update();
        }
        else {
            
            if (attemptingConnection) {
                
                //check for time out
                if (connectionTime + ValuesUtil.TIME_OUT
                    < SystemClock.uptimeMillis()) {
                    
                    //time out the connection
                    timeOut();
                }
            }
            else if (timedOut && timeOutStart +
                TIME_OUT_DISPLAY_DURATION < SystemClock.uptimeMillis()) {
                
                //end the time out
                endTimeOut();
            }
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
            TapPoint touchPoint;
            
            //add a debug touch point
            if (DebugUtil.DEBUG) {
                
                 touchPoint = new TapPoint(
                     resources.getShape("debug_touchpoint"),
                     touchPos, resources.getBounding("gui_touch_point"));
            }
            //add a normal touch point
            else {
                
                touchPoint = new TapPoint(touchPos,
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
                nextState = new LevelSelectEngine(context, resources);
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
                    new Vector2d(),
                    context.getString(R.string.connection_message), false);
                entities.add(pauseOverlay);
                
                //begin a connection attempt
                beginConnectionAttempt();
                
                //open the rev mob link
                link.open();
                break;
            }
        }
    }
    
    /**Begins a connection attempt*/
    private void beginConnectionAttempt() {
        
        connectionTime = SystemClock.uptimeMillis();
        attemptingConnection = true;
    }
    
    /**Times out an attempted connection*/
    private void timeOut() {
        
        pauseOverlay.setText(
            context.getString(R.string.time_out_message));
        timedOut = true;
        timeOutStart = SystemClock.uptimeMillis();
        attemptingConnection = false;
    }
    
    /**Ends a time out*/
    private void endTimeOut() {
        
        entities.remove(pauseOverlay);
        timedOut = false;
        paused = false;
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
        float buttonXPos = TransformationsUtil.getOpenGLDim().getX() +
            TransformationsUtil.scaleToScreen(0.6f);
        
        //play button
        TextButton playButton = new TextButton(
            new Text(new Vector2d(buttonXPos,
                TransformationsUtil.scaleToScreen(0.80f)),
                TransformationsUtil.scaleToScreen(0.12f),
                Text.Align.CENTRE,
                context.getString(R.string.play_button)),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.PLAY);
        entities.add(playButton);
        buttons.add(playButton);
        //store button
        TextButton storeButton = new TextButton(
            new Text(new Vector2d(buttonXPos,
                TransformationsUtil.scaleToScreen(0.55f)), 
                TransformationsUtil.scaleToScreen(0.12f),
                Text.Align.CENTRE,
                context.getString(R.string.store_button)),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.STORE);
        entities.add(storeButton);
        buttons.add(storeButton);
        //progress button
        TextButton progessButton = new TextButton(
            new Text(new Vector2d(buttonXPos,
                TransformationsUtil.scaleToScreen(0.30f)), 
                TransformationsUtil.scaleToScreen(0.12f),
                Text.Align.CENTRE,
                context.getString(R.string.progress_button)),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.PROGRESS);
        entities.add(progessButton);
        buttons.add(progessButton);
        //options button
        TextButton optionsButton = new TextButton(
            new Text(new Vector2d(buttonXPos,
                TransformationsUtil.scaleToScreen(0.05f)), 
                TransformationsUtil.scaleToScreen(0.12f),
                Text.Align.CENTRE,
                context.getString(R.string.options_button)),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.OPTIONS);
        entities.add(optionsButton);
        buttons.add(optionsButton);
        //more button
        TextButton moreButton = new TextButton(
            new Text(new Vector2d(buttonXPos,
                TransformationsUtil.scaleToScreen(-0.20f)),
                TransformationsUtil.scaleToScreen(0.12f),
                Text.Align.CENTRE,
                context.getString(R.string.more_button)),
            resources.getBounding("main_menu_button"),
            ValuesUtil.ButtonType.MORE);
        entities.add(moreButton);
        buttons.add(moreButton);
        
        //calculate the social buttons base x position
        float socialXPos = -TransformationsUtil.getOpenGLDim().getX() -
            TransformationsUtil.scaleToScreen(0.37f);
        
        //facebook button
        ImageButton facebookButton = new ImageButton(
            resources.getShape("main_menu_facebook_button"),
            new Vector2d(socialXPos,
            TransformationsUtil.scaleToScreen(-0.7f)),
            resources.getBounding("menu_social_button"),
            ValuesUtil.ButtonType.FACEBOOK);
        entities.add(facebookButton);
        buttons.add(facebookButton);
        //google plus button
        ImageButton googleplusButton = new ImageButton(
            resources.getShape("main_menu_googleplus_button"),
            new Vector2d(socialXPos -
            TransformationsUtil.scaleToScreen(0.4f),
            TransformationsUtil.scaleToScreen(-0.7f)),
            resources.getBounding("menu_social_button"),
            ValuesUtil.ButtonType.GOOGLEPLUS);
        entities.add(googleplusButton);
        buttons.add(googleplusButton);
        //with fire button
        ImageButton withfireButton = new ImageButton(
            resources.getShape("main_menu_withfire_button"),
            new Vector2d(-socialXPos +
            TransformationsUtil.scaleToScreen(0.4f),
            TransformationsUtil.scaleToScreen(-0.7f)),
            resources.getBounding("menu_social_button"),
            ValuesUtil.ButtonType.WITH_FIRE);
        entities.add(withfireButton);
        buttons.add(withfireButton);
        //the fade in overlay
        entities.add(new Overlay(resources.getShape("fade_overlay"),
            new Vector2d(), null, true));
    }
}
