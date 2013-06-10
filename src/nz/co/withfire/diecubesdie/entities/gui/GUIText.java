/***************************************\
| Plain Text to be displayed on the GUI |
|                                       |
| @author David Saxon                  |
\***************************************/

package nz.co.withfire.diecubesdie.entities.gui;

import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.renderer.text.Text;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class GUIText extends GUIDrawable {

    //VARIABLES
    //the text object
    private Text text;
    
    //the position
    private Vector2d pos = new Vector2d();
    
    //the string for the text
    private String string;
    //the size of the text
    private float size;
    //the alignment of text
    private Text.Align align;
    
    //CONSTRUCTOR
    /**Creates a new gui text
    @param pos the position of the text
    @param size the size of the text
    @param align the alignment of the text
    @param string the string for the text*/
    public GUIText(Vector2d pos, float size,
        Text.Align align, String string) {
        
        //initialise variables
        this.pos.copy(pos);
        this.size = size;
        this.align = align;
        this.string = string;
        
        //create the new text object
        text = new Text(pos, size, align, string);
    }
    
    //PUBLIC METHODS
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        text.draw(viewMatrix, projectionMatrix);
    }
    
    /**@param shader the fragment shader to use for the text*/
    public void setFragmentShader(int shader) {
        
        text.setFragmentShader(shader);
    }
}
