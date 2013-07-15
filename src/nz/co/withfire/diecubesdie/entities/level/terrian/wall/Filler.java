/******************************************\
| A filler wall that fills beneath ground. |
|										   |
| @author David Saxon					   |
\******************************************/

package nz.co.withfire.diecubesdie.entities.level.terrian.wall;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class Filler extends Wall {

	//CONSTRUCTOR
	/**Creates a new filler
    @param side1 the shape for north and south sides of the wall
    @param side2 the shape for east and west sides of the wall
	@param pos the position of the filler*/
	public Filler(Shape side1, Shape side2, Vector3d pos) {
		
		super(null, side1, side2, pos);
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
        
        //DRAW SIDES
        //SOUTH
        side1.draw(mvpMatrix);
        
        //transformations
        Matrix.rotateM(tMatrix, 0, 90, 0, 1.0f, 0.0f);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //EAST
        side2.draw(mvpMatrix);
        
        //transformations
        Matrix.rotateM(tMatrix, 0, 90, 0, 1.0f, 0.0f);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //NORTH
        side1.draw(mvpMatrix);
        
        //transformations
        Matrix.rotateM(tMatrix, 0, 90, 0, 1.0f, 0.0f);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //WEST
        side2.draw(mvpMatrix);
    }
}
