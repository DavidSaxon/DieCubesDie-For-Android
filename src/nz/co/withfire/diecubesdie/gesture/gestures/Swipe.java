/*****************************\
| Represents a swipe gesture. |
|                             |
| @author David Saxon        |
\*****************************/

package nz.co.withfire.diecubesdie.gesture.gestures;

import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class Swipe implements Gesture {

    //VARIABLES
    //the current position of the swipe
    private Vector2d pos = new Vector2d();
    //the original position of the swipe
    private Vector2d originalPos = new Vector2d();
    
    //is true if the swipe has been released
    private boolean finished;
    
    //CONSTRUCTOR
    /**Creates a new swipe gesture
    @param pos the current position of the swipe
    @param originalPos where the swipe started
    @param finished if the swipe has finished*/
    public Swipe(Vector2d pos, Vector2d originalPos, boolean finished) {
        
        this.pos.copy(pos);
        this.originalPos.copy(originalPos);
        this.finished = finished;
    }
    
    //PUBLIC METHODS
    /**@return if the current position of the swipe*/
    public Vector2d getPos() {
        
        return pos;
    }
    
    /**@return the starting position of the swipe*/
    public Vector2d getOriginalPos() {
        
        return originalPos;
    }
    
    /**@return if the swipe has finished*/
    public boolean finished() {
        
        return finished;
    }
}
