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
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.ShaderResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

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
    //a map from labels to shape resources
    private Map<String, ShapeResource> shapes =
            new HashMap<String, ShapeResource>();
    
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
    
    /**Loads all the shapes into memory*/
    public void loadAllShapes() {
        
        //iterate over the map and load
        for (ShapeResource r : shapes.values()) {
            
            r.load(context, this);
        }
    }
    
    /**Loads all the shapes within the group into memory
    @param group the group to load shapes from*/
    public void loadShapesFromGroup(ResourceGroup group) {
        
        //iterate over the map and check if it is within the group
        for (ShapeResource s : shapes.values()) {
            
            ResourceGroup[] groups = s.getGroups();
            
            //check if it is within the group
            for (int i = 0; i < groups.length; ++i) {
                
                if (groups[i] == group) {
                    
                    //load
                    s.load(context, this);
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
    
    /**Gets a shape from the resource map
    @param key the key of the shape
    @return the shape*/
    public Shape getShape(String key) {
        
        return shapes.get(key).getShape();
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
        
        //ADD THE TEXTURES
        //omicron splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        textures.put("omicron_splash",
            new TextureResource(R.drawable.omicron_splash,
            groups));
        }
        //with fire splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.WITH_FIRE};
        textures.put("with_fire_splash",
            new TextureResource(R.drawable.with_fire_splash,
            groups));
        }
        
        //ADD THE MODELS
        //omicron splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        shapes.put("omicron_splash", new ShapeResource(
            R.drawable.square_plane_textured, groups,
            "omicron_splash", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //with fire splash screen
        {
            ResourceGroup groups[] = {ResourceGroup.WITH_FIRE};
            shapes.put("with_fire_splash", new ShapeResource(
                R.drawable.square_plane_textured, groups,
                "with_fire_splash", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //splash screen fader
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON,
            ResourceGroup.WITH_FIRE};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        shapes.put("splash_fader", new ShapeResource(
            R.drawable.square_plane_coloured, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
    }
}