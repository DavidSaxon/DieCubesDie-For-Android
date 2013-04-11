/************************************************************************\
| Engine that controls the game start up.                                |
| Loads in all resources the are required for start up and for the menu. |
|                                                                        |
| @author David Saxon                                                    |
\************************************************************************/

package nz.co.withfire.diecubesdie.engine.startup;

import android.content.Context;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.renderer.renderable.GLTriangleCol;
import nz.co.withfire.diecubesdie.resources.ResourceManager;

public class StartUpEngine implements Engine {

    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager of the engine
    private ResourceManager resources;
    
    //TESTING
    public GLTriangleCol triangle;
    
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
        resources.loadShaders();
        
        //TESTING
        float coords[] =  { // in counterclockwise order:
                -0.5f,  0.5f, 1.0f,   // top
                -0.5f, -0.5f, 1.0f,   // bottom left
                 0.5f,  0.5f, 1.0f,    // bottom right
                 0.5f, -0.5f, 1.0f,
                -0.5f, -0.5f, 1.0f,
                 0.5f,  0.5f, 1.0f
            };
            
        float colour[] = {0.2f, 0.0f, 1.0f, 1.0f};
        
        triangle = new GLTriangleCol(coords, colour,
                resources.getShader("plain_colour_vertex"),
                resources.getShader("colour_no_lighting_fragment"));
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        return false;
    }

}
