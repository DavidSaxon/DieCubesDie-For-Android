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
        
        //TEXTURES
        //omicron splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        resources.add("omicron_splash",
            new TextureResource(R.drawable.startup_omicron_splash,
            groups));
        }
        //with fire splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.WITH_FIRE};
        resources.add("with_fire_splash",
            new TextureResource(R.drawable.startup_with_fire_splash,
            groups));
        }
        //presents splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.MENU,
                ResourceGroup.START_UP};
        resources.add("presents_splash",
            new TextureResource(R.drawable.startup_presents_splash,
            groups));
        }
        
        //SHAPES
        //omicron splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON};
        resources.add("omicron_splash", new ShapeResource(
            R.raw.shape_startup_splash, groups,
            "omicron_splash", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //with fire splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.WITH_FIRE};
        resources.add("with_fire_splash", new ShapeResource(
            R.raw.shape_startup_splash, groups,
            "with_fire_splash", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //presents splash screen
        {
        ResourceGroup groups[] = {ResourceGroup.MENU,
                ResourceGroup.START_UP};
        resources.add("presents_splash", new ShapeResource(
            R.raw.shape_startup__splash_small, groups,
            "presents_splash", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //splash screen fader
        {
        ResourceGroup groups[] = {ResourceGroup.OMICRON,
            ResourceGroup.WITH_FIRE};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        resources.add("splash_fader", new ShapeResource(
            R.raw.shape_gui_fade, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
    }
}
