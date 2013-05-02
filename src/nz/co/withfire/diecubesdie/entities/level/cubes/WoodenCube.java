/*********************************************************\
| A wooden cube, the most basic and weakest type if cube. |
|                                                         |
| @author David Saxon                                    |
\*********************************************************/

package nz.co.withfire.diecubesdie.entities.level.cubes;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;

public class WoodenCube extends Cube {

    //VARIABLES
    //the shape for the cube
    private Shape cube;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the translation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new wooden cube
    @param cube the shape for the cube*/
    public WoodenCube(Shape cube) {
        
        this.cube = cube;
    }
    
    //PUBLIC MEMBER FUNCTIONS
    @Override
    public void update() {
        
        //TODO:
        
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //calculate the mvp matrix
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        
        //draw the cube
        cube.draw(mvpMatrix);
    }
}
