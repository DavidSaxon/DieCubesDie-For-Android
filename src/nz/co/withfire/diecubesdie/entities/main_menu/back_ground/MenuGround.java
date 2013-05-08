/*******************************************************************\
| Is simply the ground that displays in the background of the menu. |
|                                                                   |
| @author David Saxon                                              |
\*******************************************************************/

package nz.co.withfire.diecubesdie.entities.main_menu.back_ground;

import android.opengl.Matrix;
import android.util.Log;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.shapes.GLTriangleCol;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class MenuGround extends Drawable implements Entity {

    //VARIABLES
    //the move speed of the ground
    private final float MOVE_SPEED = 0.5f / (90.0f / 2.0f);
    
    //the shapes of the ground
    private Shape ground;
    
    //the position of the ground
    private Vector2d pos = new Vector2d();
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new menu ground
    @param ground the shape to use for the ground*/
    public MenuGround(Shape ground) {
        
        this.ground = ground;
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {

        //move the ground
        pos.setX(pos.getX() + MOVE_SPEED * FpsManager.getTimeScale());
        
        if (pos.getX() > 4.0f) {
            
            pos.setX(0.0f);
        }
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
    
        //draw the centre ground
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        ground.draw(mvpMatrix);
        
        //draw the near left ground
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX() + 4.0f, pos.getY(), 0);
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        ground.draw(mvpMatrix);
        
        //draw the far left ground
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX() + 8.0f, pos.getY(), 0);
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        ground.draw(mvpMatrix);
        
        //draw the near right ground
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX() - 4.0f, pos.getY(), 0);
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        ground.draw(mvpMatrix);
        
        //draw the far right ground
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX() - 8.0f, pos.getY(), 0);
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        ground.draw(mvpMatrix);
    }

}
