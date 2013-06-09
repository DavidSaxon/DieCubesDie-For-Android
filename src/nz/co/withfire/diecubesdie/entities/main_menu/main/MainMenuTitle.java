/*****************************\
| The title at the main menu. |
|                             |
| @author David Saxon        |
\*****************************/

package nz.co.withfire.diecubesdie.entities.main_menu.main;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.TransformationsUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class MainMenuTitle extends GUIDrawable {

    //VARIABLES    
    //the shape of the title
    private Shape title;
    
    //the position of the title
    private Vector2d pos = new Vector2d();
    
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
        //set the position
        pos.copy(TransformationsUtil.getOpenGLDim());
        pos.setX(-pos.getX() - TransformationsUtil.scaleToScreen(0.62f));
        pos.setY(-pos.getY() - TransformationsUtil.scaleToScreen(0.70f));
    }
    
    //PUBLIC METHODS
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the cube
        title.draw(mvpMatrix);
    }
}
