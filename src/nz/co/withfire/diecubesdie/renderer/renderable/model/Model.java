/**********************************************************\
| A model consists of one or more renderables and can have |
| transformations applied to it.                           |
|                                                          |
| @author David Saxon                                      |
\**********************************************************/

package nz.co.withfire.diecubesdie.renderer.renderable.model;

import java.util.ArrayList;
import java.util.List;

import android.opengl.Matrix;

import nz.co.withfire.diecubesdie.renderer.renderable.Renderable;
import nz.co.withfire.diecubesdie.renderer.renderable.shape.Shape;

public class Model implements Renderable {

    //VARIABLES
    //the list of all the renderables the model contains
    private List<Shape> shapes = new ArrayList<Shape>();
    
    //CONSTRUCTOR
    /**Creates a new model using an List of shapes
    @param shapes the list of shapes in the model*/
    public Model(List<Shape> shapes) {
        
        //copy over the shapes
        for (Shape s : shapes) {
            
            this.shapes.add(s);
        }
    }
    
    //PUBLIC METHODS
    @Override
    public void draw(float[] mvpMatrix) {
        
        //draw all the shapes
        for (Shape s : shapes) {
            
            s.draw(mvpMatrix);
        }
    }

}
