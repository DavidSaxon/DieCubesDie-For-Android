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
        //grass 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_grass_1",
            new TextureResource(R.drawable.terrain_plains_grass_1,
            groups));
        }

        //top 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_top_1",
            new TextureResource(R.drawable.terrain_plains_top_1,
            groups));
        }
        //top 2
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_top_2",
            new TextureResource(R.drawable.terrain_plains_top_2,
            groups));
        }
        
        //side 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_side_1",
            new TextureResource(R.drawable.terrain_plains_side_1,
            groups));
        }
        //side 2
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_side_2",
            new TextureResource(R.drawable.terrain_plains_side_2,
            groups));
        }
        
        
        //SHAPES
        //grass 1 north
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ground_grass_1_north", new ShapeResource(
            R.raw.shape_terrain_ground_north, groups,
            "plains_grass_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //grass 1 east
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ground_grass_1_east", new ShapeResource(
            R.raw.shape_terrain_ground_east, groups,
            "plains_grass_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //grass 1 south
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ground_grass_1_south", new ShapeResource(
            R.raw.shape_terrain_ground_south, groups,
            "plains_grass_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //grass 1 west
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ground_grass_1_west", new ShapeResource(
            R.raw.shape_terrain_ground_west, groups,
            "plains_grass_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        
        //cliff top grass 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_top_1", new ShapeResource(
            R.raw.shape_terrain_cliff_top, groups,
            "plains_top_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        //cliff side 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_cliff_side_1", new ShapeResource(
            R.raw.shape_terrain_cliff_side, groups,
            "plains_side_1", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        
        //ramp top 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ramp_top_1", new ShapeResource(
            R.raw.shape_terrain_ramp_top, groups,
            "plains_top_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        //ramp side 1
        {
        ResourceGroup groups[] = {ResourceGroup.PLAINS};
        resources.add("plains_ramp_side_1", new ShapeResource(
            R.raw.shape_terrain_ramp_side, groups,
            "plains_side_2", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
