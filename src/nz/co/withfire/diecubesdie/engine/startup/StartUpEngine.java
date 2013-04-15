/************************************************************************\
| Engine that controls the game start up.                                |
| Loads in all resources the are required for start up and for the menu. |
|                                                                        |
| @author David Saxon                                                    |
\************************************************************************/

package nz.co.withfire.diecubesdie.engine.startup;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.renderer.renderable.Renderable;
import nz.co.withfire.diecubesdie.renderer.renderable.model.Model;
import nz.co.withfire.diecubesdie.renderer.renderable.shape.GLTriangleCol;
import nz.co.withfire.diecubesdie.resources.ResourceManager;

public class StartUpEngine implements Engine {

    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager of the engine
    private ResourceManager resources;
    
    //TESTING
    public GLTriangleCol triangle;
    public Model model;
    
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
        //load all the shaders
        resources.loadAllShaders();
        //TODO: change to only load omicron models
        resources.loadAllModels();
        
        //TESTING
        model = resources.getModel("test_plane");
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        return false;
    }

}
