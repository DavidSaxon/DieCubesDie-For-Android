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
import android.util.Log;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.startup.Splash;
import nz.co.withfire.diecubesdie.renderer.renderable.model.Model;
import nz.co.withfire.diecubesdie.renderer.renderable.shape.GLTriangleCol;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class StartUpEngine implements Engine {

    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager of the engine
    private ResourceManager resources;
    
    //The list of all entities
    private List<Entity> entities = new ArrayList<Entity>();
    //subset of entites that contains the drawables
    private List<Drawable> drawables = new ArrayList<Drawable>();
    
    //Entities
    //the omicron splash screen
    private Splash omicronSplash;
    
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
        
        //Load the textures for the omicron intro
        resources.loadTexturesFromGroup(ResourceGroup.OMICRON);

        //Load the models for the omicron intro
        resources.loadModelsFromGroup(ResourceGroup.OMICRON);
        
        //add the omicron splash screen entity
        omicronSplash = new Splash(resources.getModel(
            "omicron_splash"), null); 
        entities.add(omicronSplash);
        drawables.add(omicronSplash);
    }

    @Override
    public boolean execute() {
        
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Drawable> getDrawables() {
        
        return drawables;
    }

}
