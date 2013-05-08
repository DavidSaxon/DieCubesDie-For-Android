/*****************************\
| The title at the main menu. |
|                             |
| @author David Saxon        |
\*****************************/

package nz.co.withfire.diecubesdie.entities.main_menu.main;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class MainMenuTitle extends Drawable implements Entity {

    //VARIABLES    
    //the shape of the title
    private Shape title;
    
    //the position of the title
    private Vector2d pos = new Vector2d(1.125f, 0.295f);
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates new main menu title
    @param title the shape to use for the title*/
    public MainMenuTitle(Shape title) {
        
        this.title = title;
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
        Matrix.scaleM(tMatrix, 0, 0.75f, 0.75f, 0.75f);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the cube
        title.draw(mvpMatrix);
    }
}
