/***************************************************************\
| Interface for any shape that can be rendered by the renderer. |
|                                                               |
| @author David Saxon                                           |
\***************************************************************/

package nz.co.withfire.diecubesdie.renderer.renderable.shape;

import nz.co.withfire.diecubesdie.renderer.renderable.Renderable;

public class Shape implements Renderable {

    //METHODS
    /**Draws the shape
    @param mvpMatrix the model view projection*/
    public void draw(float mvpMatrix[]) {
        
        //do nothing
    }
    
    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        
        
    }
}
