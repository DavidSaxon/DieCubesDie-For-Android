/*********************\
| A paper cube.       |
|                     |
| @author David Saxon |
\*********************/

package nz.co.withfire.diecubesdie.entities.level.cubes;
import nz.co.withfire.diecubesdie.entities.Entity;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class PaperCube extends Cube {
    
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
