package nz.co.withfire.diecubesdie.engine.level;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;

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
    }

    @Override
    public boolean execute() {
        

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
