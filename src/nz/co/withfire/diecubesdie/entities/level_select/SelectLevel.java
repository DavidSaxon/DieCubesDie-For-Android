/******************************************\
| Represents a level that can be selected. |
|                                          |
| @author David Saxon                     |
\******************************************/

package nz.co.withfire.diecubesdie.entities.level_select;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class SelectLevel extends Drawable {

    //VARIABLES
    //the shape for the select level
    private Shape shape;
    
    //the position
    private Vector3d pos = new Vector3d();
    
    //the rotation of the select level
    private Vector3d rot = new Vector3d();
    
    //the turn rotation
    private Vector3d turnRot = new Vector3d();
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new select level
    @param shape the shape for the select level
    @param pos the position of the select level
    @param rot the rotation of the select level*/
    public SelectLevel(Shape shape, Vector3d pos, Vector3d rot) {
        
        //set variables
        this.shape = shape;
        this.pos.copy(pos);
        this.rot.copy(rot);
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
        
        //draw the cube
        shape.draw(mvpMatrix);
    }
}
