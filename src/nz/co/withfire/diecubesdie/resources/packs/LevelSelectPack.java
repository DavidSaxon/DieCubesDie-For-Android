/*********************************\
| Resource pack for level select. |
|                                 |
| @author David Saxon            |
\*********************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.ShapeResource;
import nz.co.withfire.diecubesdie.resources.types.TextureResource;

public class LevelSelectPack {

    //PUBLIC METHODS
    /**Builds level select resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
    
        //TEXTURES
        //background
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_background",
            new TextureResource(R.drawable.menu_levelselect_sky,
            groups));
        }
        //plains level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_plains_1",
            new TextureResource(R.drawable.menu_levelselect_plains_1,
            groups));
        }
        //mountains level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_mountains_1",
            new TextureResource(R.drawable.menu_levelselect_mountains_1,
            groups));
        }
        //city level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_city_1",
            new TextureResource(R.drawable.menu_levelselect_city_1,
            groups));
        }
        //desert level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_desert_1",
            new TextureResource(R.drawable.menu_levelselect_desert_1,
            groups));
        }
        //desert level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_jungle_1",
            new TextureResource(R.drawable.menu_levelselect_jungle_1,
            groups));
        }
        //stronghold level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_stronghold_1",
            new TextureResource(R.drawable.menu_levelselect_stronghold_1,
            groups));
        }
        
        //SHAPES
        //background
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_background", new ShapeResource(
            R.raw.shape_menu_levelselect_background, groups,
            "level_select_background", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //plains level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_plains_1", new ShapeResource(
            R.raw.shape_menu_levelselect_planetgrid, groups,
            "level_select_plains_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //mountains level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_mountains_1", new ShapeResource(
            R.raw.shape_menu_levelselect_planetgrid, groups,
            "level_select_mountains_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //city level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_city_1", new ShapeResource(
            R.raw.shape_menu_levelselect_planetgrid, groups,
            "level_select_city_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //desert level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_desert_1", new ShapeResource(
            R.raw.shape_menu_levelselect_planetgrid, groups,
            "level_select_desert_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //jungle level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_jungle_1", new ShapeResource(
            R.raw.shape_menu_levelselect_planetgrid, groups,
            "level_select_jungle_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //stronghold level 1
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL_SELECT};
        resources.add("level_select_stronghold_1", new ShapeResource(
            R.raw.shape_menu_levelselect_planetgrid, groups,
            "level_select_stronghold_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
