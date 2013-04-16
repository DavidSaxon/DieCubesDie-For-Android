/*******************************************\
| A Resource that stores a renderable shape |
|                                           |
| @author David Saxon                       |
\*******************************************/

package nz.co.withfire.diecubesdie.resources.types;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import android.util.Log;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.ResourceUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class ShapeResource {

    //VARIABLES
    //the shape
    private Shape shape;
    //the resource id for the shape .obj file
    private int resourceId;
    //the list of groups the shape is within
    private ResourceGroup[] groups;
    //the label of the texture to use for the shape
    private String tex;
    //the colour of the shape
    private Vector4d col;
    //the label of the vertex shader to use for the shape
    private String vertexShader;
    ///the label of the fragment shader to use for the shape
    private String fragmentShader;
    //is true once the shape has been loaded
    private boolean loaded = false;
    //is true if the shape is solid colour rather than textured
    private boolean colour;
    
    //CONSTRUCTORS
    /**Creates a new resource shape using textures
    @param resourceId the shape resource file
    @param groups the resource groups the shape is in
    @param tex the label of the texture to use for the shape
    @param vertexShader the label of the vertex shader to use for the shape
    @param fragmentShader the label of the fragmentshader to use for the shape*/
    public ShapeResource(int resourceId, ResourceGroup groups[],
        String tex, String vertexShader, String fragmentShader) {
        
        //initialise the variables
        shape = null;
        this.resourceId = resourceId;
        this.groups = groups;
        this.tex = tex;
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        
        //textured rather than coloured
        colour = false;
    }
    
    /**Creates a new resource shape using colours
    @param resourceId the resources to use in the shape
    @param groups the resource groups the shape is in
    @param tex the label of the texture to use for the shape
    @param vertexShader the label of the vertex shader to use for the shape
    @param fragmentShader the label of the fragmentshader to use for the shape*/
    public ShapeResource(int resourceId, ResourceGroup groups[],
        Vector4d col, String vertexShader, String fragmentShader) {
        
        //initialise the variables
        shape = null;
        this.resourceId = resourceId;
        this.groups = groups;
        this.col = col;
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        
        //coloured rather than textured
        colour = true;
    }
    
    //PUBLIC METHODS
    /**Loads the shape into memory
    @param context the android context
    @param resources the resource manager, so we can get the shaders*/
    public void load(final Context context, ResourceManager resources) {
        
        //check that if it has already been loaded
        if (loaded) {
            
            return;
        }
        
        //load a textured shape
        if (!colour) {
            
            shape = ResourceUtil.loadOBJ(context, resourceId,
                resources.getTexture(tex),
                resources.getShader(vertexShader),
                resources.getShader(fragmentShader));
        }
        //load a coloured shape
        else {
            
            shape = ResourceUtil.loadOBJ(context, resourceId, col,
                    resources.getShader(vertexShader),
                    resources.getShader(fragmentShader));
        }
        
        //the shape has successfully loaded
        loaded = true;
    }
    
    /**@return the loaded shape*/
    public Shape getShape() {
        
        //check that the shape has been loaded
        if (!loaded) {
            
            //report error
            throw new RuntimeException(
                    "Attempted using an un-loaded shape");
        }
        
        return shape;
    }
    
    /**@return the groups the shape is in*/
    public ResourceGroup[] getGroups() {
        
        return groups;
    }
    
    /**@return whether the shape has been loaded*/
    public boolean isLoaded() {
        
        return loaded;
    }
}
