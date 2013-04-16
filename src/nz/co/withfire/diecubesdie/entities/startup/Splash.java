package nz.co.withfire.diecubesdie.entities.startup;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.entities.Drawable;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.renderer.renderable.model.Model;

public class Splash extends Drawable implements Entity {

    //VARIABLES
    //the model of the splash screen
    private Model splash;
    //the model to use for the fade in
    private Model fadeIn;
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the translation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new splash screen
    @param splash the model to use for the splash screen
    @param fadeIn the model to use for the fade in*/
    public Splash(Model splash, Model fadeIn) {
        
        //initialise variables
        this.splash = splash;
        this.fadeIn = fadeIn;
    }
    
    //PUBLIC METHODS
    @Override
    public void update() {
        
        //do nothing
    }
    
    @Override
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //calculate the mvp matrix
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        
        //draw the models
        splash.draw(mvpMatrix);
    }
}
