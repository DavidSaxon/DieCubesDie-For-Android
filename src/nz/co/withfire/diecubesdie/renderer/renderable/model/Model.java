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

public class Model implements Renderable {

    //VARIABLES
    //the list of all the renderables the model contains
    private List<Renderable> renderables = new ArrayList<Renderable>();
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the translation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new model using an List of renderables
    @param renderables the list of renderables in the model*/
    public Model(List<Renderable> renderables) {
        
        //copy over the renderables
        for (Renderable r : renderables) {
            
            this.renderables.add(r);
        }
    }
    
    //PUBLIC METHODS
    /**Draws the shape
    @param mvpMatrix the model view projection*/
    public void draw(float mvpMatrix[]) {
        
        //do nothing
    }
    
    @Override
    public void draw(float[] viewMatrix, float[] projectionMatrix) {
        
        //set the translation matrix to the identity
        Matrix.setIdentityM(tMatrix, 0);
        
        //multiply the matrices
        Matrix.multiplyMM(mvpMatrix, 0, tMatrix, 0, viewMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw all the renderables
        for (Renderable r : renderables) {
            
            r.draw(mvpMatrix);
        }
    }

}
