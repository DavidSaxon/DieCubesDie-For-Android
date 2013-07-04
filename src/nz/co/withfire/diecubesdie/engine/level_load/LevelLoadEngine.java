/****************************\
| Engine for loading levels. |
|                            |
| @author David Saxon        |
\****************************/

package nz.co.withfire.diecubesdie.engine.level_load;

import java.util.Random;

import android.content.Context;
import android.util.Log;
import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.level.LevelEngine;
import nz.co.withfire.diecubesdie.entities.gui.GUIText;
import nz.co.withfire.diecubesdie.entities.level_load.Splash;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.renderer.text.Text;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class LevelLoadEngine implements Engine {

    //VARIABLES
    //the android context
    private final Context context;

    //the resource manager
    private ResourceManager resources;
    
    //the list of all entities
    private EntityList entities = new EntityList();
    
    //is true once the menu is complete
    private boolean complete = false;
    
    //random number generator
    private Random rand = new Random();
    
    //the resource names for all the possible load screens
    private String splashResources[] = {
            
      "load_box_cutters",
      "load_ice_fire"
    };

    //CONSTRUCTOR
    /**Creates a new level load engine
    @param context the android context
    @param resources the resource manager*/
    public LevelLoadEngine(final Context context, ResourceManager resources) {
        
        this.context = context;
        this.resources = resources;
    }
    
    //PUBLIC METHODS
    @Override
    public void init() {
        
        //randomly decide on the loading splash
        int splash = rand.nextInt(splashResources.length);
        
        Log.v(ValuesUtil.TAG, ""+splash);
        
        //load in the loading resources we need
        resources.loadTexture(splashResources[splash]);
        resources.loadShape(splashResources[splash]);
        
        //create the splash screen
        entities.add(new Splash(resources.getShape(splashResources[splash])));
        
        //loading text
        float titleSize = TransformationsUtil.scaleToScreen(0.14f);
        Vector2d titlePos = new Vector2d(
            0.0f, TransformationsUtil.getOpenGLDim().getY() / 1.35f);
        entities.add(new GUIText(titlePos, titleSize,
            Text.Align.CENTRE, context.getString(R.string.loading)));
    }

    @Override
    public boolean execute() {

        //update the entities
        entities.update();
        
        //TODO: begin loading here (on second or third iteration
        
        return complete;
    }

    @Override
    public void applyCamera(float[] viewMatrix) {

        //do nothing
    }

    @Override
    public void touchEvent(int event, int index, Vector2d touchPos) {
        
        //do nothing
    }

    @Override
    public EntityList getEntities() {

        return entities;
    }

    @Override
    public Engine nextState() {

        return new LevelEngine(context, resources);
    }
}
