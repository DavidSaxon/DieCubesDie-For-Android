/********************************\
| The renderer for Die Cubes Die |
|                                |
| @author David Saxon            |
\********************************/

package nz.co.withfire.diecubesdie.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.startup.StartUpEngine;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class GLRenderer implements GLSurfaceView.Renderer{

    //VARIABLES
    //the android context
    private final Context context;
    
    //the current engine to use
    private Engine engine;
    
    //TESTING
    private float blue = 0.0f;
    private boolean up = true;
    
    //Matrix
    //the projection matrix
    private final float[] projectionMatrix = new float[16];
    //the view matrix
    private final float[] viewMatrix = new float[16];
    
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the translation matrix
    private float[] tMatrix = new float[16];
    
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
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        
        //set the camera position
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
    }
    
    @Override
    public void onDrawFrame(GL10 arg0) {
       
        Log.v(ValuesUtil.TAG, "draw");
        
        //redraw background colour
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        //set the camera position
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        
        ((StartUpEngine) engine).model.draw(viewMatrix, projectionMatrix);
        
//        Log.v(ValuesUtil.TAG, "here");
//        //TESTING
//        if (up && blue < 1.0f) {
//            
//            blue += 0.01f;
//        }
//        else {
//            
//            up = false;
//        }
//        
//        if (!up && blue > 0.0f) {
//            
//            blue -= 0.01f;
//        }
//        else {
//            
//            up = true;
//        }
        
        //set the clear colour
        //GLES20.glClearColor(0.0f, 0.0f, blue, 1.0f);
    }

    //PRIVATE METHODS
    /**Initialise openGL*/
    private void initGL() {
        
        //set the clear colour
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        //enable transparency
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc (GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }
}
