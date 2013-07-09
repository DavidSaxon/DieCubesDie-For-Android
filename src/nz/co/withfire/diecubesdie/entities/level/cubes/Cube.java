/***************************************\
| Abstract class that all cubes extend. |
|                                       |
| @author David Saxon                  |
\***************************************/

package nz.co.withfire.diecubesdie.entities.level.cubes;

import android.opengl.Matrix;
import android.util.Log;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.level.terrian.Spawn;
import nz.co.withfire.diecubesdie.entities.level.terrian.Wall;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

abstract class Cube extends Drawable {
    
    //ENUMERATORS
    //the current state of the cube
    protected enum State {
        
        IDLE,
        SPAWN,
        MOVE,
        FINISH,
        DIE
    };
    
    //the two side's of the maze that the cube can follow
    public enum Side {
        
        LEFT,
        RIGHT
    };
    
    //the direction's that the cube can move
    protected enum Direction {
        
        NORTH,
        EAST,
        SOUTH,
        WEST
    };
    
    //VARIABLES
    //The primary shape of the cube
    protected Shape primary;
    //the secondary shape of the cube (only used by some types)
    protected Shape secondary;
    
    //the position of the cube
    protected Vector3d pos = new Vector3d();
    //the rolling animation rotation of the cube
    protected Vector3d rollingRot = new Vector3d();
    //the current rotation of the cube
    protected Vector3d currentRot = new Vector3d();
    
    //the side the cube is following
    protected Side side;
    
    //a reference to the entity map
    protected Entity entityMap[][][];
    
    //the speed at which the cube rotates
    protected final float ROT_SPEED = 1.5f;
    
    //the current state of the cube
    protected State state;
    
    //Spawn
    //the depth of the starting spawn position
    protected final Vector3d SPAWN_DEPTH = new Vector3d(0.0f, 0.0f, -2.0f);
    //the speed that spawn animation happens
    protected final float SPAWN_SPEED = 0.03f;
    //stores the starting position of the cube
    protected Vector3d startPos = new Vector3d();
    
    //Move
    //the current direction the cube is moving
    protected Direction direction = Direction.NORTH;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new cube
    @param primary the primary shape for the cube
    @param secondary the secondary shape for the cube
    @param pos the position of the cube
    @param side the side the cube will be following
    @param entityMap a reference to the entity map*/
    public Cube(Shape primary, Shape secondary, Vector3d pos,
        Side side, Entity entityMap[][][]) {
        
        //set variables
        this.primary = primary;
        this.secondary = secondary;
        this.pos.copy(pos);
        this.side = side;
        this.entityMap = entityMap;
        
        //init
        init();
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //choose the action based on the state
        switch (state) {
        
            case IDLE: {
                
                idle();
                break;
            }
            case SPAWN: {
                
                spawn();
                break;
            }
            case MOVE: {
                
                move();
                break;
            }
        }
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //shift to position
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getZ(), pos.getY());
        
        //rolling rotation
        shiftTransform();
        Matrix.rotateM(tMatrix, 0, rollingRot.getZ(), 0, 0, 1);
        Matrix.rotateM(tMatrix, 0, rollingRot.getY(), 0, 1, 0);
        Matrix.rotateM(tMatrix, 0, rollingRot.getX(), 1, 0, 0);
        
