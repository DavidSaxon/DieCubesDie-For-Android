/*******************************************************\
| Used for showing all levels. Is displayed as a world. |
|                                                       |
| @author David Saxon                                  |
\*******************************************************/

package nz.co.withfire.diecubesdie.entities.level_select;

import android.opengl.Matrix;
import android.util.Log;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class LevelSelector extends Drawable {

    //VARIABLES
    //the shape for the level
    private Shape shape;
    
    //the position of the level selector
    private Vector2d pos = new Vector2d();
    
    //the rotations of the level
    private final Vector3d rotations[] = {
            
        new Vector3d(  0.0f,    0.0f,    0.0f),
        new Vector3d(  0.0f,  -90.0f,    0.0f),
        new Vector3d(  0.0f,  -90.0f,  -90.0f),
        new Vector3d(  0.0f,  -90.0f, -180.0f),
        new Vector3d(  0.0f, -180.0f, -180.0f),
        new Vector3d( 90.0f, -180.0f, -180.0f)
    };
    
    //the forwards rotations
    private final Vector3d forwardsRotations[] = {
       
        new Vector3d(  0.0f, -90.0f,   0.0f),
        new Vector3d(  0.0f,   0.0f, -90.0f),
        new Vector3d(  0.0f,   0.0f, -90.0f),
        new Vector3d(  0.0f, -90.0f,   0.0f),
        new Vector3d( 90.0f,   0.0f,   0.0f),
        new Vector3d( 90.0f,   0.0f,   0.0f)
    };
    
    //the backwards rotations
    private final Vector3d backwardsRotations[] = {
       
        new Vector3d(-90.0f,   0.0f,   0.0f),
        new Vector3d(  0.0f,  90.0f,   0.0f),
        new Vector3d(  0.0f,   0.0f,  90.0f),
        new Vector3d(  0.0f,   0.0f,  90.0f),
        new Vector3d(  0.0f,  90.0f,   0.0f),
        new Vector3d(-90.0f,   0.0f,   0.0f)
    };
    
    //rotation speed
    private final float ROT_SPEED = 1.5f;
    
    //is true if we are rotating
    private boolean rotate = false;
    //is true if we are rotating forwards
    private boolean forwards = true;
    
    //the current rotation
    private Vector3d rot = new Vector3d();
    //the angle to rotate to
    private Vector3d rotateTo = new Vector3d();
    
    //the number of areas the world has
    private final int NUMBER_OF_AREAS = 6;
    //the current area the world is in
    private int area = 0;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new level selector
    @param shape the shape for the world
    @param pos the position of the level selector*/
    public LevelSelector(Shape shape, Vector2d pos) {
        
        this.shape = shape;
        this.pos.copy(pos);
        
        rot.copy(rotations[area]);
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //rotation
        if (rotate) {
            
            if (forwards) {
                
                //perform the rotation
                if (rot.getX() < rotateTo.getX()) {
                    
                    rot.setX(rot.getX() + ROT_SPEED *
                        FpsManager.getTimeScale());
                }
                else if (rot.getY() > rotateTo.getY()) {
                    
                    rot.setY(rot.getY() - ROT_SPEED *
                        FpsManager.getTimeScale());
                }
                else if (rot.getZ() > rotateTo.getZ()) {
                    
                    rot.setZ(rot.getZ() - ROT_SPEED *
                        FpsManager.getTimeScale());
                }
                else {
                    
                    //rotation is done
                    rot.copy(rotations[area]);
                    rotate = false;
                }
            }
            else {
                
                //perform the rotation
                if (rot.getX() > rotateTo.getX()) {
                    
                    rot.setX(rot.getX() - ROT_SPEED *
                        FpsManager.getTimeScale());
                }
                else if (rot.getY() < rotateTo.getY()) {
                    
                    rot.setY(rot.getY() + ROT_SPEED *
                        FpsManager.getTimeScale());
                }
                else if (rot.getZ() < rotateTo.getZ()) {
                    
                    rot.setZ(rot.getZ() + ROT_SPEED *
                        FpsManager.getTimeScale());
                }
                else {
                    
                    //rotation is done
                    rot.copy(rotations[area]);
                    rotate = false;
                }
            }
        }
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //shift to position
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
        Matrix.rotateM(tMatrix, 0, rot.getY() - 180.0f, 0, 1, 0);
        Matrix.rotateM(tMatrix, 0, rot.getZ(), 0, 0, 1);
        Matrix.rotateM(tMatrix, 0, rot.getX(), 1, 0, 0);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the world
        shape.draw(mvpMatrix);
    }
    
    /**rotates the level selector
    @param forwards is true if to rotate forwards*/
    public void rotate(boolean forwards) {
        
        if (!rotate) {
            
            rotate = true;
            this.forwards = forwards;
            rotateTo.copy(rot);
            
            if (forwards) {
                
                //find the new angle to rotate to
                rotateTo.add(forwardsRotations[area]);
                
                //change up level
                ++area;
                if (area >= NUMBER_OF_AREAS) {
                    
                    area = 0;
                }
            }
            else {
                
              //find the new angle to rotate to
                rotateTo.add(backwardsRotations[area]);
                
                //change down level
                --area;
                if (area < 0) {
                    
                    area = NUMBER_OF_AREAS - 1;
                }
            }
        }
    }
    
    /**@return if the level is rotating*/
    public boolean isRotating() {
        
        return rotate;
    }
}
