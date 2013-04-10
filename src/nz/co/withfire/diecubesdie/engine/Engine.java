/************************************************\
| Interface that all activity engines implement. |
|                                                |
| @author David Saxon                            |
\************************************************/

package nz.co.withfire.diecubesdie.engine;

public interface Engine {

    //PUBLIC METHODS
    /**Initialises the engine*/
    public void init();
    
    /**Executes the engine
    @return true once the engine has completed*/
    public boolean execute();
}
