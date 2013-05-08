package nz.co.withfire.diecubesdie.entities.startup;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.renderer.shapes.GLTriangleCol;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class Splash extends Drawable implements Entity {

    //VARIABLES
    //the shape of the splash screen
    private Shape splash;
    //the shape to use for the fade in
    private GLTriangleCol fader;
    
    //is true once the splash has finished loading
    private boolean fadeComplete = false;
    //the fade speed of the splash
    private float fadeSpeed = 0.5f;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new splash screen
    @param splash the shape to use for the splash screen
    @param fader the shape to use for the fade in*/
    public Splash(Shape splash, Shape fader) {
        
        //initialise variables
        this.splash = splash;
        this.fader = (GLTriangleCol) fader;
        
        //set the fader to black
        this.fader.setColour(new Vector4d(0.0f, 0.0f, 0.0f, 1.0f));
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //fade in
        if (fader.getColour().getW() > 0.0) {
            
            //change the alpha value of the fader
            fader.getColour().setW(fader.getColour().getW() - fadeSpeed);
            fader.reloadColour();
        }
        else {
            
            fadeComplete = true;
        }
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //calculate the mvp matrix
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        
        //draw the models
        splash.draw(mvpMatrix);
        fader.draw(mvpMatrix);
    }
    
    /**@return if the fade has finished*/
    public boolean fadeFinished() {
        
        return fadeComplete;
    }
    
}
