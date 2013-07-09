/************************************************************************\
| The initial activity of the game, loads up the base resources for game |
|                                                                        |
| @author David Saxon                                                   |
\************************************************************************/

package nz.co.withfire.diecubesdie;

//import com.revmob.RevMob;
//import com.revmob.RevMobAdsListener;
//import com.revmob.ads.link.RevMobLink;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.engine.main_menu.MainMenuEngine;
import nz.co.withfire.diecubesdie.engine.startup.StartUpEngine;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    //VARIABLES
    //the surface view of the activity
    private MainSurfaceView surfaceView;
    
    //PROTECTED METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        //super call
        super.onCreate(savedInstanceState);
            
        //set to full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //set up revmob
//        RevMob revmob = RevMob.start(this, ValuesUtil.REVMOB_APP_ID);
//        
//        Log.v(ValuesUtil.TAG, "3");
//        
//        RevMobAdsListener listener = new RevMobAdsListener() {
//            public void onRevMobAdReceived() {}
//            public void onRevMobAdNotReceived(String message) {}
//            public void onRevMobAdDisplayed() {}
//            public void onRevMobAdDismiss() {}
//            public void onRevMobAdClicked() {}
//        };
        
        //create the link
//        MainMenuEngine.link = revmob.createAdLink(this, listener);
        
        //set the display
        surfaceView = new MainSurfaceView(this);
        
        //set the content view to the display
        setContentView(surfaceView);
    }
    
    @Override
    protected void onResume() {
        
        //super call
        super.onResume();
        
        //set up revmob
//        RevMob revmob = RevMob.session();
//        RevMobAdsListener listener = new RevMobAdsListener() {
//            public void onRevMobAdReceived() {}
//            public void onRevMobAdNotReceived(String message) {}
//            public void onRevMobAdDisplayed() {}
//            public void onRevMobAdDismiss() {}
//            public void onRevMobAdClicked() {}
//        };
//        //create the link
//        MainMenuEngine.link = revmob.createAdLink(this, listener);
        
        //tell the engine we have resumed
        MainMenuEngine.resume = true;
    }
}

class MainSurfaceView extends GLSurfaceView {

    //VARIABLES
    //the current engine
    private Engine engine;
    
    //the renderer for the surface
    private GLRenderer renderer;
    
    //CONSTRUCTOR
    public MainSurfaceView(Context context) {
        
        //super call
        super(context);
        
        //create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        
        //create a new start up engine
        engine = new StartUpEngine(context);
        
        //create a new renderer
        renderer = new GLRenderer(context, engine);
        
        //set the configuration chooser
        setEGLConfigChooser(true);
        
        //set the renderer
        setRenderer(renderer);
        
        //set the rendering mode
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
    
    //PUBLIC METHODS
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        
        //pass the event on to the engine
        renderer.touchEvent(e);
        
        return true;
    }
}