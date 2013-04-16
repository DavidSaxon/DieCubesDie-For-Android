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
import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.startup.Splash;
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
    //the with fire splash screen
    private Splash withFireSplash;
    
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

        //Load the shapes for the omicron intro
        resources.loadShapesFromGroup(ResourceGroup.OMICRON);
        
        //add the omicron splash screen entity
        omicronSplash = new Splash(
            resources.getShape("omicron_splash"),
            resources.getShape("splash_fader")); 
        entities.add(omicronSplash);
        drawables.add(omicronSplash);
    }

    @Override
    public boolean execute() {
        
        if (omicronSplash.fadeFinished()) {
            
            //TODO: change to secondary loading
            resources.loadTexturesFromGroup(ResourceGroup.WITH_FIRE);
            resources.loadShapesFromGroup(ResourceGroup.WITH_FIRE);
            
            entities.remove(omicronSplash);
            drawables.remove(omicronSplash);
            
            withFireSplash = new Splash(
                resources.getShape("with_fire_splash"),
                resources.getShape("splash_fader"));
            entities.add(withFireSplash);
            drawables.add(withFireSplash);
        }
        
        //update the entities
        for (Entity e : entities) {
            
            e.update();
        }
        
        return false;
    }

    @Override
    public List<Drawable> getDrawables() {
        
        return drawables;
    }

}
