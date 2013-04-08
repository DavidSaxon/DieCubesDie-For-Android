/************************************************************************\
| The initial activity of the game, loads up the base resources for game |
|                                                                        |
| @author David Saxon                                                    |
\************************************************************************/

package nz.co.withfire.diecubesdie;

import nz.co.withfire.diecubesdie.startup.StartUpEngine;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class StartUpActivity extends Activity {

    //VARIABLES
    //the engine for this activity
    private StartUpEngine engine;
    
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
        engine = new StartUpEngine(this);
        
        //set the content view to the display
        setContentView(engine);
    }
}