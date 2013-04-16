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
import nz.co.withfire.diecubesdie.resources.types.TextureResource;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class ResourceManager {

    //ENUMERATORS
    //the different groups resources can be within
    public enum ResourceGroup {
        
        OMICRON,
        WITH_FIRE,
        START_UP,
        MENU
    }
    
    //VARIABLES
    //the android context
    private final Context context;
    
    //a map from labels to shader resources
    private Map<String, ShaderResource> shaders =
        new HashMap<String, ShaderResource>();
    //a map from labels to texture resources
    private Map<String, TextureResource> textures =
        new HashMap<String, TextureResource>();
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
    
    /**Loads all the textures into memory*/
    public void loadAllTextures() {
        
        //iterate over the map and load
        for (TextureResource t : textures.values()) {
            
            t.load(context);
        }
    }
    
    /**Loads all the textures within the group into memory
    @param group the group to load textures from*/
    public void loadTexturesFromGroup(ResourceGroup group) {
        
        //iterate over the map and check if it is within the group
        for (TextureResource t : textures.values()) {
            
            ResourceGroup[] groups = t.getGroups();
            
            //check if it is within the group
            for (int i = 0; i < groups.length; ++i) {
                
                if (groups[i] == group) {
                    
                    //load
                    t.load(context);
                    break;
                }
            }
        }
    }
    
    /**Loads all the models into memory*/
    public void loadAllModels() {
        
        //iterate over the map and load
        for (ModelResource r : models.values()) {
            
            r.load(context, this);
        }
    }
    
    /**Loads all the models within the group into memory
    @param group the group to load models from*/
    public void loadModelsFromGroup(ResourceGroup group) {
        
        //iterate over the map and check if it is within the group
        for (ModelResource m : models.values()) {
            
            ResourceGroup[] groups = m.getGroups();
            
            //check if it is within the group
            for (int i = 0; i < groups.length; ++i) {
                
                if (groups[i] == group) {
                    
                    //load
                    m.load(context, this);
                    break;
                }
            }
        }
    }
    
    /**Gets a shader from the resource map
    @param key the key of the shader
    @return the shader*/
    public int getShader(String key) {
        
        return shaders.get(key).getShader();
    }
    
    /**Gets a texture from the resource map
    @param key the key of the texture
    @return the texture*/
    public int getTexture(String key) {
        
        return textures.get(key).getTexture();
    }
    
    /**Gets a model from the resource map
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
        shaders.put("plain_texture_vertex",
                new ShaderResource(GLES20.GL_VERTEX_SHADER,
                R.raw.plain_texture_vertex_shader));
        shaders.put("texture_no_lighting_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.texture_no_lighting_fragment_shader));
        
        //add the textures
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        textures.put("omicron_splash",
            new TextureResource(R.drawable.omicron_splash,
            groups));
        }
        {
        ResourceGroup groups[] = {ResourceGroup.WITH_FIRE};
        textures.put("with_fire_splash",
            new TextureResource(R.drawable.with_fire_splash,
            groups));
        }
        
        //add the models
        {
        int ids[] = {R.drawable.square_plane};
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        models.put("omicron_splash", new ModelResource(ids, groups,
                "omicron_splash", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
    }
}
