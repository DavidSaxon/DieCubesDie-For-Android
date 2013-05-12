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
import nz.co.withfire.diecubesdie.entities.input.TouchPoint;
import nz.co.withfire.diecubesdie.entities.level.cubes.WoodenCube;
import nz.co.withfire.diecubesdie.entities.level.terrian.Ground;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
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
    private List<Entity> entities = new ArrayList<Entity>();
    //subset of entites that contains the drawables
    private List<Drawable> drawables = new ArrayList<Drawable>();
    
    //is true to add a touch point
    private boolean addTouchPoint= false;
    private Vector2d touchPos = new Vector2d();
    
    //TESTING
    private float followCam = 0.0f;
    
    //CONSTRUCTOR
    /**!Constructs a new Level engine
    @param context the android context
    @param resources the resource manager to use*/
    public LevelEngine(Context context, ResourceManager resources) {
        
        //set the variables
        this.context = context;
        this.resources = resources;
    }
    
    //PUBLIC METHODS
    @Override
    public void init() {

        //set the clear colour of the renderer
        GLRenderer.setClearColour(new Vector4d(0, 0, 0, 1));
        
        //TODO: do this in the loader
        resources.loadTexturesFromGroup(ResourceGroup.LEVEL);
        resources.loadShapesFromGroup(ResourceGroup.LEVEL);
        
        //TESTING
        //add a wooden cube
        WoodenCube testWoodenCube = new WoodenCube(
            resources.getShape("wooden_cube"));
        entities.add(testWoodenCube);
        drawables.add(testWoodenCube);
        
        //add ground
//        for (int i = -20; i < 4; ++i) {
//            for (int j = -1; j < 4; ++j) {
//                
//                Vector2d gPos = new Vector2d(i, j);
//                Ground g = new Ground(gPos,
//                    resources.getShape("plains_grass_tile"));
//                //entities.add(g);
//                drawables.add(g);
//            }
//        }
    }

    @Override
    public boolean execute() {
        
        //TESTING
        //followCam += (0.0445f * FpsManager.getTimeScale());
        
        //process any touch input
        processTouch();
        
        //update the entities
        for (Entity e : entities) {
            
            e.update();
        }

        return complete;
    }
    
    @Override
    public void applyCamera(float[] viewMatrix) {

        //TESTING
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(viewMatrix, 0, 0.0f, 0.0f, 25.0f);
        Matrix.rotateM(viewMatrix, 0, 70, -1.0f, 0, 0.0f);
        Matrix.rotateM(viewMatrix, 0, 0, 0, 1.0f, 0.0f);
        
       // Matrix.translateM(viewMatrix, 0, 0, -20.0f, 20.0f);
        Matrix.translateM(viewMatrix, 0, followCam, 0.0f, 0.0f);
    }
    
    @Override
    public void touchEvent(int event, Vector2d touchPos) {
        
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
    public List<Drawable> getDrawables() {

        return drawables;
    }

    @Override
    public Engine nextState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean shouldExit() {
        // TODO Auto-generated method stub
        return false;
    }
    
    //PRIVATE METHODS
    /**Process a touch point
    @param viewMatrix the view Matrix*/
    void processTouch() {
        
        //add a touch point if we need too
        if (addTouchPoint) {
            
            //add the touch point
            TouchPoint touchPoint;
            
            //add a debug touch point
            if (ValuesUtil.DEBUG) {
                
                Log.v(ValuesUtil.TAG, "pos: " + touchPos);
                
                 touchPoint = new TouchPoint(
                     resources.getShape("debug_touchpoint"),
                     touchPos);
            }
            //add a normal touch point
            else {
                
                touchPoint = new TouchPoint(touchPos);
            }
            
            entities.add(touchPoint);
            drawables.add(touchPoint);
            
            addTouchPoint = false;
        }
    }
}
