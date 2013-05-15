/**********************************************\
| Abstract class that all bounding areas implement. |
|                                                   |
| @author David Saxon                              |
\***************************************************/

package nz.co.withfire.diecubesdie.bounding;

import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public abstract class Bounding {

    //VARIABLES
    //the position of the bounding box
    private Vector2d pos = new Vector2d();
    
    //PUBLIC METHODS
    /**@param pos the position of the bounding box*/
    public void setPos(Vector2d pos) {
        
        this.pos.copy(pos);
    }
    
    /**@return the position of the bounding box*/
    public Vector2d getPos() {
        
        return pos;
    }
    
    /**Draws the bounding box for debug mode
    @param mvpMatrix the model view projection matrix*/
    public void draw(float[] mvpMatrix) {
        
        //do nothing
    }
}
