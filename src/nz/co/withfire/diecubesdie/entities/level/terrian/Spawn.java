/*****************************\
| The spawn point in a level. |
|                             |
| @author David Saxon         |
\*****************************/

package nz.co.withfire.diecubesdie.entities.level.terrian;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class Spawn extends Drawable {

    //ENUMERATORS
    //the state of the spawner
    public enum State {
        
        IDLE,
        OPEN,
        CLOSE
    };
    
    //VARIABLES
    //the shape for the door of the spawn point
    private Shape door;
    //the shape for the inside of the spawn point
    private Shape inside;
    
    //the position of the spawn point
    private Vector3d pos = new Vector3d();
    
    //the current state of the spawner
    private State state = State.OPEN;
    
    //the open/speed
    private final float DOOR_SPEED = 0.03f;
    //the width of a door of the spawner
    private final float DOOR_WIDTH = 0.5f;
    
    //the current distance from the centre the doors are when opening or closing
    private float doorPos = 0.0f;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new spawn point
    @param door the shape for the door of the spawn point
    @param inside the inside of the spawn point
    @param pos the the position of the spawn point*/
    public Spawn(Shape door, Shape inside, Vector3d pos) {
        
        this.door = door;
        this.inside = inside;
        this.pos.copy(pos);
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //choose the action based on the state
        switch (state) {
        
            case OPEN: {
                
                open();
                break;
            }
            case CLOSE: {
                
                close();
                break;
            }
        }
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //INSIDE
        //transformations
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getZ(), pos.getY());
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        inside.draw(mvpMatrix);
        
        //DOOR 1
        //transformations
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX() - doorPos,
            pos.getZ(), pos.getY());
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        door.draw(mvpMatrix);
        
        //DOOR 2
        //transformations
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX() + doorPos + DOOR_WIDTH,
            pos.getZ(), pos.getY());
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        door.draw(mvpMatrix);
    }
    
    //PRIVATE METHODS
    /**Opens the spawn hatch*/
    private void open() {
        
        if (doorPos < DOOR_WIDTH) {
            
            doorPos += DOOR_SPEED;
        }
        else {
            
            doorPos = DOOR_WIDTH;
            state = State.IDLE;
        }
    }
    
    /**Closes the spawn hatch*/
    private void close() {
        
        
    }
}
