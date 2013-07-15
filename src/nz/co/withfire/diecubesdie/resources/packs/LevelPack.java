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
    	//test
    	{
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("terrain_test",
            new TextureResource(R.drawable.terrain_all_test,
            groups));
        } int sfffjfFsdssavb;
        //spawn
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("spawn",
            new TextureResource(R.drawable.terrain_all_spawn,
            groups));
        }
        //spawn inside
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("spawn_inside",
            new TextureResource(R.drawable.terrain_all_spawn_inside,
            groups));
        }
        //finish
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("finish",
            new TextureResource(R.drawable.terrain_all_finish,
            groups));
        }
        //finish inside
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("finish_inside",
            new TextureResource(R.drawable.terrain_all_finish_inside,
            groups));
        }
    
        //paper cube
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("paper_cube",
            new TextureResource(R.drawable.cube_paper,
            groups));
        }
        //wooden cube
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("wooden_cube",
            new TextureResource(R.drawable.cube_wood,
            groups));
        }
        
        //SHAPES
        //spawn
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("spawn", new ShapeResource(
            R.raw.shape_terrain_spawn, groups,
            "spawn", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //spawn inside
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("spawn_inside", new ShapeResource(
            R.raw.shape_terrain_spawn_inside, groups,
            "spawn_inside", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //finish
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("finish", new ShapeResource(
            R.raw.shape_terrain_spawn, groups,
            "finish", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //finish inside
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("finish_inside", new ShapeResource(
            R.raw.shape_terrain_spawn_inside, groups,
            "finish_inside", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        
        //paper cube
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("paper_cube", new ShapeResource(
            R.raw.shape_cube, groups,
            "paper_cube", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
        //wooden cube
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("wooden_cube", new ShapeResource(
            R.raw.shape_cube, groups,
            "wooden_cube", "plain_texture_vertex",
            "texture_no_lighting_fragment"));
        }
    }
}
