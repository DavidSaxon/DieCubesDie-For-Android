/**********************************\
| Utilities relating to debugging. |
|                                  |
| @author David Saxon             |
\**********************************/

package nz.co.withfire.diecubesdie.utilities;

import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class DebugUtil {

    //VARIABLES
    //is true when debug mode is active
    public static final boolean DEBUG = false;
    
    //the shaders for bounding boxes
    public static int boundingVertexShader;
    public static int boundingFragmentShader;
    
    public static final Vector4d boundingColour =
        new Vector4d(0.0f, 1.0f, 0.0f, 1.0f);
}
