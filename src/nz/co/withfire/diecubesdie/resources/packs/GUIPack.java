/*********************************\
| Resource pack for GUI elements. |
|                                 |
| @author David Saxon            |
\*********************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.BoundingResource;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class GUIPack {

    //PUBLIC METHODS
    /**Builds GUI resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        //BOUNDING BOXES
        //touch point bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        resources.addBounding("gui_touch_point",
            new BoundingResource(R.raw.bounding_menu_main_button,
            groups));
        }
        
        //TEXTURES
        //text
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        resources.addTexture("text",
            new TextureResource(R.drawable.text,
            groups));
        }
        
        //SHAPES
        //transparent overlay
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 0.85f);
        resources.addShape("overlay", new ShapeResource(
            R.raw.shape_gui_overlay, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
        //fade overlay
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        resources.addShape("fade_overlay", new ShapeResource(
            R.raw.shape_gui_overlay, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
    }
}