        //current rotation
        shiftBackTransform();
        Matrix.rotateM(tMatrix, 0, currentRot.getZ(), 0, 0, 1);
        Matrix.rotateM(tMatrix, 0, currentRot.getY(), 0, 1, 0);
        Matrix.rotateM(tMatrix, 0, currentRot.getX(), 1, 0, 0);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the shapes
        primary.draw(mvpMatrix);
        if (secondary != null) {
            
            secondary.draw(mvpMatrix);
        }
    }
    
    //PROTECTED METHODS
    /**Spawns the cube*/
    protected void spawn() {
        
        if (pos.getZ() < startPos.getZ()) {
            
            //move the cube up
            pos.setZ(pos.getZ() + (SPAWN_SPEED * FpsManager.getTimeScale()));
        }
        else {
            
            //set the pos
            pos.copy(startPos);
            //free a little memory
            startPos = null;
            //set idle state
            state = State.IDLE;
        }
    }
    
    /**Decides on the next action*/
    protected void idle() {
        
        //TODO: check if we are on a trap
        
        //path finding
        if (side == Side.LEFT) {
            
            //perform left side path finding
            leftPathFind();
            
            Log.v(ValuesUtil.TAG, direction + "");
            
            //get moving
            state = State.MOVE;
            move();
        }
        else {
            
            
        }
    }
    
    /**Performs left side path finding*/
    protected void leftPathFind() {
        
        //shorthand
        int x = (int) pos.getX();
        int y = (int) pos.getY();
        int z = (int) pos.getZ();
        
        switch (direction) {
        
            case NORTH: {
               
                if (canMoveTo(x + 1, y, z)) {
                    
                    //turn left
                    direction = Direction.EAST;
                    return;
                }
                else if (canMoveTo(x, y + 1, z)){
                    
                    //continue straight
                    return;
                }
                else if (canMoveTo(x - 1, y, z)) {
                    
                    //turn right
                    direction = Direction.WEST;
                    return;
                }
                
                //go backwards
                direction = Direction.SOUTH;
                return;
            }
            case EAST: {
                
                if (canMoveTo(x, y - 1, z)) {
                    
                    //turn left
                    direction = Direction.SOUTH;
                    return;
                }
                else if (canMoveTo(x + 1, y, z)){
                    
                    //continue straight
                    return;
                }
                else if (canMoveTo(x, y + 1, z)) {
                    
                    //turn right
                    direction = Direction.NORTH;
                    return;
                }
                
                //go backwards
                direction = Direction.WEST;
                return;
            }
            default: {
                
                break;
            }
        }
    }
    
    /**Moves the cube*/
    protected void move() {
        
        //the rotation increase
        float increase = ROT_SPEED * FpsManager.getTimeScale(); 
        
        if (direction == Direction.NORTH) {
            
            if (rollingRot.getX() < 90.0f) {
                
                rollingRot.setX(rollingRot.getX() + increase);
            }
            else {
                
                rollingRot.setX(rollingRot.getX() - 90.0f + increase);
                pos.setY(pos.getY() + 1.0f);
                currentRot.setX(currentRot.getX() + 90.0f);
                state = State.IDLE;
            }
        }
        else if (direction == Direction.EAST) {
            
            if (rollingRot.getZ() > -90.0f) {
                
                rollingRot.setZ(rollingRot.getZ() - increase);
            }
            else {
                
                rollingRot.setZ(rollingRot.getZ() + 90.0f - increase);
                pos.setX(pos.getX() + 1.0f);
                currentRot.setZ(currentRot.getZ() - 90.0f);
                state = State.IDLE;
            }
        }
    }
    
    /**Checks if the cube can move to the given position
    #NOTE: the position is not given in vector form to save some memory
    @param x the x position
    @param y the y position
    @param z the z position*/
    protected boolean canMoveTo(int x, int y, int z) {
        
        return !(entityMap[z][y][x] instanceof Wall ||
                entityMap[z][y][x] instanceof Spawn);
    }
    
    /**Shifts the transformation matrix for rolling rotation*/
    protected void shiftTransform() {
        
        switch (direction) {
        
            case NORTH: {
                
                Matrix.translateM(tMatrix, 0, 0.0f, -0.5f, 0.5f);
                break;
            }
            case EAST: {
                
                Matrix.translateM(tMatrix, 0, 0.5f, -0.5f, 0.0f);
                break;
            }
            case SOUTH: {
                
                
                break;
            }
            case WEST: {
                
                
                break;
            }
        }
    }
    
    /**Shifts back the transformation matrix after rolling rotation*/
    protected void shiftBackTransform() {
        
        switch (direction) {
        
            case NORTH: {
                
                Matrix.translateM(tMatrix, 0, 0.0f, 0.5f, -0.5f);
                break;
            }
            case EAST: {
                
                Matrix.translateM(tMatrix, 0, -0.5f, 0.5f, 0.0f);
                break;
            }
            case SOUTH: {
                
                
                break;
            }
            case WEST: {
                
                
                break;
            }
        }
    }
    
    /**Initialises the cube at the spawning position*/
    protected void init() {
        
        //set the state
        state = State.SPAWN;
        //store the starting position
        startPos.copy(pos);
        //set the cube to the spawn depth
        pos.add(SPAWN_DEPTH);
    }
}
