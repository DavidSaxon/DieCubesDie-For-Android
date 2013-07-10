/*********************************************************************\
| Represents either a spawning point or a a finish point in the maze. |
|                                                                     |
| @author David Saxon                                                 |
\*********************************************************************/

package nz.co.withfire.diecubesdie.entities.level.terrian.entry;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public abstract class Entry extends Drawable {

    //ENUMERATORS
    //the state of the spawner
    public enum State {
        
        IDLE,
        OPEN,
        CLOSE
    };
    
    //VARIABLES
    //the shape for the door of the entry
    protected Shape door;
    //the shape for the inside of the entry
    protected Shape inside;
    
    //the position of the entry
    protected Vector3d pos = new Vector3d();
    
    //the current state of the spawner
    protected State state = State.OPEN;
    
    //the open/close speed
    protected final float DOOR_SPEED = 0.03f;
    //the width of the door
    protected final float DOOR_WIDTH = 0.5f;
    
    //the current distance from the centre the doors are when opening or closing
    private float doorPos = 0.0f;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new maze entry
    @param door the shape for the door of the spawn point
    @param inside the inside of the spawn point
    @param pos the the position of the spawn point*/
    public Entry(Shape door, Shape inside, Vector3d pos) {
        
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
    
    //PROTECTED METHODS
    /**Opens the spawn hatch*/
    protected void open() {
        
        if (doorPos < DOOR_WIDTH) {
            
            doorPos += DOOR_SPEED;
        }
        else {
            
            doorPos = 4 * DOOR_WIDTH;
            state = State.CLOSE;
        }
    }
    
    /**Closes the spawn hatch*/
    protected void close() {
        
        if (doorPos > 0.0f) {
            
            doorPos -= DOOR_SPEED;
        }
        else {
            
            doorPos = 0.0f;
            state = State.IDLE;
        }
    }
}
