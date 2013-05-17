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
import android.view.MotionEvent;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.level.LevelEngine;
import nz.co.withfire.diecubesdie.engine.menu.MainMenuEngine;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.startup.Splash;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class StartUpEngine implements Engine {

    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager of the engine
    private ResourceManager resources;
    
    //The list of all entities
    private EntityList entities = new EntityList();
    
    //is true once start up has finished
    private boolean complete = false;
    //counts which stage of loading the engine is up to
    private int loadCounter = 0;
    
    //Entities
    //the splash screen
    private Splash splash;
    
    //CONSTRUCTOR
    /**Creates a new start up engine
    @param context the android context*/
    public StartUpEngine(final Context context) {
        
        this.context =  context;    
    }
    
    @Override
    public void init() {
        
        //set the clear colour of the renderer
        GLRenderer.setClearColour(new Vector4d(0, 0, 0, 1));
        
        //start loading
        load();
    }

    @Override
    public boolean execute() {
        
        //if the splash screen is done
        if (splash != null && splash.fadeFinished()) {
            
            //perform more loading
            load();
        }
        
        //update the entities
        entities.update();
        
        return complete;
    }
    
    @Override
    public void applyCamera(float[] viewMatrix) {

        //do nothing
    }
    
    @Override
    public void touchEvent(int event, Vector2d touchPos) {
        
        //do nothing
    }

    @Override
    public EntityList getEntities() {
        
        return entities;
    }
    
    @Override
    public Engine nextState() {

        //go to the menu
        return new MainMenuEngine(context, resources);
        //return new LevelEngine(context, resources);
    }

    //PRIVATE FUNCTIONS
    /**Loads data in stages*/
    private void load() {
        
        if (loadCounter == 0) {
            
            //create the required objects
            //create the resource manager
            resources = new ResourceManager(context);
            
            //load all the shaders
            resources.loadAllShaders();
            
            //load the omicron splash data
            resources.loadGroup(ResourceGroup.OMICRON);
            
            //load the start up music
            //TODO:
            
            //create the omicron splash
            splash = new Splash(
                resources.getShape("omicron_splash"),
                resources.getShape("splash_fader")); 
            entities.add(splash);
        }
        else if (loadCounter == 1) {
            
            //load the resources that are required by all
            resources.loadGroup(ResourceGroup.ALL);
            
            //load the with fire resources
            resources.loadGroup(ResourceGroup.WITH_FIRE);
            
            //remove the omicron splash screen
            entities.remove(splash);
            
            //TODO:release the omicron resources
            
            //add the with fire splash screen
            splash = new Splash(
                resources.getShape("with_fire_splash"),
                resources.getShape("splash_fader"));
            entities.add(splash);
        }
        else if (loadCounter == 2) {
            
            if (DebugUtil.DEBUG) {
                
                //load the debug resources
                resources.loadGroup(ResourceGroup.DEBUG);
                
                //set the debug bounding box shaders
                DebugUtil.boundingVertexShader =
                    resources.getShader("plain_colour_vertex");
                
                DebugUtil.boundingFragmentShader =
                    resources.getShader("colour_no_lighting_fragment");
            }
            
            //load the menu resources
            resources.loadGroup(ResourceGroup.MENU);
            
            //remove the with fire splash screen
            entities.remove(splash);
            
            //TODO:release the with fire resources
            
            //add the presents splash screen
            splash = new Splash(
                resources.getShape("presents_splash"),
                resources.getShape("splash_fader"));
            entities.add(splash);
        }
        else if (loadCounter == 3) {
            
            //we're done!
            complete = true;
        }
        
        ++loadCounter;
    }
}
