/**********************************************\
| A Collection of important and useful values. |
|                                              |
| @author David Saxon                          |
\**********************************************/

package nz.co.withfire.diecubesdie.utilities;

public class ValuesUtil {

    //VARIABLES
    //the program name
    public static final String TAG = "DieCubesDie";
    
    //the rev mob program id
    public static final String REVMOB_APP_ID =
        "518904a3100c778cb5000006";
    
    //the size of a float in bytes
    public static final int sizeOfFloat = 4;
    
    //ENUMERATORS
    //the different kind of buttons
    public enum ButtonType {
        
        NONE,
        PLAY,
        STORE,
        OPTIONS,
        MORE,
        FACEBOOK,
        GOOGLEPLUS,
        WITH_FIRE
    }
}
