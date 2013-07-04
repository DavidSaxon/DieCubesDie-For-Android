/*******************************************\
| A resource that stores an OpenGL texture. |
|                                           |
| @author David Saxon                       |
\*******************************************/

package nz.co.withfire.diecubesdie.resources.types;

import android.content.Context;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.ResourceUtil;

public class TextureResource {

    //VARIABLES
    //the resource id of the texture
    private int resourceId;
    //the groups the texture is in
    private ResourceGroup groups[];
    //the texture
    private int texture;
    //is true once the the texture has been loaded
    private boolean loaded = false;
    
    //CONSTRUCTOR
    /**Creates a new texture resource
    @param resourceId the resource id of the texture
    @param groups the groups the texture is in*/
    public TextureResource(int resourceId, ResourceGroup[] groups) {
        
        //initialise variables
        this.resourceId = resourceId;
        this.groups = groups;
    }
    
    //PUBLIC METHODS
    /**Loads the texture into memory
    @param context the OpenGL context*/
    public void load(final Context context) {
        
        //check that if it has already been loaded
        if (loaded) {
            
            return;
        }
        
        //load the texture
        texture = ResourceUtil.loadPNG(context, resourceId);
        
        //the texture has successfully loaded
        loaded = true;
    }
    
    /**@return the loaded texture*/
    public int getTexture() {
        
        //check that the model has been loaded
        if (!loaded) {
            
            //report error
            throw new RuntimeException(
                    "Attempted to use an un-loaded texture");
        }
        
        return texture;
    }
    
    /**@return the groups the texture is contained within*/
    public ResourceGroup[] getGroups() {
        
        return groups;
    }
    
    /**@return whether the texture has been loaded*/
    public boolean isLoaded() {
        
        return loaded;
    }
}
