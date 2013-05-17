/***********************************\
| Utilities relating to collisions. |
|                                   |
| @author David Saxon              |
\***********************************/

package nz.co.withfire.diecubesdie.utilities;

import java.util.ArrayList;

import android.util.Log;
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.bounding.BoundingRect;
import nz.co.withfire.diecubesdie.entities.gui.Button;
import nz.co.withfire.diecubesdie.entities.gui.TouchPoint;

public class CollisionUtil {

    //PUBLIC METHODS
    /**Checks if the there is a collision between the 2 boundings
    @param a the first bounding
    @param b the second bounding
    @return the value of the collision, where 0 means there is no collision*/
    public static float collision(Bounding a, Bounding b) {
        
        //if there are two bounding rectangles
        if (a instanceof BoundingRect && b instanceof BoundingRect) {
            
            return collision((BoundingRect) a,(BoundingRect) b);
        }
        
        return 0;
    }
    
    /**Finds the button type that the greatest collision is occuring with
    @param touchPoint the touch point
    @param the buttons
    @return the button type a collision has occured with*/
    public static ValuesUtil.ButtonType checkButtonCollisions(
        TouchPoint touchPoint, ArrayList<Button> buttons) {
        
        //create an array that stores the collision value for each button
        float[] values = new float[buttons.size()];
        
        //iterate over the buttons and check their collisions
        for (int i = 0; i < buttons.size(); ++i) {
            
            values[i] = CollisionUtil.collision(
                touchPoint.getBounding(), buttons.get(i).getBounding());
        }
        
        //find the greatest value
        int greatest = -1;
        for (int i = 0; i < values.length; ++i) {
            
            if (values[i] > 0.0f) {
                
                if (greatest == -1) {
                    
                    greatest = i;
                }
                else if (values[i] > values[greatest]) {
                    
                    greatest = i;
                }
            }
        }
        
        if (greatest == -1) {
        
            return ValuesUtil.ButtonType.NONE;
        }
        
        return buttons.get(greatest).getType();
    }
    
    //PRIVATE METHODS
    /**Checks if there is a collision between 2 bounding rectangles
    @param a the first bounding rect
    @param b the second bounding rect
    @return the value of the collision*/
    private static float collision(BoundingRect a, BoundingRect b) {
        
        //get the values of the bounding rectangles
        float ahdx = a.getDim().getX() / 2.0f;
        float ahdy = a.getDim().getY() / 2.0f;
        float ax1  = a.getPos().getX() - ahdx;
        float ax2  = a.getPos().getX() + ahdx;
        float ay1  = a.getPos().getY() - ahdy;
        float ay2  = a.getPos().getY() + ahdy;
        float bhdx = b.getDim().getX() / 2.0f;
        float bhdy = b.getDim().getY() / 2.0f;
        float bx1  = b.getPos().getX() - bhdx;
        float bx2  = b.getPos().getX() + bhdx;
        float by1  = b.getPos().getY() - bhdy;
        float by2  = b.getPos().getY() + bhdy;
        
        //calculate the area of intersection
        float area = Math.max(0, Math.min(ax2, bx2) - Math.max(ax1, bx1)) *
            Math.max(0, Math.min(ay2, by2) - Math.max(ay1, by1));
        
        return area;
    }
}