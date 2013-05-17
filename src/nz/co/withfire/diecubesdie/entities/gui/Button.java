/****************************\
| A button on the main menu. |
|                            |
| @author David Saxon       |
\****************************/

package nz.co.withfire.diecubesdie.entities.gui;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.bounding.Bounding;
import nz.co.withfire.diecubesdie.engine.menu.MainMenuEngine;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class Button extends GUIDrawable implements Entity {

    //VARIABLES
    //the shape of the button
    private Shape button;
    
    //the position of the button
    private Vector2d pos = new Vector2d();
    
    //the bounding box of the button
    private Bounding bounding;
    
    //the button type this is
    private ValuesUtil.ButtonType type;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new main menu button
    @param button the shape of the button
    @param pos the position of the button
    @param bounding the bounding box of the button
    @param type the type of button this is*/
    public Button(Shape button, Vector2d pos,
        Bounding bounding, ValuesUtil.ButtonType type) {
        
        this.button = button;
        this.pos.copy(pos);
        this.bounding = bounding;
        this.type = type;
        
        //set the position of the bounding box
        this.bounding.setPos(this.pos);
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {

    }

    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the cube
        button.draw(mvpMatrix);
        
        //draw the bounding box
        if (DebugUtil.DEBUG) {
            
            bounding.draw(mvpMatrix);
        }
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
