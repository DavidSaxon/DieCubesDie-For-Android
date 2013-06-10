/******************************************\
| Represents a level that can be selected. |
|                                          |
| @author David Saxon                     |
\******************************************/

package nz.co.withfire.diecubesdie.entities.level_select;

import android.opengl.Matrix;
import android.util.Log;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class SelectLevel extends Drawable {

    //VARIABLES
    //the shape for the select level
    private Shape shape;
    
    //the position
    private Vector3d pos = new Vector3d();
    
    //the rotation of the select level
    private Vector3d rot = new Vector3d();
    
    //is true if the level is locked
    private boolean locked;
    
    //the turn rotation
    private Vector3d turnRot = new Vector3d();
    
    //is true if the level is selected
    private boolean selected = false;
    
    //the fragment shaders
    private int shaders[];
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new select level
    @param shape the shape for the select level
    @param shaders the fragment shaders
    @param pos the position of the select level
    @param rot the rotation of the select level
    @param locked if the level is locked*/
    public SelectLevel(Shape shape, int shaders[], Vector3d pos,
        Vector3d rot, boolean locked) {
        
        //set variables
        this.shape = shape;
        this.shaders = shaders;
        this.pos.copy(pos);
        this.rot.copy(rot);
        this.locked = locked;
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //apply transformations
        Matrix.setIdentityM(tMatrix, 0);
        
        Matrix.rotateM(tMatrix, 0, turnRot.getY(), 0, 1.0f, 0);
        Matrix.rotateM(tMatrix, 0, turnRot.getX(), 1.0f, 0, 0);
        
        Matrix.rotateM(tMatrix, 0, rot.getY(), 0, 1.0f, 0);
        Matrix.rotateM(tMatrix, 0, rot.getX(), 1.0f, 0, 0);
        
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), pos.getZ());
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //set the shader
        setShader();
        
        //draw the cube
        shape.draw(mvpMatrix);
    }
    
    /**Selects the level
    @param select if the level is to be selected or not*/
    public void select(boolean select) {
        
        this.selected = select;
    }
    
    //PRIVATE METHODS
    /**Sets the fragment shader based on the current state*/
    private void setShader() {
        
        if (!locked && !selected) {
            
            Log.v(ValuesUtil.TAG, "here");
            
            shape.setFragmentShader(shaders[0]);
        }
        else if (!locked && selected) {
            
            shape.setFragmentShader(shaders[1]);
        }
        else if (locked && !selected) {
            
            shape.setFragmentShader(shaders[2]);
        }
        else {
            
            shape.setFragmentShader(shaders[3]);
        }
    }
}
