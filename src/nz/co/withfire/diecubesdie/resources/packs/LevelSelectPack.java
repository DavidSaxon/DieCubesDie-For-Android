/*********************************\
| Resource pack for level select. |
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

public class LevelSelectPack {

    //PUBLIC METHODS
    /**Builds level select resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
    
        //BOUNDING
        //grid bounding box
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_grid",
            new BoundingResource(R.raw.bounding_levelselect_grid,
            groups));
        }
        
        //TEXTURES
        //background
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_background",
            new TextureResource(R.drawable.levelselect_sky,
            groups));
        }
        //world
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_world",
            new TextureResource(R.drawable.levelselect_world,
            groups));
        }
        //grid
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_grid",
            new TextureResource(R.drawable.levelselect_grid,
            groups));
        }
        
        //SHAPES
        //background
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_background", new ShapeResource(
            R.raw.shape_levelselect_background, groups,
            "level_select_background", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //world
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_world", new ShapeResource(
            R.raw.shape_levelselect_world, groups,
            "level_select_world", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //grid
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_grid", new ShapeResource(
            R.raw.shape_levelselect_grid, groups,
            "level_select_grid", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
