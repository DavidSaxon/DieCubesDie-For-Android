/**********************************************************************\
| Stores where a finger point is on the screen and where it's previous |
| position was.                                                        |
|                                                                      |
| @author David Saxon                                                 |
\**********************************************************************/

package nz.co.withfire.diecubesdie.touch_control;

import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class TouchPoint {

    //VARIABLES
    //the current touch position
    private Vector2d currentPos = new Vector2d();
    //the first position of the touch point
    private Vector2d originalPos = new Vector2d();
    
    //is true if the touch point is being read
    private boolean read = false;
    //is true if the touch point has finished being read
    private boolean readDone = false;
    //is true if the touch point has finished
    private boolean finished = false;
    
    //CONSTRUCTOR
    /**Creates a new touch point
    @param pos the current position of the touch point*/
    public TouchPoint(Vector2d pos) {
        
        currentPos.copy(pos);
        originalPos.copy(pos);
    }
    
    //PUBLIC METHODS
    /**@param pos the new current position*/
    public void setCurrentPos(Vector2d pos) {
        
        currentPos.copy(pos);
    }
    
    /**The touch point is now being read*/
    public void read() {
        
        read = true;
    }
    
    /**Touch point reading has been completed*/
    public void readDone() {
        
        readDone = true;
    }
    
    /**Sets the touch point as finished*/
    public void finish() {
        
        finished = true;
    }
    
    /**@return the current position*/
    public Vector2d getCurrentPos() {
        
        return currentPos;
    }
    
    /**@return the original position*/
    public Vector2d getOriginalPos() {
        
        return originalPos;
    }

    /**@return if the touch point is being read*/
    public boolean isBeingRead() {
        
        return read;
    }
    
    /**@return if the touch point has finished*/
    public boolean finished() {
        
        return finished;
    }
    
    /**@return if the touch point should be removed*/
    public boolean shouldRemove() {
        
        return finished && readDone;
    }
}
