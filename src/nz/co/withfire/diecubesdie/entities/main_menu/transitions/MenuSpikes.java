package nz.co.withfire.diecubesdie.entities.main_menu.transitions;

import android.opengl.Matrix;
import android.util.Log;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class MenuSpikes extends MenuTransition {

    //VARIABLES
    //the shape for the spikes
    private Shape spikes;
    
    //the final position of the left spikes
    private final Vector2d LEFT_END_POS = new Vector2d(1.0f, 0.0f);
    //the final position of the right spikes
    private final Vector2d RIGHT_END_POS = new Vector2d(-1.0f, 0.0f);
    
    //the move speed of the spikes
    private final float MOVE_SPEED = 0.06f;
    //the acceleration speed of the spikes
    private final float ACC_SPEED = 0.005f;
    //the current speed of the spikes
    private float currentSpeed = MOVE_SPEED;
    
    //the current position of the left spikes
    private Vector2d leftPos =
        new Vector2d(-TransformationsUtil.getOpenGLDim().getX() + 1.5f, 0.0f);
    //the current position of the right spikes
    private Vector2d rightPos =
        new Vector2d(TransformationsUtil.getOpenGLDim().getX() - 1.5f, 0.0f);
    
    //is true if the spikes are bouncing back
    private boolean back = false;
    //the force that the spikes bounce back with
    private float backForce = 1.0f;
    
    //is true once the transition is complete
    private boolean complete = false;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates new menu spikes
    @param overlay the shape for the spikes*/
    public MenuSpikes(Shape spikes) {
        
        this.spikes = spikes;
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //increase the move speed of the spikes
        currentSpeed += ACC_SPEED;
        //cap at the top speed
        if (currentSpeed > MOVE_SPEED) {
            
            currentSpeed = MOVE_SPEED;
        }
        
        
        if (!back) {
            
            //move the spikes
            if (rightPos.getX() < RIGHT_END_POS.getX()) {
                
                leftPos.setX(leftPos.getX() -
                    currentSpeed * FpsManager.getTimeScale());
                rightPos.setX(rightPos.getX() +
                    currentSpeed * FpsManager.getTimeScale());
            }
            else {
                
                //bounce back
                if (backForce > 0.4f) {
                    
                    //bounce back
                    back = true;
                    backForce = backForce / 2.0f;
                    currentSpeed = MOVE_SPEED / 3.0f;
                }
                //we are done
                else {
                    
                    leftPos.copy(LEFT_END_POS);
                    rightPos.copy(RIGHT_END_POS);
                    complete = true;
                }
            }
        }
        else {
            
            if (rightPos.getX() > -backForce + RIGHT_END_POS.getX()) {
                
                leftPos.setX(leftPos.getX() +
                    currentSpeed * FpsManager.getTimeScale());
                rightPos.setX(rightPos.getX() -
                    currentSpeed * FpsManager.getTimeScale());
            }
            else {
                
                back = false;
                currentSpeed = 0.0f;
            }
        }
    }

    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //left spikes
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, leftPos.getX(),
            leftPos.getY(), 0.0f);
        Matrix.scaleM(tMatrix, 0, -1.0f, -1.0f, 0.0f);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the spikes
        spikes.draw(mvpMatrix);
        
        //right spikes
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, rightPos.getX(),
            rightPos.getY(), 0.0f);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the spikes
        spikes.draw(mvpMatrix);
    }
    
    @Override
    public boolean complete() {
        
        return complete;
    }
}
