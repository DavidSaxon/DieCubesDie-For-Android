/**********************************************\
| Builds all resources in the plains tile set. |
|                                              |
| @author David Saxon                         |
\**********************************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;

public class PlainsPack {

    //PUBLIC METHODS
    /**Builds plains tile set resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        //TEXTURES
        //grass 1 tile
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL,
                ResourceGroup.MENU, ResourceGroup.TERRIAN,
                ResourceGroup.PLAINS};
        resources.add("plains_grass_tile",
            new TextureResource(R.drawable.terrain_plains_grass_1,
            groups));
        }
        
        //SHAPES
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL,
                ResourceGroup.CUBE};
        resources.add("plains_grass_tile", new ShapeResource(
            R.raw.shape_terrian_ground_tile, groups,
            "plains_grass_tile", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
