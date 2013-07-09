/**********************\
| A wall in the level. |
|                      |
| @author David Saxon  |
\**********************/

package nz.co.withfire.diecubesdie.entities.level.terrian;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class Wall extends Drawable {

    //VARIABLES
    //the shape for side of the wall
    private Shape side;
    //the shape for the top of the wall
    private Shape top;
    
    //the position of the wall
    private Vector3d pos = new Vector3d();
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new wall
    @param side the shape for side of the wall
    @param top the shape for the top of the wall
    @param pos the position of the wall*/
    public Wall(Shape side, Shape top, Vector3d pos) {
        
        //initialise the variables
        this.side = side;
        this.top = top;
        this.pos.copy(pos);
    }
    
    //PUBLIC METHODS
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //transformations
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getZ(), pos.getY());
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        side.draw(mvpMatrix);
        top.draw(mvpMatrix);
    }
}
