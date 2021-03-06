/**********************************************\
| A Collection of important and useful values. |
|                                              |
| @author David Saxon                          |
\**********************************************/

package nz.co.withfire.diecubesdie.utilities;

import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class ValuesUtil {

    //VARIABLES
    //the program name
    public static final String TAG = "DieCubesDie";
    
    //the rev mob program id
    public static final String REVMOB_APP_ID =
        "518904a3100c778cb5000006";
    
    //the size of a float in bytes
    public static final int FLOAT_SIZE = 4;
    
    //the natural dimensions of the screen
    public static final Vector2d NATURAL_SCREEN_SIZE  =
        new Vector2d(1.77777777f, 1.0f);
    
    //connection time-out duration for internet actions (in ms)
    public static int TIME_OUT = 40000;
    
    //radians to degrees constant
    public static float RADIANS_TO_DEGREES = 57.2957795f;
    //degrees to radians constant
    public static float DEGREES_TO_RADIANS = 0.0174532925f;
    
    //ENUMERATORS
    //the different kind of buttons
    public enum ButtonType {
        
        NONE,
        
        //Main menu
        PLAY,
        STORE,
        PROGRESS,
        OPTIONS,
        MORE,
        FACEBOOK,
        GOOGLEPLUS,
        WITH_FIRE,
        
        //level select
        BEGIN
    }
    
    //the different level areas
    public enum LevelArea {
        
        PLAINS,
        MOUNTAINS,
        CITY,
        DESERT,
        JUNGLE,
        STRONGHOLD
    }
}
