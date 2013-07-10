/******************************\
| The finish point in a level. |
|                              |
| @author David Saxon          |
\******************************/

package nz.co.withfire.diecubesdie.entities.level.terrian.entry;
import nz.co.withfire.diecubesdie.renderer.shapes.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

public class Finish extends Entry {
    
    //CONSTRUCTOR
    /**Creates a new spawn point
    @param door the shape for the door of the spawn point
    @param inside the inside of the spawn point
    @param pos the the position of the spawn point*/
    public Finish(Shape door, Shape inside, Vector3d pos) {
        
        super(door, inside, pos);
    }
}
