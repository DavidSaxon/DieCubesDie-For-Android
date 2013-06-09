/*******************************\
| A button on that uses an image|
|                               |
| @author David Saxon          |
\*******************************/

package nz.co.withfire.diecubesdie.entities.gui.button;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class ImageButton extends Button {

    //VARIABLES
    //the shape of the button
    private Shape button;
    
    //CONSTRUCTOR
    /**Creates a new main menu button
    @param button the shape of the button
    @param pos the position of the button
    @param bounding the bounding box of the button
    @param type the type of button this is*/
    public ImageButton(Shape button, Vector2d pos,
        Bounding bounding, ValuesUtil.ButtonType type) {
        
        this.button = button;
        this.pos.copy(pos);
        this.bounding = bounding;
        this.type = type;
        
        //set the position of the bounding box
        this.bounding.setPos(this.pos);
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
        button.draw(mvpMatrix);
        
        //draw the bounding box
        if (DebugUtil.DEBUG) {
            
            bounding.draw(mvpMatrix);
        }
    }
}
