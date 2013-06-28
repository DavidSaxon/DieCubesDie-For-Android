/****************************************\
| The buttons for the level select grid. |
|                                        |
| @author David Saxon                   |
\****************************************/

package nz.co.withfire.diecubesdie.entities.level_select;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.entities.gui.button.Button;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class GridButton extends Button {

    //VARIABLES
    //the shape for the gird button
    private Shape shape;
    
    //is true if should be draw
    private boolean shouldDraw = true;
    
    //the shaders
    private int shaders[];
    
    //CONSTRUCTOR
    /**Creates a new grid button
    @param shape the shape for the button
    @param pos the position of the button
    @param bounding the bounding of the button
    @param shaders the shaders to use*/
    public GridButton(Shape shape, Vector2d pos,
        Bounding bounding, int shaders[]) {
        
        this.shape = shape;
        this.pos.copy(pos);
        this.bounding = bounding;
        this.shaders = shaders;
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        if (shouldDraw) {
            
            findShader();
            
            Matrix.setIdentityM(tMatrix, 0);
            Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
            
            //multiply the matrix
            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
            
            //draw the cube
            shape.draw(mvpMatrix);
            
            //draw the bounding box
            if (DebugUtil.DEBUG) {
                
                bounding.draw(mvpMatrix);
            }
        }
    }
    
    /**@param draw whether the button should be drawn or not*/
    public void shouldDraw(boolean draw) {
        
        shouldDraw = draw;
    }
    
    //PRIVATE METHODS
    /**Finds and sets the shader the button should be using*/
    private void findShader() {
        
        if (selected && !locked) {
            
            shape.setFragmentShader(shaders[0]);
        }
        else if (!selected && !locked) {
            
            shape.setFragmentShader(shaders[1]);
        }
        else if (selected && locked) {
            
            shape.setFragmentShader(shaders[2]);
        }
        else {
            
            shape.setFragmentShader(shaders[3]);
        }
    }
}
