/**************************************************************************\
| The ground for the level stores the entire in one object since ground is |
| effectively an empty square.                                             |
|                                                                          |
| @author David Saxon                                                      |
\**************************************************************************/

package nz.co.withfire.diecubesdie.entities.level.terrian;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class Ground extends Drawable {

    //VARIABLES
    //the size of the level
    private Vector3d dim = new Vector3d();
    
    //the array of ground shapes
    private Shape shapes[][][];
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new ground object
    @param dim the dimensions of the ground*/
    public Ground(Vector3d dim) {
        
        this.dim.copy(dim);
        
        //create the number of floors
        shapes = new Shape[(int) this.dim.getZ()]
            [(int) this.dim.getY()][(int) this.dim.getX()];
    }
    
    //PUBLIC METHODS
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //iterate over the ground and draw
        for (int z = 0; z < shapes.length; ++z) {
            for (int y = 0; y < shapes[0].length; ++y) {
                for (int x = 0; x < shapes[0][0].length; ++x) {
                 
                    //transformations
                    Matrix.setIdentityM(tMatrix, 0);
                    Matrix.translateM(tMatrix, 0, x, z, y);
                    
                    //multiply the matrix
                    Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
                    Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix,
                        0, mvpMatrix, 0);
                    
                    if (shapes[z][y][x] != null) {
                        
                        //draw the ground
                        shapes[z][y][x].draw(mvpMatrix);
                    }
                }
            }
        }
    }
    
    /**Adds a the given shape add the given position
    @param shape the shape to add
    @param pos the position to add the shape add*/
    public void add(Shape shape, Vector3d pos) {
        
        shapes[(int) pos.getZ()][(int) pos.getY()]
            [(int) pos.getX()] = shape;
    }
}
