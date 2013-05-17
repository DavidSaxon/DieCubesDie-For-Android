/************************************************\
| Interface that all activity engines implement. |
|                                                |
| @author David Saxon                            |
\************************************************/

package nz.co.withfire.diecubesdie.engine;

import java.util.List;

import android.view.MotionEvent;

import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public interface Engine {

    //PUBLIC METHODS
    /**Initialises the engine*/
    public void init();
    
    /**Executes the engine
    @param viewMatrix the view matrix
    @param projectionMatrix the projectiond matrix
    @return true once the engine has completed*/
    public boolean execute();
    
    /**Applies the camera transformations
    @param viewMatrix the view matrix to apply the camera translations to*/
    public void applyCamera(float[] viewMatrix);
    
    /**Inputs a motion event into the engine
    @param event the motion event*/
    public void touchEvent(int event, Vector2d touchPos);
    
    /**@return the entity list*/
    public EntityList getEntities();
    
    /**@return the next state to execute once this state has completed*/
    public Engine nextState();
}
