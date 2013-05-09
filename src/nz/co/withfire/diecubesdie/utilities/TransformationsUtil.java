/***************************************\
| Utilities relating to transformations |
|                                       |
| @author David Saxon                  |
\***************************************/

package nz.co.withfire.diecubesdie.utilities;

import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class TransformationsUtil {

    //VARIABLES
    //the openGL view port dimensions
    private static Vector2d openGLDim = new Vector2d();
    
    //PUBLIC FUNCTIONS
    /**Initialise the need values for the transformation utilities
    @param openGLDim the openGL view port dimensions*/
    public static void init(Vector2d openGLDim) {
        
        TransformationsUtil.openGLDim = openGLDim;
    }
    
    /**Calculates the scaled position based on the openGL viewport size
    @param pos the position to be scaled*/
    public static void scalePos(Vector2d pos) {
        
        
    }
}