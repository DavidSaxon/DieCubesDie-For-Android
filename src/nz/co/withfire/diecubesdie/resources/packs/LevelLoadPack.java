/**********************************\
| Resource pack for level loading. |
|                                  |
| @author David Saxon              |
\**********************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;

public class LevelLoadPack {

    //PUBLIC METHODS
    /**Builds level select resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        //TEXTURES
        //box cutters
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_LOAD};
        resources.add("load_box_cutters",
            new TextureResource(R.drawable.levelload_box_cutters,
            groups));
        }
        //ice fire
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_LOAD};
        resources.add("load_ice_fire",
            new TextureResource(R.drawable.levelload_ice_fire,
            groups));
        }
        
        //SHAPES
        //box cutters
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_LOAD};
        resources.add("load_box_cutters", new ShapeResource(
            R.raw.shape_levelload_splash, groups,
            "load_box_cutters", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //ice fire
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_LOAD};
        resources.add("load_ice_fire", new ShapeResource(
            R.raw.shape_levelload_splash, groups,
            "load_ice_fire", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
