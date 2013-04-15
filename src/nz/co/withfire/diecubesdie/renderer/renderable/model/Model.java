/**********************************************************\
| A model consists of one or more renderables and can have |
| transformations applied to it.                           |
|                                                          |
| @author David Saxon                                      |
\**********************************************************/

package nz.co.withfire.diecubesdie.renderer.renderable.model;

import java.util.ArrayList;
import java.util.List;

import nz.co.withfire.diecubesdie.renderer.renderable.Renderable;

public class Model implements Renderable {

    //VARIABLES
    //the list of all the renderables the model contains
    private List<Renderable> renderables = new ArrayList<Renderable>();
    
    //CONSTRUCTOR
    /**Creates a new model using an List of renderables
    @param renderables the list of renderables in tge model*/
    public Model(List<Renderable> renderables) {
        
        //copy over the renderables
        for (Renderable r : renderables) {
            
            this.renderables.add(r);
        }
    }
    
    //PUBLIC METHODS
    @Override
    public void draw(float[] mvpMatrix) {
        
        //draw all the renderables
        for (Renderable r : renderables) {
            
            r.draw(mvpMatrix);
        }
    }

}
