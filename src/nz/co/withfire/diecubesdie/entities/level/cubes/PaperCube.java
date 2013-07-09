/*********************\
| A paper cube.       |
|                     |
| @author David Saxon |
\*********************/

package nz.co.withfire.diecubesdie.entities.level.cubes;

import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.fps_manager.FpsManager;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class PaperCube extends Cube {

    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new paper cube
    @param cube the shape for the cube
    @param pos the position of the cube
    @param side the side the cube will be following
    @param entityMap a reference to the entity map*/
    public PaperCube(Shape cube, Vector3d pos,
        Side side, Entity entityMap[][][]) {
        
        super(cube, null, pos, side, entityMap);
    }
}
