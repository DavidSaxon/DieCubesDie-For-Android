/***************************\
| Displays a splash screen. |
|                           |
| @author David Saxon       |
\***************************/

package nz.co.withfire.diecubesdie.entities.level_load;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;

public class Splash extends GUIDrawable {

    //VARIABLES
    //the shape for the splash
    private Shape shape;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new level load splash
    @param shape the shape to use*/
    public Splash(Shape shape) {
        
        this.shape = shape;
    }
    
    //PUBLIC METHODS
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        Matrix.setIdentityM(tMatrix, 0);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the cube
        shape.draw(mvpMatrix);
    }
}
