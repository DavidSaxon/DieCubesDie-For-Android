/**********************\
| An gui overlay       |
|                      |
| @author David Saxon |
\**********************/

package nz.co.withfire.diecubesdie.entities.gui;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.entities.GUIDrawable;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.shapes.GLTriangleCol;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.renderer.text.Text;
import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class Overlay extends GUIDrawable {

    //VARIABLES
    //the shape for the overlay
    private GLTriangleCol overlay;
    
    //the position of the overlay
    private Vector2d pos = new Vector2d();
    
    //text the overlay should display
    private String text;
    //the text object for the text
    private Text textObject;
    //the size of the text
    private final float TEXT_SIZE = 0.12f;
    //the alignment of text
    private final Text.Align align = Text.Align.CENTRE;
    
    //is true if the overlay should fade
    private boolean fade;
    //the fade amount of the overlay
    private float fadeAmount = 1.0f;
    //the fade speed
    private final float FADE_SPEED = 0.01f;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new overlay
    @param overlay the shape for the overlay
    @param pos the position of the overlay
    @param text text the overlay should display
    @param fade is true if the overlay should fade*/
    public Overlay(Shape overlay, Vector2d pos, String text, boolean fade) {
        
        this.overlay = (GLTriangleCol) overlay;
        this.pos.copy(pos);
        this.fade = fade;
        if (this.fade) {
            
            this.overlay.getColour().setW(fadeAmount);
        }
        
        //create the text object
        if (text != null) {
            
            Vector2d textPos = new Vector2d(0.0f, TEXT_SIZE * 0.5f);
            textObject = new Text(textPos, TEXT_SIZE, align, text);
        }
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {

        //fade
        if (fade && fadeAmount > 0.0f) {
            
            overlay.getColour().setW(fadeAmount);
            fadeAmount -= FADE_SPEED * FpsManager.getTimeScale();
        }
    }

    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        Matrix.setIdentityM(tMatrix, 0);
        Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
        
        //multiply the matrix
        Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        
        //draw the cube
        overlay.draw(mvpMatrix);
        
        //draw the text
        if (textObject != null) {
            
            textObject.draw(viewMatrix, projectionMatrix);
        }
    }
    
    @Override
    public boolean shouldRemove() {
        
        return fadeAmount <= 0.0f;
    }
    
    /**@param text the new text for the overlay*/
    public void setText(String text) {
        
        textObject.setText(text);
    }
}
