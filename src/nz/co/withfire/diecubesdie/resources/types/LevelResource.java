package nz.co.withfire.diecubesdie.resources.types;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;

public class LevelResource {

    //VARIABLES
    //the resource id of the level file
    private int resourceId;
    //the list of groups this resource is within
    private ResourceGroup[] groups;
    //is true once the level has been loaded
    private boolean loaded = false;
    
    //a list of entities for the map of the level
    private List<Entity> levelMap = new ArrayList<Entity>();
    
    //CONSTRUCTOR
    /**Creates a new level resource
    @param resourceId the level resource file
    @param groups the groups the level is within*/
    public LevelResource(int resourceId, ResourceGroup groups[]) {
        
        //initialise the variables
        this.resourceId = resourceId;
        this.groups = groups;
    }
    
    //PUBLIC METHODS
    /**Loads the level into memory
    @param context the android context
    @param resources the resource manager*/
    public void load(final Context context, ResourceManager resources) {
        
        //TODO: read from the file and build the entities
        //Probably should use a function to do this
        
        //the level has successfully loaded
        loaded = true;
    }
    
    /**@return the level map*/
    public List<Entity> getLevelMap() {
        
        //check that the level has been loaded
        if (!loaded) {
            
            //report error
            throw new RuntimeException(
                    "Attempted to use an un-loaded level map");
        }
        
        return levelMap;
    }
    
    /**@return the groups the level is in*/
    public ResourceGroup[] getGroups() {
        
        return groups;
    }
    
    /**@return whether the shape has been loaded*/
    public boolean isLoaded() {
        
        return loaded;
    }
}
