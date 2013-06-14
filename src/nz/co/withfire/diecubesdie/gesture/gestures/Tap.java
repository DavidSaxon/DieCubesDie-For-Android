/**************************\
| Represents a tap gesture |
|                          |
| @author David Saxon     |
\**************************/

package nz.co.withfire.diecubesdie.gesture.gestures;

import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class Tap implements Gesture {

    //VARIABLES
    //the position of the tap
    private Vector2d pos = new Vector2d();
    
    //CONSTRUCTOR
    /**Creates a new tap gesture
    @param pos the position of the tap*/
    public Tap(Vector2d pos) {
        
        this.pos.copy(pos);
    }
    
    //PUBLIC METHODS
    /**@return the position of the tap*/
    public Vector2d getPos() {
        
        return pos;
    }
}
