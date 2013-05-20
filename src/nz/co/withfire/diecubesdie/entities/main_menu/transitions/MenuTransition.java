/*************************************\
| Abstract class for menu transitions |
|                                     |
| @author David Saxon                |
\*************************************/

package nz.co.withfire.diecubesdie.entities.main_menu.transitions;

import nz.co.withfire.diecubesdie.entities.GUIDrawable;

public abstract class MenuTransition extends GUIDrawable {

    //VARIABLES
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //PUBLIC METHODS
    /**@return if the transition has completed*/
    public boolean complete() {
        
        return false;
    }
}
