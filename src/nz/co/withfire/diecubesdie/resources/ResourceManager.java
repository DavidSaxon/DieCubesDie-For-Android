/*****************************************************************\
| Controls the loading of resources and stores resources in maps. |
|                                                                 |
| @author David Saxon                                             |
\*****************************************************************/

package nz.co.withfire.diecubesdie.resources;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.renderer.renderable.model.Model;
import nz.co.withfire.diecubesdie.resources.types.ModelResource;
import nz.co.withfire.diecubesdie.resources.types.ShaderResource;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class ResourceManager {

    //ENUMERATORS
    //the different groups resources can be within
    public enum ResourceGroup {
        
        OMICRON,
        START_UP,
        MENU
    }
    
    //VARIABLES
    //the android context
    private final Context context;
    
    //a map from labels to shader resources
    private Map<String, ShaderResource> shaders =
        new HashMap<String, ShaderResource>();
    //a map from labels to model resources
    private Map<String, ModelResource> models =
            new HashMap<String, ModelResource>();
    
    //CONSTRUCTOR
    public ResourceManager(Context context) {
        
        //store the context
        this.context = context;
        
        //initialise the resource maps
        init();
    }
    
    //PUBLIC METHODS
    /**Loads all of the shaders into memory*/
    public void loadAllShaders() {
        
        //iterate over the map and load
        for (ShaderResource s : shaders.values()) {
            
            s.load(context);
        }
    }
    
    /**Loads all the models into memory*/
    public void loadAllModels() {
        
        //iterate over the map and load
        for (ModelResource r : models.values()) {
            
            r.load(context, this);
        }
    }
    
    /**Gets a shader from the resource map
    @param key the key of the shader
    @return the shader*/
    public int getShader(String key) {
        
        return shaders.get(key).getShader();
    }
    
    /**Gets a model from the resouce map
    @param key the key of the model
    @return the model*/
    public Model getModel(String key) {
        
        return models.get(key).getModel();
    }
    
    //PRIVATE METHODS
    /**Initialises all the resource maps, but does not load any resources*/
    private void init() {
        
        //add the shaders
        shaders.put("plain_colour_vertex",
            new ShaderResource(GLES20.GL_VERTEX_SHADER,
            R.raw.plain_colour_vertex_shader));
        shaders.put("colour_no_lighting_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.colour_no_lighting_fragment_shader));
        
        //add the models
        {
        int ids[] = {R.drawable.omicron_intro_plane};
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        models.put("test_plane", new ModelResource(ids, groups,
                "plain_colour_vertex", "colour_no_lighting_fragment"));
        }
    }
}
