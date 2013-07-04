/********************************************\
| Resource pack for general level resources. |
|                                            |
| @author David Saxon                       |
\********************************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.LevelResource;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;

public class LevelPack {
    
    //PUBLIC METHODS
    /**Builds general level resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        //TEXTURES
        //wooden cube
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("wooden_cube",
            new TextureResource(R.drawable.cube_wood,
            groups));
        }
        
        //SHAPES
        //wooden cube
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("wooden_cube", new ShapeResource(
            R.raw.shape_cube_textured, groups,
            "wooden_cube", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
