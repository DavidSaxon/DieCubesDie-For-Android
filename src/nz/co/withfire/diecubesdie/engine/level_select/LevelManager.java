/*************************************\
| Manages the levels in level select. |
|                                     |
| @author David Saxon                |
\*************************************/

package nz.co.withfire.diecubesdie.engine.level_select;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.entities.gui.TapPoint;
import nz.co.withfire.diecubesdie.entities.gui.button.Button;
import nz.co.withfire.diecubesdie.entities.level_select.GridButton;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.utilities.CollisionUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class LevelManager {

    //VARIABLES
    //the resources manager
    private ResourceManager resources;
    
    //the entities list
    private EntityList entities;
    
    //the shape for the grid
    private Shape gridShape;
    
    //the centre position of the grid
    private Vector2d pos = new Vector2d();
    
    //the number of areas
    private final int NUMBER_OF_AREAS = 6;
    //the number of levels per area
    private final int LEVELS_PER_AREA = 20;
    //the width of the gird (in levels)
    private final int GRID_WIDTH = 4;
    //the size of a grid element
    private final float GRID_ELEMENT_WIDTH = 0.375f;
    
    //the current area
    private int area = 0;
    
    //the names of the area
    private final String areaNames[];
    
    //the grid buttons
    private List<Button> grid = new ArrayList<Button>();
    
    //CONSTRUCTOR
    /**Creates a new level manager
    @param context the android context
    @param resources the resource manager
    @param entities the entity list
    @param pos the position for the level select grid*/
    public LevelManager(final Context context, ResourceManager resources,
        EntityList entities, Vector2d pos) {
        
        this.resources = resources;
        this.entities = entities;
        this.pos.setX(pos.getX() - 0.12f);
        this.pos.setY(pos.getY() + 0.187f);
        
        //get needed data from resources
        gridShape = resources.getShape("level_select_grid");
        
        //get the area names
        areaNames = new String[NUMBER_OF_AREAS];
                
        initAreaNames(context);
        
        initButtons();
    }
    
    //PUBLIC METHODS
    /**Checks if the tap point is pressing on the grid
    @param tap the tap point*/
    public void tapEvent(TapPoint tap) {
        
        //find if any grid buttons have been pressed
        Button pressed =
            CollisionUtil.findButtonCollisions(tap, grid);
        
        if (pressed != null) {
            
            //deselect all the buttons
            deselectGrid();
            //select the pressed button
            pressed.selected(true);
        }
    }
    
    /**Increments the area*/
    public void incrementArea() {
        
        area++;
        
        //clamp
        area = area % NUMBER_OF_AREAS;
        
        deselectGrid();
    }
    
    /**Decrements the area*/
    public void decrementArea() {
        
        --area;
        
        //clamp
        if (area < 0) {
            
            area = NUMBER_OF_AREAS - 1;
        }
        
        deselectGrid();
    }
    
    /**@param display whether the grid should be displayed*/
    public void displayGrid(boolean display) {
        
        for (Button b : grid) {
            
            GridButton g = (GridButton) b;
            
            g.shouldDraw(display);
        }
    }
    
    /**@return the name of this area*/
    public String getAreaName() {
        
        return areaNames[area];
    }
    
    //PRIVATE METHODS
    /**Deselects all grid buttons*/
    private void deselectGrid() {
        
        for (Button b : grid) {
            
            b.selected(false);
        }
    }
    
    /**Initialises the area names array
    @param context the android context*/
    private void initAreaNames(final Context context) {
        
        areaNames[0] = (context.getString(R.string.level_select_area_1));
        areaNames[1] = (context.getString(R.string.level_select_area_2));
        areaNames[2] = (context.getString(R.string.level_select_area_3));
        areaNames[3] = (context.getString(R.string.level_select_area_4));
        areaNames[4] = (context.getString(R.string.level_select_area_5));
        areaNames[5] = (context.getString(R.string.level_select_area_6));
    }
    
    /**Adds the grid buttons*/
    private void initButtons() {
        
        //the array of shaders for buttons to use
        int gridShaders[] = {
                
            resources.getShader("grid_unlocked_select"),
            resources.getShader("grid_unlocked_deselect"),
            resources.getShader("grid_locked_select"),
            resources.getShader("grid_locked_deselect"),
        };
        
        for (int y = 0; y < GRID_WIDTH; ++y) {
            for (int x = 0; x < GRID_WIDTH; ++x) {
                
                //shorthand
                int _x = x - GRID_WIDTH / 2;
                int _y = y - GRID_WIDTH / 2;
                
                //calculate the position of the button
                Vector2d buttonPos = 
                    new Vector2d(
                        pos.getX() + (_x * GRID_ELEMENT_WIDTH),
                        pos.getY() + (_y * GRID_ELEMENT_WIDTH)
                    );
                
                //add the new button
                GridButton temp = new GridButton(gridShape, buttonPos,
                        resources.getBounding("level_select_grid"),
                        gridShaders);
                
                if (x == 3 && y == 3) {
                    
                    temp.locked(false);
                }
                else {
                    
                    temp.locked(true);
                }
                
                entities.add(temp);
                grid.add(temp);
            }
        }
        
    }
}
