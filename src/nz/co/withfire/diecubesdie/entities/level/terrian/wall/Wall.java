/**********************\
| A wall in the level. |
|                      |
| @author David Saxon  |
\**********************/

package nz.co.withfire.diecubesdie.entities.level.terrian.wall;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class Wall extends Drawable {

    //VARIABLES
    //the shape for the top of the wall
    protected Shape top;
    //the shape for north and south sides of the wall
    protected Shape side1;
    //the shape for the east and west sides of the wall
    protected Shape side2;
    
    //the position of the wall
    protected Vector3d pos = new Vector3d();
    
    //Matrix
    //the model view projection matrix
    protected float[] mvpMatrix = new float[16];
    //the transformation matrix
    protected float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new wall
    @param top the shape for the top of the wall
    @param side1 the shape for north and south sides of the wall
    @param side2 the shape for east and west sides of the wall
    @param pos the position of the wall*/
    public Wall(Shape top, Shape side1, Shape side2, Vector3d pos) {
        
        //initialise the variables
        this.top = top;
        this.side1 = side1;
        this.side2 = side2;
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
        
        top.draw(mvpMatrix);
        
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
