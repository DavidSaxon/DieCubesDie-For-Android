/*****************************************\
| The cube in the background of the menu. |
|                                         |
| @author David Saxon                    |
\*****************************************/

package nz.co.withfire.diecubesdie.entities.main_menu.back_ground;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class MenuCube extends Drawable implements Entity {

    //VARIABLES
    //the roll speed of the cube
    private final float ROLL_SPEED = 1.5f;
    //the move speed of the cube
    private final float MOVE_SPEED = 1.0f / (90.0f / ROLL_SPEED);
    
    //the shape of the cube
    private Shape cube;
    
    //the position of the cube
    private Vector2d pos = new Vector2d(0.0f, -0.25f);
    //the roll rotation of the cube
    private float rollRot = 0.0f;
    //the side rotation of the cube
    private float sideRot = 0.0f;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new menu cube
    @param cube the shape to use for the cube*/
    public MenuCube(Shape cube) {
        
        this.cube = cube;
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {

        //calculate the amount we need to rotate and move by
        float rot = ROLL_SPEED * FpsManager.getTimeScale();
        float move = MOVE_SPEED * FpsManager.getTimeScale();
        
        if (rollRot < 90.0f) {
            
            //roll and move
            rollRot += rot;
            pos.setX(pos.getX() + move);
        }
        else {
            
            //reset roll
            rollRot = rollRot - 90.0f + rot;
            pos.setX(0.0f);
            sideRot += 90;
        }
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //apply transformations
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
        Matrix.translateM(tMatrix, 0, -0.5f, -0.25f, 0);
        Matrix.rotateM(tMatrix, 0, rollRot, 0, 0, 1);
        Matrix.translateM(tMatrix, 0, 0.5f, 0.5f, 0);
        Matrix.rotateM(tMatrix, 0, sideRot, 0, 0, 1);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the cube
        cube.draw(mvpMatrix);
    }
}
