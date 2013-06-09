/********************************************\
| Abstract class that all buttons implement. |
|                                            |
| @author David Saxon                       |
\********************************************/
package nz.co.withfire.diecubesdie.entities.gui.button;

import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public abstract class Button extends GUIDrawable {

    //VARIABLES
    //the position of the button
    protected Vector2d pos = new Vector2d();
    
    //the bounding box of the button
    protected Bounding bounding;
    
    //the button type this is
    protected ValuesUtil.ButtonType type;
    
    //Matrix
    //the model view projection matrix
    protected float[] mvpMatrix = new float[16];
    //the transformation matrix
    protected float[] tMatrix = new float[16];
    
    //PUBLIC METHODS
    @Override
    public void update() {

    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //do nothing
    }
    
    /**@return the bounding box of the button*/
    public Bounding getBounding() {
        
        //set the position of the bounding box before returning it
        bounding.setPos(pos);
        
        return bounding;
    }
    
    /**@return the type of button this is*/
    public ValuesUtil.ButtonType getType() {
        
        return type;
    }
}
