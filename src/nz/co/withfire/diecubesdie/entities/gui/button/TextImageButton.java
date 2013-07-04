/*********************************************\
| A button that has text infront of an image. |
|                                             |
| @author David Saxon                         |
\*********************************************/

package nz.co.withfire.diecubesdie.entities.gui.button;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.renderer.text.Text;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class TextImageButton extends Button {

    //VARIABLES
    //the text for the button
    private Text text;
    //the image for the button
    private Shape image;
    
    //CONSTRUCTOR
    /**Creates a new text and image button
    @param text the text for the button
    @param image the image for the button
    @param bounding the bounding box of the button
    @param type the type of button this is*/
    public TextImageButton(Text text, Shape image,
        Bounding bounding, ValuesUtil.ButtonType type) {
        
        //initialise variables
        this.text = text;
        this.image = image;
        this.pos.copy(text.getPos());
        this.bounding = bounding;
        this.type = type;
        
        //set the position of the bounding box
        this.bounding.setPos(this.pos);
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
                
        //transformations
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
        
        //TODO: if the text is too big we have to scale the button
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the image
        image.draw(mvpMatrix);
        
        //draw the text
        text.draw(viewMatrix, projectionMatrix);
        
        //draw the bounding box
        if (DebugUtil.DEBUG) {
            
            //transformations
            Matrix.setIdentityM(tMatrix, 0);
            Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
            
            //multiply the matrix
            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
            
            bounding.draw(mvpMatrix);
        }
    }
}
