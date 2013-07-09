package nz.co.withfire.diecubesdie.engine.level;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.gui.Overlay;
import nz.co.withfire.diecubesdie.entities.gui.TapPoint;
import nz.co.withfire.diecubesdie.entities.level.cubes.PaperCube;
import nz.co.withfire.diecubesdie.entities.level.terrian.Ground;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class LevelEngine implements Engine {

    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager
    private ResourceManager resources;
    
    //is true once the level is complete
    private boolean complete = false;
    
    //The list of all entities
    private EntityList entities = new EntityList();
    
    //the map of terrain entities
    private Entity entityMap[][][];
    
    //is true to add a touch point
    private boolean addTouchPoint= false;
    private Vector2d touchPos = new Vector2d();
    
    //TESTING
    private float followCam = 0.0f;
    
    //CONSTRUCTOR
    /**!Constructs a new Level engine
    @param context the android context
    @param resources the resource manager to use
    @param entityMap the entityMap for the terrian
    @param ground the ground*/
    public LevelEngine(Context context, ResourceManager resources,
        Entity entityMap[][][], Ground ground) {
        
        //set the variables
        this.context = context;
        this.resources = resources;
        this.entityMap = entityMap;
        
        //add the terrian entities
        addTerrain();
        
        //add the ground
        entities.add(ground);
    }
    
    //PUBLIC METHODS
    @Override
    public void init() {

        //set the clear colour of the renderer
        GLRenderer.setClearColour(new Vector4d(0, 0, 0, 1));
        
        //TODO: do this in the loader
        resources.loadGroup(ResourceGroup.LEVEL);
        
        //TESTING
        entities.add(new PaperCube(resources.getShape("paper_cube"),
            new Vector3d(4.0f, 0.0f, 0.0f), PaperCube.Side.LEFT, entityMap));
        
        //the fade in overlay
        //entities.add(new Overlay(resources.getShape("fade_overlay"),
        //    new Vector2d(), null, true));
    }

    @Override
    public boolean execute() {
        
        //TESTING
        followCam += (0.0445f * FpsManager.getTimeScale());
        
        //process any touch input
        //processTouch();
        
        //update the entities
        entities.update();

        return complete;
    }
    
    @Override
    public void applyCamera(float[] viewMatrix) {

//        //TESTING
//        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3.0f, 0.0f,
//                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(viewMatrix, 0, -4.0f, -3.7f, 11.0f);
        Matrix.rotateM(viewMatrix, 0, 65, -1.0f, 0, 0.0f);
//        Matrix.rotateM(viewMatrix, 0, 0, 0, 1.0f, 0.0f);
//        
//       // Matrix.translateM(viewMatrix, 0, 0, -20.0f, 20.0f);
//        Matrix.translateM(viewMatrix, 0, followCam, 0.0f, 0.0f);
    }
    
    @Override
    public void touchEvent(int event, int index, Vector2d touchPos) {
        
        switch (event) {
        
            //the user has pressed down
            case MotionEvent.ACTION_DOWN: {
                
                //request for a touch point to be added add a
                //touch point to the menu
                addTouchPoint = true;
                
                //set the co-ordantes from the event
                this.touchPos.copy(touchPos);
                break;
            }
        }
    }


    @Override
    public EntityList getEntities() {

        return entities;
    }

    @Override
    public Engine nextState() {
        // TODO Auto-generated method stub
        return null;
    }
    
    //PRIVATE METHODS
    /**Process a touch point
    @param viewMatrix the view Matrix*/
    void processTouch() {
        
    }
    
    /**Adds the terrian from the entity map to the entity list*/
    private void addTerrain() {
        
        for (int z = 0; z < entityMap.length; ++z) {
            for (int y = 0; y < entityMap[0].length; ++y) {
                for (int x = 0; x < entityMap[0][0].length; ++x) {
                    
                    if (entityMap[z][y][x] != null) {
                        
                        entities.add(entityMap[z][y][x]);
                    }
                }
            }
        }
    }
}
