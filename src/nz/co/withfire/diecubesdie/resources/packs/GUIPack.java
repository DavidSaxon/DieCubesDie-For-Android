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
        resources.add("gui_touch_point",
            new BoundingResource(R.raw.bounding_menu_main_button,
            groups));
        }
        //text image button bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        resources.add("button_text_image",
            new BoundingResource(R.raw.bounding_gui_button_text_image,
            groups));
        }
        
        //TEXTURES
        //text
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        resources.add("text",
            new TextureResource(R.drawable.text,
            groups));
        }
        //text and image button black and white transparent
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        resources.add("text_image_button_bwt",
            new TextureResource(R.drawable.gui_button_text_image_bwt,
            groups));
        }
        
        //SHAPES
        //transparent overlay
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 0.85f);
        resources.add("overlay", new ShapeResource(
            R.raw.shape_gui_overlay, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
        //fade overlay
        {
        ResourceGroup groups[] = {ResourceGroup.GUI};
        Vector4d col = new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
        resources.add("fade_overlay", new ShapeResource(
            R.raw.shape_gui_overlay, groups,
            col, "plain_colour_vertex",
            "colour_no_lighting_fragment"));
        }
        //text and image button black and white transparent
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("text_image_button_bwt", new ShapeResource(
            R.raw.shape_gui_button_text_image, groups,
            "text_image_button_bwt", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
