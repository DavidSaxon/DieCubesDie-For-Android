/**************************************\
| Engine for the game start up.        |
| Loads initial resources into memory. |
|                                      |
| @author David Saxon                  |
\**************************************/

package nz.co.withfire.diecubesdie.startup;

import nz.co.withfire.diecubesdie.renderer.GLRenderer;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class StartUpEngine extends GLSurfaceView {

    //VARIABLES
    //the renderer (will be created by this engine)
    private GLRenderer renderer;
    
    
    public StartUpEngine(Context context) {
        
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
