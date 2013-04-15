/**********************************************************************\
| Interface the defines the methods for any object that is renderable. |
|                                                                      |
| @author David Saxon                                                  |
\**********************************************************************/

package nz.co.withfire.diecubesdie.renderer.renderable;

public interface Renderable {

    //METHODS
    /**Draws the renderable
    @param mvpMatrix the model view projection*/
    public void draw(float mvpMatrix[]);
}
