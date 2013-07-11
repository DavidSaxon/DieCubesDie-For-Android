/****************************\
| Engine for loading levels. |
|                            |
| @author David Saxon        |
\****************************/

package nz.co.withfire.diecubesdie.engine.level_load;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.util.Log;
import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.level.LevelEngine;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.gui.GUIText;
import nz.co.withfire.diecubesdie.entities.level.terrian.Ground;
import nz.co.withfire.diecubesdie.entities.level_load.Splash;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.renderer.text.Text;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.types.LevelResource;
import nz.co.withfire.diecubesdie.utilities.LevelLoadUtil;
import nz.co.withfire.diecubesdie.utilities.ResourceUtil;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

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
    
    //the level key
    private final String levelName;
    
    //the number of iterations we perform before beginning loading
    private final int PREITERATIONS = 3;
    //the current iteration count
    private int iterations = 0;
    
    //the map of all terrain entities
    private Entity entityMap[][][];
    //the ground object for the level
    private Ground ground;
    
    //the resource names for all the possible load screens
    private String splashResources[] = {
            
      "load_box_cutters",
      "load_ice_fire"
    };

    //CONSTRUCTOR
    /**Creates a new level load engine
    @param context the android context
    @param resources the resource manager
    @param the level name to load*/
    public LevelLoadEngine(final Context context, ResourceManager resources,
        String levelName) {
        
        this.context = context;
        this.resources = resources;
        this.levelName = levelName;
    }
    
    //PUBLIC METHODS
    @Override
    public void init() {
        
        //randomly decide on the loading splash
        int splash = rand.nextInt(splashResources.length);
        
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
        
        //wait unit enough iterations have passed before loading
        if (iterations >= PREITERATIONS) {
            
            //load all the data we need
            load();
        }
        
        //count the number of iterations
        ++iterations;
        
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

        return new LevelEngine(context, resources, entityMap, ground);
    }
    
    //PRIVATE FUNCTIONS
    /**Load all the needed data for the level*/
    private void load() {
        
        //get the level resource and preload
        LevelResource levelData = resources.getLevel(levelName);
        levelData.preload(context);
        
        //load general level resources
        resources.loadGroup(ResourceManager.ResourceGroup.LEVEL);
        
        //load the area
        loadArea(levelData);
        
        //load the entity map
        loadEntities(levelData);
        
        //ready to exit
        complete = true;
    }
    
    /**Loads in the area resources for the level
    @param levelData the level resource*/
    private void loadArea(LevelResource levelData) {
        
        ValuesUtil.LevelArea area = levelData.getArea();
        
        //get the resource group for the level
        ResourceManager.ResourceGroup group =
            ResourceUtil.areaToGroup(area);
        
        //load in the resources in that group
        resources.loadGroup(group);
    }
    
    /**Loads all the entities to the entity map
    @param level data the level resource*/
    private void loadEntities(LevelResource levelData) {
        
        //read the dimensions
        Vector3d dim = new Vector3d(levelData.getDim());
        
        //create the ground
        ground = new Ground(dim);
        
        //create the empty entity map
        entityMap = new Entity[(int) dim.getZ()]
            [(int) dim.getY()][(int) dim.getX()];
        
        //get the code map
        List<List<String>> codeMap = levelData.getMap();
        
        //iterate over the map and the components
        for (int y = 0; y < dim.getY(); ++y) {
            for (int x = 0; x < dim.getX(); ++x) {
                
                //shorthand
                int _x = (int) ((dim.getX() - 1) - x);
                int _y = (int) ((dim.getY() - 1) - y);
                
                //get the entity
                LevelLoadUtil.createEntity(codeMap.get(y).get(x),
                    new Vector2d(_x, _y), entityMap, ground, resources);
            }
        }
    }
}
