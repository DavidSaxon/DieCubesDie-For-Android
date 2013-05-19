/************************************\
| Interface that all entities extend |
|                                    |
| @author David Saxon                |
\************************************/

package nz.co.withfire.diecubesdie.entities;

public abstract class Entity {

    //PUBLIC METHODS
    /**Updates the entity*/
    public void update() {
        
        //do nothing
    }
    
    /**@return whether the entity should be removed*/
    public boolean shouldRemove() {
        
        return false;
    }
    
}
