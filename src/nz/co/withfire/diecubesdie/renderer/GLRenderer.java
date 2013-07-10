/********************************\
| The renderer for Die Cubes Die |
|                                |
| @author David Saxon            |
\********************************/

package nz.co.withfire.diecubesdie.renderer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

public class GLRenderer implements GLSurfaceView.Renderer {

    //VARIABLES
    //the android context
    private final Context context;
    
    //the current engine to use
    private Engine engine;
    //the fps manager
    private FpsManager fps = new FpsManager();
    
    //the clear colour
    private static Vector4d clearColour =
        new Vector4d(0.0f, 0.0f, 0.0f, 1.0f);
    
    private  List<MotionEvent> touchEvents =
        new CopyOnWriteArrayList<MotionEvent>();
    
    //Matrix
    //the projection matrix
    private final float[] projectionMatrix = new float[16];
    //the view matrix
    private final float[] viewMatrix = new float[16];
    
    
    //CONSTRUCTOR
    /**Creates a new OpenGL renderer
    @param context the android context
    @param engine the current engine the renderer will be using*/
    public GLRenderer(final Context context, Engine engine) {
        
        //initialise variables
        this.context = context;
        this.engine = engine;
    }
    
    //PUBLIC METHODS
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        
        //initialise openGL
        initGL();
        
        //Initialise the engine
        engine.init();
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        
        //set the view port
        GLES20.glViewport(0, 0, width, height);

        //calculate the projection matrix
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3.0f, 50);
        
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        
        //set up the transformation utilities
        TransformationsUtil.init(new Vector2d(width, height),
            viewMatrix, projectionMatrix);
        
        fps.zero();
    }
    
    @Override
    public void onDrawFrame(GL10 arg0) {
        
        //the amount of times we need to update
        int updateAmount = fps.update();
        
        //update as many times as we need to
        for (int i = 0; i < updateAmount; ++i) {
            
            //process the events and pass them to the engine
            for (MotionEvent e : touchEvents) {
                
                for (int index = 0; index < e.getPointerCount(); ++index) {
                    
                    //get the touch point
                    Vector2d touchPos = new Vector2d(e.getX(index),
                        e.getY(index));
                    
                    //convert to OpenGL co-ordinates
                    touchPos.copy(TransformationsUtil.screenPosToOpenGLPos(
                        touchPos, viewMatrix, projectionMatrix));
                    
                    engine.touchEvent(e.getAction(), index, touchPos);
                }
            }
            
            //clear the touch events
            touchEvents.clear();
            
            //executes the engine
            if (engine.execute()) {
                    
                //get the next engine and continue
                engine = engine.nextState();
                engine.init();
                
                //zero the fps
                fps.zero();
                break;
            }
        }
        
        //redraw background colour
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        
        //apply the camera
        engine.applyCamera(viewMatrix);
        
        //get the entities from the engine and draw them
        engine.getEntities().draw(viewMatrix, projectionMatrix);
        
        //reset the camera
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        
        //draw the gui components
        engine.getEntities().drawGUI(viewMatrix, projectionMatrix);
    }
    
    /**Inputs an event
    @param event the input event*/
    public void touchEvent(MotionEvent event) {
        
        //add to the list of touch events
        touchEvents.add(event);
    }
    
    /**@param clearColour the new clear colour*/
    public static void setClearColour(Vector4d clearColour) {
        
        GLRenderer.clearColour.copy(clearColour);
        
        GLES20.glClearColor(clearColour.getX(), clearColour.getY(),
            clearColour.getZ(), clearColour.getW());
    }

    //PRIVATE METHODS
    /**Initialise openGL*/
    private void initGL() {
        
        //set the clear colour
        GLES20.glClearColor(clearColour.getX(), clearColour.getY(),
            clearColour.getZ(), clearColour.getW());
        
        //enable depth testing
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);
        
        //enable backface culling
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glCullFace(GLES20.GL_BACK);
        GLES20.glFrontFace(GLES20.GL_CCW);
        
        //enable transparency
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc (GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }
}
