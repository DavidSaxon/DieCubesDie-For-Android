/**********************************************************************\
| Interface the represents any shape that can be drawn by the renderer |
|                                                                      |
| @author David Saxon                                                  |
\**********************************************************************/

package nz.co.withfire.diecubesdie.renderer.shapes;

public interface Shape {

    //PUBLIC METHODS
    /**Draws the shape
    @param mvpMatrix the model view projection matrix*/
    public void draw(float[] mvpMatrix);
    
    /**Sets the new fragment shader for the shape
    @param shader the new fragment shader*/
    public void setFragmentShader(int shader);
}
