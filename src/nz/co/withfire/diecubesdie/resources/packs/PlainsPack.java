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
        //ground grass 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_grass_1",
            new TextureResource(R.drawable.terrain_plains_ground_grass_1,
            groups));
        }
        //ground grass 2
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_grass_2",
            new TextureResource(R.drawable.terrain_plains_ground_grass_2,
            groups));
        }
        
        //ground dirt 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_dirt_1",
            new TextureResource(R.drawable.terrain_plains_ground_dirt_1,
            groups));
        }
        
        
        //cliff 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_1",
            new TextureResource(R.drawable.terrain_plains_cliff_1,
            groups));
        }
        //cliff 2
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_2",
            new TextureResource(R.drawable.terrain_plains_cliff_2,
            groups));
        }
        //cliff 3
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_3",
            new TextureResource(R.drawable.terrain_plains_cliff_3,
            groups));
        }
        //cliff 4
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_4",
            new TextureResource(R.drawable.terrain_plains_cliff_4,
            groups));
        }
        
        
        //ramp top 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ramp_top_1",
            new TextureResource(R.drawable.terrain_plains_ground_dirt_1,
            groups));
        }
        //ramp side 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ramp_side_1",
            new TextureResource(R.drawable.terrain_plains_cliff_2,
            groups));
        }
        
        
        //SHAPES
        //grass 1 top left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ground_grass_1_tl", new ShapeResource(
            R.raw.shape_terrain_ground_top_left, groups,
            "plains_grass_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //grass 1 top right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ground_grass_1_tr", new ShapeResource(
            R.raw.shape_terrain_ground_top_right, groups,
            "plains_grass_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //grass 1 bottom right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ground_grass_1_br", new ShapeResource(
            R.raw.shape_terrain_ground_bottom_right, groups,
            "plains_grass_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //grass 1 bottom left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ground_grass_1_bl", new ShapeResource(
            R.raw.shape_terrain_ground_bottom_left, groups,
            "plains_grass_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        
        //wall 1 top left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_wall_1_tl", new ShapeResource(
            R.raw.shape_terrain_wall_top_left, groups,
            "plains_grass_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //wall 1 top right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_wall_1_tr", new ShapeResource(
            R.raw.shape_terrain_wall_top_right, groups,
            "plains_grass_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //wall 1 bottom right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_wall_1_br", new ShapeResource(
            R.raw.shape_terrain_wall_bottom_right, groups,
            "plains_grass_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //wall 1 bottom left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_wall_1_bl", new ShapeResource(
            R.raw.shape_terrain_wall_bottom_left, groups,
            "plains_grass_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        
        //cliff 1 top left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_1_tl", new ShapeResource(
            R.raw.shape_terrain_cliff_top_left, groups,
            "plains_cliff_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //cliff 1 top right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_1_tr", new ShapeResource(
            R.raw.shape_terrain_cliff_top_right, groups,
            "plains_cliff_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        //cliff 2 top left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_2_tl", new ShapeResource(
            R.raw.shape_terrain_cliff_top_left, groups,
            "plains_cliff_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //cliff 2 top right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_2_tr", new ShapeResource(
            R.raw.shape_terrain_cliff_top_right, groups,
            "plains_cliff_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //cliff 2 bottom right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_2_br", new ShapeResource(
            R.raw.shape_terrain_cliff_bottom_right, groups,
            "plains_cliff_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //cliff 2 bottom left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_2_bl", new ShapeResource(
            R.raw.shape_terrain_cliff_bottom_left, groups,
            "plains_cliff_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        //cliff 3 top left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_3_tl", new ShapeResource(
            R.raw.shape_terrain_cliff_top_left, groups,
            "plains_cliff_3", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //cliff 3 top right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_3_tr", new ShapeResource(
            R.raw.shape_terrain_cliff_top_right, groups,
            "plains_cliff_3", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        //cliff 4 top left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_4_tl", new ShapeResource(
            R.raw.shape_terrain_cliff_top_left, groups,
            "plains_cliff_4", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //cliff 4 top right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_4_tr", new ShapeResource(
            R.raw.shape_terrain_cliff_top_right, groups,
            "plains_cliff_4", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //cliff 4 bottom right
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_4_br", new ShapeResource(
            R.raw.shape_terrain_cliff_bottom_right, groups,
            "plains_cliff_4", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //cliff 4 bottom left
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_4_bl", new ShapeResource(
            R.raw.shape_terrain_cliff_bottom_left, groups,
            "plains_cliff_4", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        
        //ramp top 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ramp_top_1", new ShapeResource(
            R.raw.shape_terrain_ramp_top, groups,
            "plains_ramp_top_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //ramp side 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ramp_side_1", new ShapeResource(
            R.raw.shape_terrain_ramp_side, groups,
            "plains_ramp_side_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
