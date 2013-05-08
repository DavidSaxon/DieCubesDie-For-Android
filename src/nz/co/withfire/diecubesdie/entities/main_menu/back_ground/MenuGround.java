/*******************************************************************\
| Is simply the ground that displays in the background of the menu. |
|                                                                   |
| @author David Saxon                                              |
\*******************************************************************/

package nz.co.withfire.diecubesdie.entities.main_menu.back_ground;

import android.opengl.Matrix;
import android.util.Log;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.renderer.shapes.GLTriangleCol;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class MenuGround extends Drawable implements Entity {

    //VARIABLES
    //the shape of the ground
    private Shape ground;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new menu ground
    @param ground the shape to use for the ground*/
    public MenuGround(Shape ground) {
        
        this.ground = ground;
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {

        //do nothing
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        
        ground.draw(mvpMatrix);
    }

}
