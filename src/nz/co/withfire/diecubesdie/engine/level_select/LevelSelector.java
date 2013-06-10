/********************************************************\
| Controls level selection and displays the cube planet. |
|                                                        |
| @author David Saxon                                   |
\********************************************************/

package nz.co.withfire.diecubesdie.engine.level_select;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import nz.co.withfire.diecubesdie.entities.level_select.SelectLevel;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class LevelSelector {

    //VARIABLES
    //the number of areas
    private int NUMBER_OF_AREAS = 6;
    //the width of an area in levels
    private int AREA_WIDTH = 4;
    
    //the resource manager
    private ResourceManager resources;
    
    //the entity list
    private EntityList entities;
    
    //a list of list of the select levels
    private List<List<SelectLevel>> selectLevels;
    
    //CONSTRUCTOR
    /**Creates a new level selector
    @param resources a reference to the resource manager
    @param entities a reference to the entity list*/
    public LevelSelector(ResourceManager resources,
        EntityList entities) {
        
        this.resources = resources;
        this.entities = entities;
        
        selectLevels = new ArrayList<List<SelectLevel>>();
        
        init();
    }
    
    //PUBLIC METHODS
    
    //PRIVATE METHODS
    /**Initialises the level selector*/
    private void init() {
        
        //the rotation for each area
        Vector3d rotations[] = {new Vector3d(), new Vector3d(0.0f, 90.0f, 0.0f),
            new Vector3d(90.0f, 90.0f, 0.0f), new Vector3d(180.0f, 90.0f, 0.0f),
            new Vector3d(180.0f, 0.0f, 0.0f), new Vector3d(270.0f, 0.0f, 0.0f)
        };
        //the shapes for each area
        String shapes[] = {"level_select_plains_1", "level_select_mountains_1",
            "level_select_city_1", "level_select_desert_1",
            "level_select_jungle_1", "level_select_stronghold_1"
        };
        //the shaders
        int shaders[] = {resources.getShader("texture_no_lighting_fragment"),
            resources.getShader("texture_black_to_white_fragment"),
            resources.getShader("texture_dim_fragment"),
            resources.getShader("texture_dim_btw_fragment")
        };
        
        //add all the select levels
        for (int i = 0; i < NUMBER_OF_AREAS; ++i) {
            
            //create  a new array list
            ArrayList<SelectLevel> area = new ArrayList<SelectLevel>();
            
            //add the selects to the area
            for (float x = -1.0f; x < 1.0f; x += 0.5f) {
                for (float y = -1.0f; y < 1.0f; y += 0.5f) {
                    
                    //TESTING
                    boolean locked = true;
                    if (x <= -0.9f) {
                        
                        locked = false;
                    }
                    
                    Log.v(ValuesUtil.TAG, locked + "");
                    
                    //create a new select level
                    SelectLevel selectLevel = new SelectLevel(
                        resources.getShape(shapes[i]), shaders,
                        new Vector3d(x + 0.25f, y + 0.25f, -2.5f),
                        rotations[i], locked);
                    entities.add(selectLevel);
                    area.add(selectLevel);
                }
            }
            
            selectLevels.add(area);
        }
    }
}
