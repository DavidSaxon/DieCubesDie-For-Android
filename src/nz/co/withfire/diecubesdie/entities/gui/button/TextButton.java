/*******************************\
| A button on that uses an image|
|                               |
| @author David Saxon          |
\*******************************/

package nz.co.withfire.diecubesdie.entities.gui.button;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.renderer.text.Text;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class TextButton extends Button {

    //VARIABLES
    //the text for the button
    private Text text;
    
    //CONSTRUCTOR
    /**Creates a new main menu button
    @param text the text for the button
    @param bounding the bounding box of the button
    @param type the type of button this is*/
    public TextButton(Text text, Bounding bounding,
        ValuesUtil.ButtonType type) {
        
        this.text = text;
        this.pos.copy(text.getPos());
        this.bounding = bounding;
        this.type = type;
        
        //set the position of the bounding box
        this.bounding.setPos(this.pos);
    }

    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
                
        //draw the text
        text.draw(viewMatrix, projectionMatrix);
        
        //draw the bounding box
        if (DebugUtil.DEBUG) {
            
            Matrix.setIdentityM(tMatrix, 0);
            Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
            
            //multiply the matrix
            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
            
            bounding.draw(mvpMatrix);
        }
    }
}
