/*****************************\
| A two finger touch gesture. |
|                             |
| @author David Saxon         |
\*****************************/

package nz.co.withfire.diecubesdie.gesture.gestures;

import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class Pinch implements Gesture {

    //VARIABLES
    //the position of the first point
    private Vector2d pos1 = new Vector2d();
    //the position of the second point
    private Vector2d pos2 = new Vector2d();
    //the centre position of the two points
    private Vector2d centrePos = new Vector2d();
    
    //CONSTRUCTOR
    /**Creates a new pinch gesture
    @param pos1 the position of the first point
    @param pos2 the position of the second point*/
    public Pinch(Vector2d pos1, Vector2d pos2) {
        
        this.pos1.copy(pos1);
        this.pos2.copy(pos2);
        
        //find the centre position
        findCentre();
    }
    
    //PUBLIC METHODS
    /**@return the position of the first point*/
    public Vector2d getPos1() {
        
        return pos1;
    }
    
    /**@return the position of the second point*/
    public Vector2d getPos2() {
        
        return pos2;
    }
    
    /**@return the centre position*/
    public Vector2d getCentrePos() {
        
        return centrePos;
    }
    
    //PRIVATE METHODS
    /**Finds the centre position*/
    private void findCentre() {
        
        //find the distance between the points
        float xDis = Math.abs(pos1.getX() - pos2.getX());
        float yDis = Math.abs(pos1.getY() - pos2.getY());
        
        //find the greatest x and y positions
        float greatestX = Math.max(pos1.getX(), pos2.getX());
        float greatestY = Math.max(pos1.getY(), pos2.getY());
        
        //set the centre pos
        centrePos.set(greatestX, greatestY);
    }
}
