package nz.co.withfire.diecubesdie.entities.level.terrian;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class Ground extends Drawable implements Entity {

    //VARIABLES
    //the position
    private Vector2d pos = new Vector2d();
    
    //the shape
    private Shape ground;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the translation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a ground tile
    @param pos the position of the tile
    @param shape the shape to use for the tile*/
    public Ground(Vector2d pos, Shape shape) {
        
        //set variables
        this.pos.copy(pos);
        ground = shape;
    }
    
    //PUBLIC MEMBER FUNCTIONS
    @Override
    public void update() {

        //do nothing
    }

    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //shift to position
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, 8 * pos.getX(), 0, 8 * pos.getY());
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        ground.draw(mvpMatrix);
    }
}
