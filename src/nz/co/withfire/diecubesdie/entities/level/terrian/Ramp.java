/**********************\
| A ramp in the level. |
|                      |
| @author David Saxon  |
\**********************/

package nz.co.withfire.diecubesdie.entities.level.terrian;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class Ramp extends Drawable {

    //ENUMERATORS
	//The direction the ramp can be facing
	public enum Direction {
		
		NORTH,
		EAST,
		SOUTH,
		WEST
	};
	
    //VARIABLES
    //the shape for the top of the ramp
    private Shape top;
    //the shape for the side of the ramp
    private Shape side;
    
    //the position of the ramp
    private Vector3d pos = new Vector3d();
    
    //the direction of the ramp
    private Direction direction;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new ramp
    @param top the shape for the top of the ramp
    @param side the shape for the side of the ramp
    @param pos the position of the ramp
    @param direction the direction of the ramp*/
    public Ramp(Shape top, Shape side, Vector3d pos, Direction direction) {
        
        //initialise variables
        this.top = top;
        this.side = side;
        this.pos.copy(pos);
        this.direction = direction;
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
        
        top.draw(mvpMatrix);
        side.draw(mvpMatrix);
    }
}
