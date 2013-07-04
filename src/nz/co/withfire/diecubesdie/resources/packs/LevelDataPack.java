/*******************************\
| Resource pack for level data. |
|                               |
| @author David Saxon           |
\*******************************/

package nz.co.withfire.diecubesdie.resources.packs;

import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.resources.types.LevelResource;

public class LevelDataPack {
    
    //PUBLIC METHODS
    /**Builds level select resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        //LEVELS
        //test level
        {
        ResourceGroup groups[] = {ResourceGroup.LEVEL};
        resources.add("test_level",
            new LevelResource(R.raw.level_test,
            groups));
        }
    }
}
