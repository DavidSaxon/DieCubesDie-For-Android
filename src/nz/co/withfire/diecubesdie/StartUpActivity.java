/************************************************************************\
| The initial activity of the game, loads up the base resources for game |
|                                                                        |
| @author David Saxon                                                    |
\************************************************************************/

package nz.co.withfire.diecubesdie;

import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class StartUpActivity extends Activity {

    //VARIABLES
    //the gl surface to render onto
    private StartUpGLSurfaceView display;
    
    //PUBLIC METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        //super call
        super.onCreate(savedInstanceState);
        
        //set to full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //set the display
        display = new StartUpGLSurfaceView(this);
        
        //set the content view to the display
        setContentView(display);
    }
}


//TODO turn the display into the engine
//have a different engine for each activity

class StartUpGLSurfaceView extends GLSurfaceView {
    
    //VARIABLES
    //the renderer
    public GLRenderer renderer;

    //PUBLIC METHODS
    public StartUpGLSurfaceView(Context context) {
        
        //super call
        super(context);

        //create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        
        //create the renderer
        renderer = new GLRenderer();
        
        //set the configuration chooser
        setEGLConfigChooser(false);
        
        //set the renderer
        setRenderer(renderer);
        
        //set the rendering mode
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
    
}