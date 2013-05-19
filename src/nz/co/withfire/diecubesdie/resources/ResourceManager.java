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
        ALL,
        OMICRON,
        WITH_FIRE,
        START_UP,
        MENU,
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
    
    //PRIVATE METHODS
    /**Initialises all the resource maps, but does not load any resources*/
    private void init() {
        
        //----------SHADERS----------
        shaders.put("plain_colour_vertex",
            new ShaderResource(GLES20.GL_VERTEX_SHADER,
            R.raw.shader_vertex_plain_colour));
        shaders.put("colour_no_lighting_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_colour_no_lighting));
        shaders.put("plain_texture_vertex",
                new ShaderResource(GLES20.GL_VERTEX_SHADER,
                R.raw.shader_vertex_plain_texture));
        shaders.put("texture_no_lighting_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_texture_no_lighting));
        
        
        
        
        
        //----------BOUNDING BOXES----------
        //GUI
        //touch point bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.ALL};
        boundings.put("gui_touch_point",
            new BoundingResource(R.raw.bounding_menu_main_button,
            groups));
        }
        
        //MENU
        //main menu button bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        boundings.put("main_menu_button",
            new BoundingResource(R.raw.bounding_menu_main_button,
            groups));
        }
        //social button bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        boundings.put("menu_social_button",
            new BoundingResource(R.raw.bounding_menu_social_button,
            groups));
        }
        
        
        
        
        
        
        //----------TEXTURES----------
        
        //START UP
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
        //presents splash screen
        {
            ResourceGroup groups[] = {ResourceGroup.MENU,
                    ResourceGroup.START_UP};
            textures.put("presents_splash",
                new TextureResource(R.drawable.splash_presents,
                groups));
        }
        
        //MENU
        //Main
        //main title
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_title",
                new TextureResource(R.drawable.menu_main_title,
                groups));
        }
        //play button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_play_button",
                new TextureResource(R.drawable.menu_main_play,
                groups));
        }
        //store button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_store_button",
                new TextureResource(R.drawable.menu_main_store,
                groups));
        }
        //options button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_options_button",
                new TextureResource(R.drawable.menu_main_options,
                groups));
        }
        //more button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_more_button",
                new TextureResource(R.drawable.menu_main_more,
                groups));
        }
        //exit button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            textures.put("main_menu_exit_button",
                new TextureResource(R.drawable.menu_main_exit,
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
        
        //GUI
        //overlay
        {
        ResourceGroup groups[] = {ResourceGroup.ALL};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 0.85f);
        shapes.put("overlay", new ShapeResource(
            R.raw.shape_gui_overlay, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
        //START UP
        //omicron splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        shapes.put("omicron_splash", new ShapeResource(
            R.raw.shape_square_plane_textured, groups,
            "omicron_splash", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //with fire splash screen
        {
            ResourceGroup groups[] = {ResourceGroup.WITH_FIRE};
            shapes.put("with_fire_splash", new ShapeResource(
                R.raw.shape_square_plane_textured, groups,
                "with_fire_splash", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //presents splash screen
        {
            ResourceGroup groups[] = {ResourceGroup.MENU,
                    ResourceGroup.START_UP};
            shapes.put("presents_splash", new ShapeResource(
                R.raw.shape_small_square_plane_textured, groups,
                "presents_splash", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //splash screen fader
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON,
            ResourceGroup.WITH_FIRE};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        shapes.put("splash_fader", new ShapeResource(
            R.raw.shape_square_plane_coloured, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
        
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
        //play button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_menu_play_button", new ShapeResource(
                R.raw.shape_menu_main_button, groups,
                "main_menu_play_button", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //store button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_menu_store_button", new ShapeResource(
                R.raw.shape_menu_main_button, groups,
                "main_menu_store_button", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //options button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_menu_options_button", new ShapeResource(
                R.raw.shape_menu_main_button, groups,
                "main_menu_options_button", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //more button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_menu_more_button", new ShapeResource(
                R.raw.shape_menu_main_button, groups,
                "main_menu_more_button", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
        //exit button
        {
            ResourceGroup groups[] = {ResourceGroup.MENU};
            shapes.put("main_menu_exit_button", new ShapeResource(
                R.raw.shape_menu_main_button, groups,
                "main_menu_exit_button", "plain_texture_vertex",
                "texture_no_lighting_fragment"));
        }
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
        
        //DEBUG
        //Touch
        //debug touch point
        {
        ResourceGroup groups[] = {ResourceGroup.DEBUG};
        Vector4d col = new Vector4d(0.0f, 1.0f, 0.0f, 1.0f);
        shapes.put("debug_touchpoint", new ShapeResource(
            R.raw.shape_debug_touchpoint, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
    }
}
