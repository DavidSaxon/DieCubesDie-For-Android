package nz.co.withfire.diecubesdie.engine.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.level.LevelEngine;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.level.terrian.Ground;
import nz.co.withfire.diecubesdie.entities.main_menu.back_ground.MenuCube;
import nz.co.withfire.diecubesdie.entities.main_menu.back_ground.MenuGround;
import nz.co.withfire.diecubesdie.entities.main_menu.main.MainMenuTitle;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class MainMenuEngine implements Engine {

    //VARIABLES
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
        GLRenderer.setClearColour(new Vector4d(0.25f, 0.25f, 0.5f, 1.0f));
        
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
    }

    @Override
    public boolean execute() {

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

}
