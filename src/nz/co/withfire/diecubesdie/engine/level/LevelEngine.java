package nz.co.withfire.diecubesdie.engine.level;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.level.cubes.WoodenCube;
import nz.co.withfire.diecubesdie.entities.level.terrian.Ground;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class LevelEngine implements Engine {

    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager
    private ResourceManager resources;
    
    //is true once the level is complete
    private boolean complete = false;
    
    //The list of all entities
    private List<Entity> entities = new ArrayList<Entity>();
    //subset of entites that contains the drawables
    private List<Drawable> drawables = new ArrayList<Drawable>();
    
    //CONSTRUCTOR
    /**!Constructs a new Level engine
    @param context the android context
    @param resources the resource manager to use*/
    public LevelEngine(Context context, ResourceManager resources) {
        
        //set the variables
        this.context = context;
        this.resources = resources;
    }
    
    //PUBLIC METHODS
    @Override
    public void init() {

        resources.loadTexturesFromGroup(ResourceGroup.LEVEL);
        resources.loadShapesFromGroup(ResourceGroup.LEVEL);
        
        //TESTING
        //add a wooden cube
        WoodenCube testWoodenCube = new WoodenCube(
            resources.getShape("wooden_cube"));
        entities.add(testWoodenCube);
        drawables.add(testWoodenCube);
        
        //add ground
        for (int i = -50; i < 4; ++i) {
            for (int j = -1; j < 4; ++j) {
                
                Vector2d gPos = new Vector2d(i, j);
                Ground g = new Ground(gPos,
                    resources.getShape("plains_grass_tile"));
                //entities.add(g);
                drawables.add(g);
            }
        }
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
    public Engine nextState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean shouldExit() {
        // TODO Auto-generated method stub
        return false;
    }

}
