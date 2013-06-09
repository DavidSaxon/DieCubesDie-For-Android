/*********************************\
| The background at level select. |
|                                 |
| @author David Saxon            |
\*********************************/

package nz.co.withfire.diecubesdie.entities.level_select;

import java.util.Random;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;

public class Background extends GUIDrawable {

    //VARIABLES
    //the amount to shift the z position to make the stars twinkle
    private final float TWINKLE_SHIFT = 90.0f;
    //the rotation speed of the background
    private final float ROT_SPEED = 0.01f;
    
    //the shape for the background
    private Shape shape;
    
    //the z position of the background
    private float z = 0.0f;
    
    //the z rotation of the background
    private float zRot = 0.0f;
    
    //random number generator
    private Random rand = new Random();
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new level select background
    @param shape the shape for the background*/
    public Background(Shape shape) {
        
        this.shape = shape;
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //randomly shift the z position to create a twinkle effect
        z = (rand.nextFloat() / TWINKLE_SHIFT);
        
        //rotate the background
        zRot += ROT_SPEED;
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //apply transformations
        Matrix.setIdentityM(tMatrix, 0);
        
        Matrix.translateM(tMatrix, 0, 0.0f, 0.0f, z);
        Matrix.rotateM(tMatrix, 0, zRot, 0, 0, 1.0f);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        shape.draw(mvpMatrix);
    }
}
