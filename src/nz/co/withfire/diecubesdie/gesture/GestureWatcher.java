/************************************************************************\
| Watches for gestures. Must be told the types of gestures to watch for. |
|                                                                        |
| @author David Saxon                                                   |
\************************************************************************/

package nz.co.withfire.diecubesdie.gesture;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import nz.co.withfire.diecubesdie.gesture.gestures.Gesture;
import nz.co.withfire.diecubesdie.gesture.gestures.Tap;
import nz.co.withfire.diecubesdie.touch_control.TouchPoint;
import nz.co.withfire.diecubesdie.touch_control.TouchTracker;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class GestureWatcher {

    //ENUMERATOR
    //The different types of gestures
    public enum GestureType {
        
        TAP,
        SWIPE,
        PINCH
    }
    
    //VARIABLES
    //the maximum distance a point can travel for it to be considered a tap
    private final float TAP_DISTANCE = 0.1f;
    
    //the current gestures to watch for
    private List<GestureType> watchList = new ArrayList<GestureType>();
    
    //the touch tracker
    private TouchTracker touchTracker = new TouchTracker();
    
    //the current first touch point
    private TouchPoint point1;
    //the current second touch point
    private TouchPoint point2;
    
    //we are watching a swipe
    private boolean swipe = false;
    //we are watching a pinch
    private boolean pinch = false;
    
    //CONSTRUCTOR
    /**Creates a new gesture watcher
    @param gestures the gestures to watch for*/
    public GestureWatcher(GestureType ... gestures) {
        
        addGestures(gestures);
    }
    
    //PUBLIC METHODS
    /**Gets the current gesture*/
    public Gesture getGesture() {
        
        //the current gesture
        Gesture gesture = null;
        
        //get the new touch points if we need to
        if (point1 == null) {
            
            point1 = touchTracker.getPoint1();
        }
        if (point2 == null) {
            
            point2 = touchTracker.getPoint2();
        }
        
        //check for tap
        if (!swipe && !pinch) {
            
            gesture = checkTap();
        }
        
        //mark any finish touch points and clear them
        if (point1 != null && point1.finished()) {
            
            point1.readDone();
            point1 = null;
        }
        if (point2 != null && point2.finished()) {
            
            point2.readDone();
            point2 = null;
        }
        
        touchTracker.update();
        
        return gesture;
    }
    
    /**Inputs a touch event
    @param eventType the type of event
    @param index the index of the event
    @param pos the position of the event*/
    public void inputEvent(int eventType, int index, Vector2d pos) {
        
        //pass on to the touch tracker
        touchTracker.inputEvent(eventType, index, pos);
    }
    
    /**Adds new gestures to the watch list
    @param gestures the new gestures to watch for*/
    public void addGestures(GestureType ... gestures) {
        
        //add the gestures to the watch list if they are not already in there
        for (GestureType g : watchList) {
            
            if (!watchList.contains(g)) {
                
                watchList.add(g);
            }
        }
    }
    
    //PRIVATE METHODS
    /**Checks for a tap
    @return a tap gesture if one is read, else null*/
    private Gesture checkTap() {
        
        //if there is a point and it's finished
        if (point1 != null && point1.finished()) {
            
            //check how far it has moved
            if (point1.getOriginalPos().distance(point1.getCurrentPos()) <=
                    TAP_DISTANCE) {
                
                Log.v(ValuesUtil.TAG, "distance: " + point1.getOriginalPos().distance(point1.getCurrentPos()));
                
                //we have found a tap
                return new Tap(point1.getOriginalPos());
            }
        }
        
        return null;
    }
}
