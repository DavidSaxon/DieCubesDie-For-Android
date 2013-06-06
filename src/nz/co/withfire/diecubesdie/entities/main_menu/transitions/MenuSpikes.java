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
    
    //the current position of the left spikes
    private Vector2d leftPos =
        new Vector2d(-TransformationsUtil.getOpenGLDim().getX() + 1.5f, 0.0f);
    //the current position of the right spikes
    private Vector2d rightPos =
        new Vector2d(TransformationsUtil.getOpenGLDim().getX() - 1.5f, 0.0f);
    
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
        
        //move the spikes
        if (rightPos.getX() < RIGHT_END_POS.getX()) {
            
            leftPos.setX(leftPos.getX() -
                MOVE_SPEED * FpsManager.getTimeScale());
            rightPos.setX(rightPos.getX() +
                MOVE_SPEED * FpsManager.getTimeScale());
        }
        else {
                
            //we are done
            leftPos.copy(LEFT_END_POS);
            rightPos.copy(RIGHT_END_POS);
            complete = true;
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
