/********************************\
| A 2d single point touch point. |
|                                |
| @author David Saxon           |
\********************************/

package nz.co.withfire.diecubesdie.entities.input;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class TouchPoint extends GUIDrawable implements Entity {

    //VARIABLES
    //the shape for the touch point (only in debug mode)
    private Shape point;
    
    //the position of the touch point
    private Vector2d pos = new Vector2d();
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTORS
    /**Creates a new touch point
    @param pos the position of the touch point*/
    public TouchPoint(Vector2d pos) {
        
        this.pos.copy(pos);
    }
    
    /**Creates a new touch point in debug mode
    @param point the shape for the point
    @param pos the position of the touch point*/
    public TouchPoint(Shape point, Vector2d pos) {
        
        this.point = point;
        this.pos.copy(pos);
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //do nothing
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //only draw in debug mode
        if (DebugUtil.DEBUG) {

            Matrix.setIdentityM(tMatrix, 0);
            Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
            
            //multiply the matrix
            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
            //draw the point
            point.draw(mvpMatrix);
        }
    }
}
