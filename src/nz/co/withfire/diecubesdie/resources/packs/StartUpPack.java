/*****************************\
| Resource pack for start up. |
|                             |
| @author David Saxon        |
\*****************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.BoundingResource;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class StartUpPack {

    //PUBLIC METHODS
    /**Builds GUI resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        //BOUNDING
        //main menu button bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.addBounding("main_menu_button",
            new BoundingResource(R.raw.bounding_menu_main_button,
            groups));
        }
        //social button bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.MENU};
        resources.addBounding("menu_social_button",
            new BoundingResource(R.raw.bounding_menu_social_button,
            groups));
        }
        
        //TEXTURES
        //omicron splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        resources.addTexture("omicron_splash",
            new TextureResource(R.drawable.omicron_splash,
            groups));
        }
        //with fire splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.WITH_FIRE};
        resources.addTexture("with_fire_splash",
            new TextureResource(R.drawable.with_fire_splash,
            groups));
        }
        //presents splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.MENU,
                ResourceGroup.START_UP};
        resources.addTexture("presents_splash",
            new TextureResource(R.drawable.splash_presents,
            groups));
        }
        
        //SHAPES
        //omicron splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        resources.addShape("omicron_splash", new ShapeResource(
            R.raw.shape_square_plane_textured, groups,
            "omicron_splash", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //with fire splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.WITH_FIRE};
        resources.addShape("with_fire_splash", new ShapeResource(
            R.raw.shape_square_plane_textured, groups,
            "with_fire_splash", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //presents splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.MENU,
                ResourceGroup.START_UP};
        resources.addShape("presents_splash", new ShapeResource(
            R.raw.shape_small_square_plane_textured, groups,
            "presents_splash", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //splash screen fader
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON,
            ResourceGroup.WITH_FIRE};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        resources.addShape("splash_fader", new ShapeResource(
            R.raw.shape_square_plane_coloured, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
    }
}
