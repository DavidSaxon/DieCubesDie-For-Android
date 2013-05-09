package nz.co.withfire.diecubesdie.engine.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.level.LevelEngine;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.level.terrian.Ground;
import nz.co.withfire.diecubesdie.entities.main_menu.back_ground.MenuCube;
import nz.co.withfire.diecubesdie.entities.main_menu.back_ground.MenuGround;
import nz.co.withfire.diecubesdie.entities.main_menu.main.MainMenuButton;
import nz.co.withfire.diecubesdie.entities.main_menu.main.MainMenuTitle;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
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
    private List<Entity> entities = new ArrayList<Entity>();
    //subset of entites that contains the drawables
    private List<Drawable> drawables = new ArrayList<Drawable>();
    
    //is true once the menu is complete
    private boolean complete = false;
    
    //the back ground colour
    private Vector4d backgroundCol =
        new Vector4d(STD_COLOUR_VALUE, STD_COLOUR_VALUE, STD_COLOUR_VALUE, 1.0f);
    //the stage we are at changing the background colour
    private int colourChangeStage = 0;
    //the change in back ground colour
    private float colourChange = 0.0f;
    
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
        
        //add the needed entities
        //ground
        MenuGround ground = new MenuGround(resources.getShape("menu_ground"));
        entities.add(ground);
        drawables.add(ground);
        //cube
        MenuCube cube = new MenuCube(resources.getShape("menu_cube"));
        entities.add(cube);
        drawables.add(cube);
        //title
        MainMenuTitle title =
            new MainMenuTitle(resources.getShape("main_title"));
        entities.add(title);
        drawables.add(title);
        //play button
        MainMenuButton playButton = new MainMenuButton(
            resources.getShape("main_menu_play_button"),
            new Vector2d(-1.2f, 0.75f));
        entities.add(playButton);
        drawables.add(playButton);
        //store button
        MainMenuButton storeButton = new MainMenuButton(
                resources.getShape("main_menu_store_button"),
                new Vector2d(-1.2f, 0.5f));
        entities.add(storeButton);
        drawables.add(storeButton);
        //options button
        MainMenuButton optionsButton = new MainMenuButton(
                resources.getShape("main_menu_options_button"),
                new Vector2d(-1.2f, 0.25f));
        entities.add(optionsButton);
        drawables.add(optionsButton);
        //more button
        MainMenuButton moreButton = new MainMenuButton(
                resources.getShape("main_menu_more_button"),
                new Vector2d(-1.2f, 0.0f));
        entities.add(moreButton);
        drawables.add(moreButton);
        //exit button
        MainMenuButton exitButton = new MainMenuButton(
                resources.getShape("main_menu_exit_button"),
                new Vector2d(-1.2f, -0.25f));
        entities.add(exitButton);
        drawables.add(exitButton);
        //facebook button
        MainMenuButton facebookButton = new MainMenuButton(
                resources.getShape("main_menu_facebook_button"),
                new Vector2d(1.4f, -0.7f));
        entities.add(facebookButton);
        drawables.add(facebookButton);
        //google plus button
        MainMenuButton googleplusButton = new MainMenuButton(
            resources.getShape("main_menu_googleplus_button"),
            new Vector2d(1.0f, -0.7f));
        entities.add(googleplusButton);
        drawables.add(googleplusButton);
        //with fire button
        MainMenuButton withfireButton = new MainMenuButton(
            resources.getShape("main_menu_withfire_button"),
            new Vector2d(-1.0f, -0.75f));
        entities.add(withfireButton);
        drawables.add(withfireButton);
    }

    @Override
    public boolean execute() {

        //change the background colour
        changeColour();
        
        //update the entities
        for (Entity e : entities) {
            
            e.update();
        }
        
        return complete;
    }

    @Override
    public List<Drawable> getDrawables() {

        return drawables;
    }

    @Override
    public void applyCamera(float[] viewMatrix) {

        //do nothing
    }

    @Override
    public Engine nextState() {

        return new LevelEngine(context, resources);
    }

    @Override
    public boolean shouldExit() {

        return false;
    }

    //PRIVATE METHODS
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
    
}
