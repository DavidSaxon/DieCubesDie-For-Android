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
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.resources.packs.DebugPack;
import nz.co.withfire.diecubesdie.resources.packs.GUIPack;
import nz.co.withfire.diecubesdie.resources.packs.ShaderPack;
import nz.co.withfire.diecubesdie.resources.packs.StartUpPack;
import nz.co.withfire.diecubesdie.resources.types.BoundingResource;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.ShaderResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

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
    
    //CONSTRUCTOR
    public ResourceManager(Context context) {
        
        //store the context
        this.context = context;
        
        //initialise the resource maps
        init();
    }
    
    //PUBLIC METHODS
    /**Loads all of the resources into memory*/
    public void loadAll() {
        
        loadAllShaders();
        loadAllBoundings();
        loadAllTextures();
        loadAllShapes();
    }
    
    /**Loads all of the resources from the group
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
    
    /**Adds a shader resources
    @param key the shader key
    @param shader the shader resource to add*/
    public void addShader(String key, ShaderResource shader) {
        
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
    public void addBounding(String key, BoundingResource bounding) {
        
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
    public void addTexture(String key, TextureResource texture) {
        
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
    public void addShape(String key, ShapeResource shape) {
        
        //check to make sure the map doesn't contain the key
        if (shapes.containsKey(key)) {
            
            Log.v(ValuesUtil.TAG, "Invalid shape key");
            throw new RuntimeException("Invalid shape key");
        }
        
        shapes.put(key, shape);
    }
    
    //PRIVATE METHODS
    /**Initialises all the resource maps, but does not load any resources*/
    private void init() {
        
        //build the resource packs
        ShaderPack.build(this);
        DebugPack.build(this);
        GUIPack.build(this);
        StartUpPack.build(this);
        
        //----------TEXTURES----------
        //MENU
        //Main
        //main title
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_title",
                new TextureResource(R.drawable.menu_main_title,
                groups));
        }
        //facebook button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_facebook_button",
                new TextureResource(R.drawable.menu_main_facebook,
                groups));
        }
        //google plus button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_googleplus_button",
                new TextureResource(R.drawable.menu_main_googleplus,
                groups));
        }
        //with fire button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_withfire_button",
                new TextureResource(R.drawable.menu_main_withfire,
                groups));
        }
        //Background
        //background cubs
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_cube",
                new TextureResource(R.drawable.menu_main_cube,
                groups));
        }
        //LEVEL SELECT
        //background
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            textures.put("level_select_background",
                new TextureResource(R.drawable.menu_levelselect_sky,
                groups));
        }
        //plains level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            textures.put("level_select_plains_1",
                new TextureResource(R.drawable.menu_levelselect_plains_1,
                groups));
        }
        //mountains level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            textures.put("level_select_mountains_1",
                new TextureResource(R.drawable.menu_levelselect_mountains_1,
                groups));
        }
        //city level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            textures.put("level_select_city_1",
                new TextureResource(R.drawable.menu_levelselect_city_1,
                groups));
        }
        //desert level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            textures.put("level_select_desert_1",
                new TextureResource(R.drawable.menu_levelselect_desert_1,
                groups));
        }
        //desert level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            textures.put("level_select_jungle_1",
                new TextureResource(R.drawable.menu_levelselect_jungle_1,
                groups));
        }
        //stronghold level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            textures.put("level_select_stronghold_1",
                new TextureResource(R.drawable.menu_levelselect_stronghold_1,
                groups));
        }
        //LEVEL
        //Cubes
        //wooden cube
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL,
                ResourceGroup.MENU, ResourceGroup.CUBE};
        textures.put("wooden_cube",
            new TextureResource(R.drawable.cube_wood,
            groups));
        }
        //Terrian
        //Planes
        //grass 1 tile
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL,
                ResourceGroup.MENU, ResourceGroup.TERRIAN,
                ResourceGroup.PLAINS};
        textures.put("plains_grass_tile",
            new TextureResource(R.drawable.terrain_plains_grass_1,
            groups));
        }
        
        
        
        
        //----------SHAPES----------
        
        //MENU
        //Main Menu
        //the title
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_title", new ShapeResource(
                R.raw.shape_menu_main_title, groups,
                "main_title", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //Buttons
        //facebook button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_menu_facebook_button", new ShapeResource(
                R.raw.shape_menu_social_button, groups,
                "main_menu_facebook_button", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //google plus button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_menu_googleplus_button", new ShapeResource(
                R.raw.shape_menu_social_button, groups,
                "main_menu_googleplus_button", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //with fire button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_menu_withfire_button", new ShapeResource(
                R.raw.shape_menu_withfire_button, groups,
                "main_menu_withfire_button", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //Background
        //the ground in the menu
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        shapes.put("menu_ground", new ShapeResource(
            R.raw.shape_menu_ground, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
        //the cube in the menu
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("menu_cube", new ShapeResource(
                R.raw.shape_menu_cube, groups,
                "main_menu_cube", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //Transitions
        //spikes
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        shapes.put("menu_spikes", new ShapeResource(
            R.raw.shape_menu_spikes, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
        
        //LEVEL SELECT
        //background
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            shapes.put("level_select_background", new ShapeResource(
                R.raw.shape_menu_levelselect_background, groups,
                "level_select_background", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //plains level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            shapes.put("level_select_plains_1", new ShapeResource(
                R.raw.shape_menu_levelselect_planetgrid, groups,
                "level_select_plains_1", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //mountains level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            shapes.put("level_select_mountains_1", new ShapeResource(
                R.raw.shape_menu_levelselect_planetgrid, groups,
                "level_select_mountains_1", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //city level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            shapes.put("level_select_city_1", new ShapeResource(
                R.raw.shape_menu_levelselect_planetgrid, groups,
                "level_select_city_1", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //desert level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            shapes.put("level_select_desert_1", new ShapeResource(
                R.raw.shape_menu_levelselect_planetgrid, groups,
                "level_select_desert_1", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //jungle level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            shapes.put("level_select_jungle_1", new ShapeResource(
                R.raw.shape_menu_levelselect_planetgrid, groups,
                "level_select_jungle_1", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //stronghold level 1
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
            shapes.put("level_select_stronghold_1", new ShapeResource(
                R.raw.shape_menu_levelselect_planetgrid, groups,
                "level_select_stronghold_1", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        
        //LEVEL
        //Cubes
        //wooden cube
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL,
                    ResourceGroup.CUBE};
            shapes.put("wooden_cube", new ShapeResource(
                R.raw.shape_cube_textured, groups,
                "wooden_cube", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //Terrain
        //Plains
        //plains grass tile
        {
            ResourceGroup groups[] = {ResourceGroup.LEVEL,
                    ResourceGroup.CUBE};
            shapes.put("plains_grass_tile", new ShapeResource(
                R.raw.shape_terrian_ground_tile, groups,
                "plains_grass_tile", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
    }
}
