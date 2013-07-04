/******************************************\
| A resource that stores an OpenGL Shader. |
|                                          |
| @author David Saxon                      |
\******************************************/

package nz.co.withfire.diecubesdie.resources.types;

import nz.co.withfire.diecubesdie.utilities.ResourceUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import android.content.Context;
import android.util.Log;

public class ShaderResource {

    //VARIABLES
    //the resource id of the shader
    private final int resourceID;
    //the shader type
    private final int shaderType;
    //the loaded shader
    private int shader = 0;
    //is true once the shader has been loaded
    private boolean loaded = false;
    
    //CONSTRUCTOR
    /**Creates a new shader resource
    @param shaderType the type of shader
    @param resourceID the resource id of the shader*/
    public ShaderResource(int shaderType, int resourceID) {
        
        this.resourceID = resourceID;
        this.shaderType = shaderType;
    }
    
    //PUBLIC METHODS
    /**Loads the shader, and compiles it with OpenGL
    @param context the android context*/
    public void load(final Context context) {
        
        //check that if it has already been loaded
        if (loaded) {
            
            return;
        }
        
        //get the shader from the file and compile it
        shader = ResourceUtil.compileShader(shaderType,
            ResourceUtil.resourceToString(context, resourceID));
        
        //the shader has successfully loaded
        loaded = true;
    }
    
    /**@return the loaded shader*/
    public int getShader() {
        
        //check that the shader has been loaded
        if (!loaded) {
            
            //report error
            throw new RuntimeException(
                    "Attempted to use an un-loaded shader");
        }
        
        return shader;
    }
    
    /**@return whether shader has been loaded*/
    public boolean isLoaded() {
        
        return loaded;
    }
}
