/**********************************\
| Resource Pack for the main menu. |
|                                  |
| @author David Saxon             |
\**********************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.BoundingResource;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class MainMenuPack {

    //PUBLIC METHODS
    /**Builds main menu resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        //BOUNDING
        //main menu button bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_menu_button",
            new BoundingResource(R.raw.bounding_menu_main_button,
            groups));
        }
        //social button bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("menu_social_button",
            new BoundingResource(R.raw.bounding_menu_social_button,
            groups));
        }
        
        //TEXTURES
        //main title
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_title",
            new TextureResource(R.drawable.menu_main_title,
            groups));
        }
        //facebook button
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_menu_facebook_button",
            new TextureResource(R.drawable.menu_main_facebook,
            groups));
        }
        //google plus button
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_menu_googleplus_button",
            new TextureResource(R.drawable.menu_main_googleplus,
            groups));
        }
        //with fire button
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_menu_withfire_button",
            new TextureResource(R.drawable.menu_main_withfire,
            groups));
        }
        //Background
        //background cubs
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_menu_cube",
            new TextureResource(R.drawable.menu_main_cube,
            groups));
        }
        
        //SHAPES
        //the title
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_title", new ShapeResource(
            R.raw.shape_menu_main_title, groups,
            "main_title", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //Buttons
        //facebook button
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_menu_facebook_button", new ShapeResource(
            R.raw.shape_menu_social_button, groups,
            "main_menu_facebook_button", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //google plus button
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_menu_googleplus_button", new ShapeResource(
            R.raw.shape_menu_social_button, groups,
            "main_menu_googleplus_button", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //with fire button
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("main_menu_withfire_button", new ShapeResource(
            R.raw.shape_menu_withfire_button, groups,
            "main_menu_withfire_button", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //Background
        //the ground in the menu
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        resources.add("menu_ground", new ShapeResource(
            R.raw.shape_menu_ground, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
        //the cube in the menu
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.add("menu_cube", new ShapeResource(
            R.raw.shape_menu_cube, groups,
            "main_menu_cube", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //Transitions
        //spikes
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        resources.add("menu_spikes", new ShapeResource(
            R.raw.shape_menu_spikes, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
    }
}
