/**********************\
| An gui overlay       |
|                      |
| @author David Saxon |
\**********************/

package nz.co.withfire.diecubesdie.entities.gui;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class Overlay extends GUIDrawable implements Entity {

    //VARIABLES
    //the shape for the overlay
    private Shape overlay;
    
    //the position of the overlay
    private Vector2d pos = new Vector2d();
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new overlay
    @param overlay the shape for the overlay
    @param pos the position of the overlay*/
    public Overlay(Shape overlay, Vector2d pos) {
        
        this.overlay = overlay;
        this.pos.copy(pos);
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {

        //do nothing
    }

    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the cube
        overlay.draw(mvpMatrix);
    }
}
