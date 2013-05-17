/**************************************************************\
| A list that stores and manager entities based on their type. |
|                                                              |
| @author David Saxon                                         |
\**************************************************************/

package nz.co.withfire.diecubesdie.entity_list;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class EntityList {

    //VARIABLES
    //the list of all entities
    private List<Entity> entities = new ArrayList<Entity>();
    //the list of drawable entities
    private List<Drawable> drawables = new ArrayList<Drawable>();
    //the list of GUI drawables
    private List<GUIDrawable> gui = new ArrayList<GUIDrawable>();
    
    //PUBLIC METHODS
    /**Updates all the entities in the entity list*/
    public void update() {
        
        for (Entity e : entities) {
            
            e.update();
        }
    }

    /**Draws the standard drawables
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix*/
    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        
        for (Drawable d : drawables) {
            
            d.draw(viewMatrix, projectionMatrix);
        }
    }
    
    /**Draws the gui components
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix*/
    public void drawGUI(float[] viewMatrix, float[] projectionMatrix) {
        
        for (GUIDrawable d : gui) {
            
            d.draw(viewMatrix, projectionMatrix);
        }
    }
    
    /**@param e the entity to add to the entity list*/
    public void add(Entity e) {
        
        //always add everything to the list of entities
        entities.add(e);
        
        //check the types
        if (e instanceof GUIDrawable) {
            
            gui.add((GUIDrawable) e);
        }
        else if (e instanceof Drawable) {
            
            drawables.add((Drawable) e);
        }
    }
    
    /**@param e the entity to remove from the entity list*/
    public void remove(Entity e) {
        
        entities.remove(e);
        
        //check the types
        if (e instanceof GUIDrawable) {
            
            gui.remove((GUIDrawable) e);
        }
        else if (e instanceof Drawable) {
            
            drawables.remove((Drawable) e);
        }
    }
}
