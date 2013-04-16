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
    
}
