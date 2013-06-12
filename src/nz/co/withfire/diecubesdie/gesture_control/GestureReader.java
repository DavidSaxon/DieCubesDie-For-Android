/*****************************************************************************\
| Used to read gestures from touch input. Must be told which gesture types to |
| watch for.                                                                  |
|                                                                             |
| @author David Saxon                                                        |
\*****************************************************************************/

package nz.co.withfire.diecubesdie.gesture_control;

import java.util.ArrayList;
import java.util.List;

public class GestureReader {

    //ENUMERATORS
    //The different kinds of gestures that can be read
    public enum GestureType {
        
        TAP,
        SWIPE_UP,
        SWIPE_DOWN,
        SWIPE_LEFT,
        SWIPE_RIGHT
    }
    
    //VARIABLES
    //the gestures to read for
    private List<GestureType> gestureWatchList =
        new ArrayList<GestureType>();
    
    //CONSTRUCTOR
    /**Creates a new gesture reader
    @param gestures the gestures to watch for*/
    public GestureReader(GestureType ... gestures) {
        
        //add the gestures we need to watch for
        for (GestureType g : gestures) {
            
            if (!gestureWatchList.contains(g)) {
                
                gestureWatchList.add(g);
            }
        }
    }
    
    //PUBLIC METHODS
    
    
    /**Adds more gestures to the gesture watch list
    @param gestures new gestures to watch for*/
    public void addGesture(GestureType ... gestures) {
        
        //add the gestures we need to watch for
        for (GestureType g : gestures) {
            
            if (!gestureWatchList.contains(g)) {
                
                gestureWatchList.add(g);
            }
        }
    }
}
