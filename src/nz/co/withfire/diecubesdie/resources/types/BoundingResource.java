/****************************************\
| A resource that stores a bounding box. |
|                                        |
| @author David Saxon                   |
\****************************************/

package nz.co.withfire.diecubesdie.resources.types;

import android.content.Context;
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.ResourceUtil;

public class BoundingResource {

    //VARIABLES
    //the resource id of the bounding box
    private final int resourceID;
    //the groups the bounding is in
    private ResourceGroup groups[];
    //the bounding box
    private Bounding bounding;
    //is true once the resource has been loaded
    private boolean loaded = false;
    
    //CONSTRUCTOR
    /**Creates a new bounding box resource
    @param resourceID the resource id of the bounding box
    @param groups the groups the resource is in*/
    public BoundingResource(int resourceID, ResourceGroup groups[]) {
        
        this.resourceID = resourceID;
        this.groups = groups;
    }
    
    //PUBLIC METHODS
    /**Loads the bounding box
    @param context the android context*/
    public void load(final Context context) {
        
        //check that it already hasn't been loaded
        if (loaded) {
            
            return;
        }
        
        //load the bounding box
        bounding = ResourceUtil.loadBounding(context, resourceID);
        
        loaded = true;
    }
    
    /**@return the bounding box*/
    public Bounding getBounding() {
        
        return bounding;
    }
    
    /**@return the groups this bounding is within*/
    public ResourceGroup[] getGroups() {
        
        return groups;
    }
    
    /**@return whether the bounding box has been loaded or not*/
    public boolean isLoaded() {
        
        return loaded;
    }
}
