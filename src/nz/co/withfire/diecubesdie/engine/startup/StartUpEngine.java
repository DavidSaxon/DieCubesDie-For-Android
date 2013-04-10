/************************************************************************\
| Engine that controls the game start up.                                |
| Loads in all resources the are required for start up and for the menu. |
|                                                                        |
| @author David Saxon                                                    |
\************************************************************************/

package nz.co.withfire.diecubesdie.engine.startup;

import android.content.Context;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.resources.ResourceManager;

public class StartUpEngine implements Engine {

    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager of the engine
    private ResourceManager resources;
    
    //CONSTRUCTOR
    /**Creates a new start up engine
    @param context the android context*/
    public StartUpEngine(final Context context) {
        
        this.context =  context;
    }
    
    @Override
    public void init() {
        
        //since this is the start up engine, create a new resource manager
        resources = new ResourceManager(context);
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        return false;
    }

}
