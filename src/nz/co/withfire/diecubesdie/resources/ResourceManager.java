/*****************************************************************\
| Controls the loading of resources and stores resources in maps. |
|                                                                 |
| @author David Saxon                                             |
\*****************************************************************/

package nz.co.withfire.diecubesdie.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.resources.packs.DebugPack;
import nz.co.withfire.diecubesdie.resources.packs.GUIPack;
import nz.co.withfire.diecubesdie.resources.packs.LevelLoadPack;
import nz.co.withfire.diecubesdie.resources.packs.LevelPack;
import nz.co.withfire.diecubesdie.resources.packs.LevelSelectPack;
import nz.co.withfire.diecubesdie.resources.packs.MainMenuPack;
import nz.co.withfire.diecubesdie.resources.packs.PlainsPack;
import nz.co.withfire.diecubesdie.resources.packs.ShaderPack;
import nz.co.withfire.diecubesdie.resources.packs.StartUpPack;
import nz.co.withfire.diecubesdie.resources.types.BoundingResource;
import nz.co.withfire.diecubesdie.resources.types.LevelResource;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.ShaderResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class ResourceManager {

    //ENUMERATORS
    //the different groups resources can be within
    public enum ResourceGroup {
        
        DEBUG,
        GUI,
        OMICRON,
        WITH_FIRE,
        START_UP,
        MENU,
        LEVEL_SELECT,
        LEVEL_LOAD,
        LEVEL,
        CUBE,
        TERRIAN,
        PLAINS
    }
    
    //VARIABLES
    //the android context
    private final Context context;
    
    //a map from labels to shader resources
    private Map<String, ShaderResource> shaders =
        new HashMap<String, ShaderResource>();
    //a map from labels to bounding resources
    private Map<String, BoundingResource> boundings =
        new HashMap<String, BoundingResource>();
    //a map from labels to texture resources
    private Map<String, TextureResource> textures =
        new HashMap<String, TextureResource>();
    //a map from labels to shape resources
    private Map<String, ShapeResource> shapes =
            new HashMap<String, ShapeResource>();
    //a map from labels to level resources
    private Map<String, LevelResource> levels =
        new HashMap<String, LevelResource>();
    
    //CONSTRUCTOR
    public ResourceManager(Context context) {
        
        //store the context
        this.context = context;
        
        //initialise the resource maps
        init();
    }
    
    //PUBLIC METHODS
    /**Loads all of the resources into memory
    NUUUU DON'T DO IT!*/
    public void loadAll() {
        
        loadAllShaders();
        loadAllBoundings();
        loadAllTextures();
        loadAllShapes();
        loadAllLevels();
    }
    
    /**Loads all of the resources from the group
    #NOTE: this does not contain shaders or levels
    @param group the group to load from*/
    public void loadGroup(ResourceGroup group) {
        
        loadBoundingsFromGroup(group);
        loadTexturesFromGroup(group);
        loadShapesFromGroup(group);
    }
    
    /**Loads all of the shaders into memory*/
    public void loadAllShaders() {
        
        //iterate over the map and load
        for (ShaderResource s : shaders.values()) {
            
            s.load(context);
        }
    }
    
    /**Loads all the bounding boxes into memory*/
    public void loadAllBoundings() {
        
        for (BoundingResource b : boundings.values()) {
            
            b.load(context);
        }
    }
    
    /**Loads all the bounding boxes within the group into memory
    @param group the group to load the boundings from*/
    public void loadBoundingsFromGroup(ResourceGroup group) {
        
        for (BoundingResource b : boundings.values()) {
            
            ResourceGroup groups[] = b.getGroups();
            
            //check if it is within the group
            for (int i = 0; i < groups.length; ++i) {
                
                if (groups[i] == group) {
                    
                    //load
                    b.load(context);
                    break;
                }
            }
        }
    }
    
    /**Loads the bounding with the given key
    @param key the key of the bounding to load*/
    public void loadBounding(String key) {
        
        boundings.get(key).load(context);
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
    
    /**Loads the texture with the given key
    @param key the key of the texture to load*/
    public void loadTexture(String key) {
        
        textures.get(key).load(context);
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
    
    /**Loads the shape with the given key
    @param key the key of the shape to load*/
    public void loadShape(String key) {
        
        shapes.get(key).load(context, this);
    }
    
    /**Loads all the levels into memory
    OH CRAP YOU PROBABLY DON'T WANT TO DO THIS HOMIE*/
    public void loadAllLevels() {
        
        //iterate over the map and load
        for (LevelResource l : levels.values()) {
            
           l.load(context, this);
        }
    }
    
    /**Loads the level with the given key
    @param key the key of the level to load*/
    public void loadLevel(String key) {
        
        levels.get(key).load(context, this);
    }
    
    /**Gets a shader from the resource map
    @param key the key of the shader
    @return the shader*/
    public int getShader(String key) {
        
        return shaders.get(key).getShader();
    }
    
    /**Gets a bounding from the resource map
    @param key the key of the bounding
    @return the bounding*/
    public Bounding getBounding(String key) {
        
        return boundings.get(key).getBounding();
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
    
    /**Gets a level map from the resource map
    @param key the key of the level
    @return the level map*/
    public List<Entity> getLevelMap(String key) {
        
        return levels.get(key).getLevelMap();
    }
    
    /**Adds a shader resources
    @param key the shader key
    @param shader the shader resource to add*/
    public void add(String key, ShaderResource shader) {
        
        //check to make sure the map doesn't contain the key
        if (shaders.containsKey(key)) {
            
            Log.v(ValuesUtil.TAG, "Invalid shader key");
            throw new RuntimeException("Invalid shader key");
        }
        
        shaders.put(key, shader);
    }
    
    /**Adds a bounding resource
    @param key the resource key
    @param bounding the bounding resource*/
    public void add(String key, BoundingResource bounding) {
        
        //check to make sure the map doesn't contain the key
        if (boundings.containsKey(key)) {
            
            Log.v(ValuesUtil.TAG, "Invalid bounding key");
            throw new RuntimeException("Invalid bounding key");
        }
        
        boundings.put(key, bounding);
    }
    
    /**Adds a texture resource
    @param key the resource key
    @param texture the texture resource*/
    public void add(String key, TextureResource texture) {
        
        //check to make sure the map doesn't contain the key
        if (textures.containsKey(key)) {
            
            Log.v(ValuesUtil.TAG, "Invalid texture key");
            throw new RuntimeException("Invalid texture key");
        }
        
        textures.put(key, texture);
    }
    
    /**Adds a shape resource
    @param key the resource key
    @param shape the shape resource*/
    public void add(String key, ShapeResource shape) {
        
        //check to make sure the map doesn't contain the key
        if (shapes.containsKey(key)) {
            
            Log.v(ValuesUtil.TAG, "Invalid shape key");
            throw new RuntimeException("Invalid shape key");
        }
        
        shapes.put(key, shape);
    }
    
    /**Adds a level resource
    @param key the resource key
    @param level the level resource*/
    public void add(String key, LevelResource level) {
        
        //check to make sure the map doesn't contain the key
        if (levels.containsKey(key)) {
            
            Log.v(ValuesUtil.TAG, "Invalid level key");
            throw new RuntimeException("Invalid level key");
        }
        
        levels.put(key, level);
    }
    
    //PRIVATE METHODS
    /**Initialises all the resource maps, but does not load any resources*/
    private void init() {
        
        //build the resource packs
        ShaderPack.build(this);
        DebugPack.build(this);
        GUIPack.build(this);
        StartUpPack.build(this);
        MainMenuPack.build(this);
        LevelSelectPack.build(this);
        LevelLoadPack.build(this);
        LevelPack.build(this);
        PlainsPack.build(this);
    }
}
