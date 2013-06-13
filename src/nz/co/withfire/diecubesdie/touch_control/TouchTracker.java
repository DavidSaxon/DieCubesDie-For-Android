/***************************\
| Used to read touch input. |
|                           |
| @author David Saxon      |
\***************************/

package nz.co.withfire.diecubesdie.touch_control;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.MotionEvent;

import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class TouchTracker {
    
    //VARIABLES
    //the max distance that two points can be consider the same
    private final float SAME_DISTANCE = 5.0f;
    
    //the list of current touch points
    private List<TouchPoint> touchPoints =
        new ArrayList<TouchPoint>();
    
    //CONSTRUCTOR
    /**Creates a new gesture reader
    @param gestures the gestures to watch for*/
    public TouchTracker() {
        
    }
    
    //PUBLIC METHODS
    /**Updates the tracker by removing finished and read points*/
    public void update() {
        
        List<TouchPoint> removeList = new ArrayList<TouchPoint>();
        for (TouchPoint t : touchPoints) {
            
            //add the point to be removed
            if (t.finished()) {
                
                removeList.add(t);
            }
        }
        
        //remove the points
        touchPoints.remove(removeList);
    }
    
    /**Pass in a touch event
    @param eventType the event type
    @param pos the position of the event*/
    public void inputEvent(int eventType, Vector2d pos) {
        
        if (eventType == MotionEvent.ACTION_DOWN) {
            
            //add a new touch point
            touchPoints.add(new TouchPoint(pos));
        }
        else if (eventType == MotionEvent.ACTION_MOVE) {
            
            //search for the same point
            TouchPoint point = samePointSearch(pos);
            
            //no same point
            if (point == null) {
                
                //add a new touch point
                touchPoints.add(new TouchPoint(pos));
            }
            else {
                
                //update the point
                point.setCurrentPos(pos);
            }
        }
        else if (eventType == MotionEvent.ACTION_UP) {
            
            //search for the same point
            TouchPoint point = samePointSearch(pos);
            
            //no same point
            if (point == null) {
                
                //uh oh this shouldn't happen
                Log.v(ValuesUtil.TAG, "Trying to remove null touch point");
                
                //as a safety measure finish all current points
                for (TouchPoint t : touchPoints) {
                    
                    t.finish();
                }
            }
            else {
                
                //finish this point
                point.finish();
            }
        }
    }
    
    /**@return the list of touchPoints*/
    public List<TouchPoint> getTouchPoints() {
        
        return touchPoints;
    }
    
    //PRIVATE METHODS
    /**Finds the given position is part of a current touch point
    @param pos the position of the touch
    @return the touch point this is part of, null if not part of any
    current touch point*/
    private TouchPoint samePointSearch(Vector2d pos) {
        
        if (touchPoints.isEmpty()) {
            
            return null;
        }
        
        //iterate over the current touch points and find the closest
        TouchPoint closest = null;
        float distance = 0.0f;
        for (TouchPoint t : touchPoints) {
            
            //if this is the first point or the current closest
            if (closest == null || (!closest.finished() && 
                pos.distance(t.getCurrentPos()) < distance)) {
                
                closest = t;
            }
        }
        
        //check if the closest is close enough to be consider the same point
        if (closest != null && distance <= SAME_DISTANCE) {
            
            return closest;
        }
        
        return null;
    }
}
