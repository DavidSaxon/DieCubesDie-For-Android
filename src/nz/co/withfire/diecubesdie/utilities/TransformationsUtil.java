/***************************************\
| Utilities relating to transformations |
|                                       |
| @author David Saxon                  |
\***************************************/

package nz.co.withfire.diecubesdie.utilities;

import android.opengl.Matrix;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class TransformationsUtil {

    //VARIABLES
    //the screen dimensiosn
    private static Vector2d screenDim = new Vector2d();
    //the openGL view port dimensions
    private static Vector2d openGLDim = new Vector2d();
    //the scale amounts
    private static Vector2d scale = new Vector2d();
    
    //PUBLIC FUNCTIONS
    /**Initialise the need values for the transformation utilities
    @param screenDim the screen dimensions
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix*/
    public static void init(Vector2d screenDim,
        float[] viewMatrix, float[] projectionMatrix) {
        
        TransformationsUtil.screenDim = screenDim;
        
        //set the opengl dimensions
        openGLDim.copy(screenPosToOpenGLPos(screenDim,
            viewMatrix, projectionMatrix));
        
        //get the scaling amounts
        scale.setX(-(openGLDim.getX() / ValuesUtil.NATURAL_SCREEN_SIZE.getX()));
        scale.setY(-(openGLDim.getY() / ValuesUtil.NATURAL_SCREEN_SIZE.getY()));
    }
    
    /**Converts screen position to the equivlent OpenGL position
    @param screenPos the screen position to convert
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix
    @return the new OpenGL position*/
    public static Vector2d screenPosToOpenGLPos(Vector2d screenPos,
        float[] viewMatrix, float[] projectionMatrix) {
        
        //initialise the new position vector
        Vector2d openGLPos = new Vector2d();
        
        //matrix
        float[] invertedMatrix = new float[16];
        float[] transformationMatrix = new float[16];
        
        //points
        float[] normalisedPoint = new float[4];
        float[] outPoint = new float[4];
        
        //invert the positions
        int screenPosX = (int) (screenDim.getX() - screenPos.getX());
        int screenPosY = (int) (screenDim.getY() - screenPos.getY());
        
        //transform the screen point
        normalisedPoint[0] =
            (float) ((screenPosX) * 2.0f / screenDim.getX() - 1.0);
        normalisedPoint[1] =
                (float) ((screenPosY) * 2.0f / screenDim.getY() - 1.0);
        normalisedPoint[2] =  -1.0f;
        normalisedPoint[3] =   1.0f;
        
        //get the matrix
        Matrix.multiplyMM(transformationMatrix, 0, projectionMatrix, 0,
                viewMatrix, 0);
        Matrix.invertM(invertedMatrix, 0, transformationMatrix, 0);
        
        //apply the inverse to the point
        Matrix.multiplyMV(outPoint, 0, invertedMatrix, 0, normalisedPoint, 0);
        
        //avoid dividing by zero
        if (outPoint[3] == 0.0f) {
            
            return openGLPos;
        }
        
        //Divide by the 3rd component to find the real world position
        openGLPos.setX(- (outPoint[0] / outPoint[3]));
        openGLPos.setY(outPoint[1] / outPoint[3]);
        
        return openGLPos;
    }
    
    /**Get the scaled position of the give position
    @param pos the position to scale
    @return the new scaled position*/
    public static Vector2d scaleToScreen(Vector2d pos) {
        
        return new Vector2d(pos.getX() * scale.getX(),
            pos.getY() * scale.getY());
    }
    
    /**@return the OpenGL perspective dimensions*/
    public static Vector2d getOpenGLDim() {
        
        return openGLDim;
    }
    
    /**@return the screen dimensions*/
    public static Vector2d getScreenDim() {
        
        return screenDim;
    }
}