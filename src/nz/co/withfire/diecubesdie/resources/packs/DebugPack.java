/****************************************\
| Resource pack for all debug resources. |
|                                        |
| @author David Saxon                   |
\****************************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class DebugPack {

    //PUBLIC METHODS
    /**Builds GUI resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        //SHAPES
        //debug touch point
        {
        ResourceGroup groups[] = {ResourceGroup.DEBUG};
        Vector4d col = new Vector4d(0.0f, 1.0f, 0.0f, 1.0f);
        resources.add("debug_touchpoint", new ShapeResource(
            R.raw.shape_debug_touchpoint, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
    }
}
