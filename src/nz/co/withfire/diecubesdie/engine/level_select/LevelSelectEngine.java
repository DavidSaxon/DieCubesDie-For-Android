/*********************************\
| Engine to control level select. |
|                                 |
| @author David Saxon            |
\*********************************/

package nz.co.withfire.diecubesdie.engine.level_select;

import nz.co.withfire.diecubesdie.engine.Engine;
import nz.co.withfire.diecubesdie.entities.gui.Overlay;
import nz.co.withfire.diecubesdie.entities.level_select.Background;
import nz.co.withfire.diecubesdie.entity_list.EntityList;
import nz.co.withfire.diecubesdie.renderer.GLRenderer;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;
import android.content.Context;
import android.opengl.Matrix;
import android.util.Log;

public class LevelSelectEngine implements Engine {
    
    //VARIABLES
    //the android context
    private final Context context;
    
    //the resource manager
    private ResourceManager resources;
    
    //the list of all entities
    private EntityList entities = new EntityList();
    
    //the next state to move to once completed
    private Engine nextState = null;
    //is true once the menu is complete
    private boolean complete = false;
    
    //the level selector
    private LevelSelector selector;
    
    //CONSTRUCTOR
    /**Creates a new level select engine
    @param context the android context
    @param resources the resources manager*/
    public LevelSelectEngine(Context context, ResourceManager resources) {
        
        //set variables
        this.context = context;
        this.resources = resources;
    }
    
    //PUBLIC METHODS
    @Override
    public void init() {
        
        //set the clear colour of the renderer
        GLRenderer.setClearColour(new Vector4d(0, 0, 0, 1));
        
        //load the level select resources
        resources.loadGroup(ResourceGroup.LEVEL_SELECT);
        
        Log.v(ValuesUtil.TAG, "Load complete");
        
        addInitObjects();
    }
    
    @Override
    public boolean execute() {
        
        entities.update();
        
        return complete;
    }

    @Override
    public void applyCamera(float[] viewMatrix) {

        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix.translateM(viewMatrix, 0, 0.0f, 0.1f, 2.0f);
        Matrix.rotateM(viewMatrix, 0, 15, -1.0f, 0, 0.0f);
        Matrix.rotateM(viewMatrix, 0, 0, 0, -1.0f, 0.0f);
    }

    @Override
    public void touchEvent(int event, Vector2d touchPos) {

        //do nothing
    }

    @Override
    public EntityList getEntities() {

        return entities;
    }

    @Override
    public Engine nextState() {

        return nextState;
    }
    
    //PRIVATE METHODS
    /**Adds the initial objects*/
    private void addInitObjects() {
        
        //add the background
        entities.add(new Background(
            resources.getShape("level_select_background")));
        
        //create a new level selector
        selector = new LevelSelector(resources, entities);
        
        //the fade in overlay
        entities.add(new Overlay(resources.getShape("fade_overlay"),
            new Vector2d(), null, true));
    }
}
