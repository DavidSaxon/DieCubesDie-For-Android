/****************************************************************\
| Interface for any object that can be rendered by the renderer. |
|                                                                |
| @author David Saxon                                            |
\****************************************************************/

package nz.co.withfire.diecubesdie.renderer.renderable;

public interface Renderable {

    //METHODS
    /**Draws the renderable
    @param mvpMatrix the model view projection*/
    public void draw(float mvpMatrix[]);
}
