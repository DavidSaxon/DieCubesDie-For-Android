/************************************************\
| Interface that all activity engines implement. |
|                                                |
| @author David Saxon                            |
\************************************************/

package nz.co.withfire.diecubesdie.engine;

import java.util.List;

import nz.co.withfire.diecubesdie.entities.Drawable;

public interface Engine {

    //PUBLIC METHODS
    /**Initialises the engine*/
    public void init();
    
    /**Executes the engine
    @return true once the engine has completed*/
    public boolean execute();
    
    /**@return the list of drawables in the engine*/
    public List<Drawable> getDrawables();
    
    /**Applies the camera transformations
    @param viewMatrix the view matrix to apply the camera translations to*/
    public void applyCamera(float[] viewMatrix);
    
    /**@return the next state to execute once this state has completed*/
    public Engine nextState();
    
    /**@return if the game should exit after this state has been completed*/
    public boolean shouldExit();
}
