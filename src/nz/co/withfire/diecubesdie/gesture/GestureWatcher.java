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
import nz.co.withfire.diecubesdie.gesture.gestures.Pinch;
import nz.co.withfire.diecubesdie.gesture.gestures.Swipe;
import nz.co.withfire.diecubesdie.gesture.gestures.Tap;
import nz.co.withfire.diecubesdie.touch_control.TouchPoint;
import nz.co.withfire.diecubesdie.touch_control.TouchTracker;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class GestureWatcher {
    
    //VARIABLES
    //the maximum distance a point can travel for it to be considered a tap
    private final float TAP_DISTANCE = 0.2f;
    
    //the touch tracker
    private TouchTracker touchTracker = new TouchTracker();
    
    //the current first touch point
    private TouchPoint point1;
    //the current second touch point
    private TouchPoint point2;
    
    //we are watching a swipe
    private boolean swipe = false;
    
    //CONSTRUCTOR
    /**Creates a new gesture watcher
    @param gestures the gestures to watch for*/
    public GestureWatcher() {

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
        
        //check for pinch
        if (!swipe) {
            
            gesture = checkPinch();
        }
        //check for tap
        if (gesture == null && !swipe) {
            
            gesture = checkTap();
        }
        if (gesture == null) {
            
            gesture = checkSwipe();
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
    
    //PRIVATE METHODS
    /**Checks for a tap
    @return a tap gesture if one is read, else null*/
    private Gesture checkTap() {
        
        //if there is a point and it's finished
        if (point1 != null && point1.finished() && point2 == null) {
            
            //check how far it has moved
            if (point1.getOriginalPos().distance(point1.getCurrentPos()) <=
                    TAP_DISTANCE) {
                
                //we have found a tap
                return new Tap(point1.getOriginalPos());
            }
        }
        
        return null;
    }
    
    /**Checks for a swipe
    @return a swipe gesture if one is read, else null*/
    private Gesture checkSwipe() {
        
        //check if a swipe is currently being read
        if (swipe) {
            
            if (point1 != null) {
                
                return new Swipe(point1.getCurrentPos(),
                        point1.getOriginalPos(), point1.finished());
            } else {
                
                //dont do anything until the user has stop messing around
                if (point2 == null) {
                    
                    swipe = false;
                }
            }
        }
        else {
            
            if (point1 != null && point2 == null) {
                
                if (point1.getOriginalPos().distance(point1.getCurrentPos()) >
                        TAP_DISTANCE) {
                        
                    swipe = true;
                    return new Swipe(point1.getCurrentPos(),
                        point1.getOriginalPos(), point1.finished());
                }
            }
        }
        
        return null;
    }
    
    /**Checks for a pinch
    @return a pinch gesture if one is read, else null*/
    private Gesture checkPinch() {
        
        if (point1 != null && point2 != null) {
            
            return new Pinch(point1.getCurrentPos(), point2.getCurrentPos());
        }
        
        return null;
    }
}
