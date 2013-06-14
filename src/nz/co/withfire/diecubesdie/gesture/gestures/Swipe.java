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
    
    //is true if the swipe has been released
    private boolean finished;
    
    //CONSTRUCTOR
    /**Creates a new swipe gesture
    @param pos the current position of the swipe
    @param finished if the swipe has finished*/
    public Swipe(Vector2d pos, boolean finished) {
        
        this.pos.copy(pos);
        this.finished = finished;
    }
    
    //PUBLIC METHODS
    /**@return if the current position of the swipe*/
    public Vector2d getPos() {
        
        return pos;
    }
    
    /**@return if the swipe has finished*/
    public boolean finished() {
        
        return finished;
    }
}
