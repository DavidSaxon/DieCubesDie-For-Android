package nz.co.withfire.diecubesdie.resources.types;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.LevelLoadUtil;
import nz.co.withfire.diecubesdie.utilities.ResourceUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class LevelResource {

    //VARIABLES
    //the resource id of the level file
    private int resourceId;
    //the list of groups this resource is within
    private ResourceGroup[] groups;
    
    //the level data stored as a string
    private String levelData;
    
    //the area of the level
    private ValuesUtil.LevelArea levelArea;
    //the dimensions of the level
    private Vector3d dim = new Vector3d();
    
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
    /**Loads the level into a string and stores the important data
    @param context the android context*/
    public void preload(final Context context) {
        
        //get the level data as a string
        levelData = ResourceUtil.resourceToString(context, resourceId);
        
        //get the level area
        levelArea = LevelLoadUtil.getArea(levelData);
        
        //get the dimensions
        dim.copy(LevelLoadUtil.getDim(levelData));
    }
    
    /**@return the area of the level*/
    public ValuesUtil.LevelArea getArea() {
        
        return levelArea;
    }
    
    /**@return the dimensions of the level*/
    public Vector3d getDim() {
        
        return dim;
    }
    
    /**Gets the map of the level as a 2d list of entity codes
    @return the level map*/
    public List<List<String>> getMap() {
        
        return LevelLoadUtil.getMap(levelData);
    }
    
    /**@return the groups the level is in*/
    public ResourceGroup[] getGroups() {
        
        return groups;
    }
}
