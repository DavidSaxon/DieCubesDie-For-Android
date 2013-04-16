/*******************************************************************\
| A Resource that stores a renderable model consisting of triangles |
|                                                                   |
| @author David Saxon                                               |
\*******************************************************************/

package nz.co.withfire.diecubesdie.resources.types;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import nz.co.withfire.diecubesdie.renderer.renderable.Renderable;
import nz.co.withfire.diecubesdie.renderer.renderable.model.Model;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.ResourceManager.ResourceGroup;
import nz.co.withfire.diecubesdie.utilities.ResourceUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class ModelResource {

    //VARIABLES
    //the actual model of the shader
    private Model model;
    //the list of files that the model data is contained within
    private int resourceIds[];
    //the list of groups the model is within
    private ResourceGroup[] groups;
    //the label of the texture to use for the model
    private String tex;
    //the label of the vertex shader to use for the model
    private String vertexShader;
    ///the label of the fragment shader to use for the model
    private String fragmentShader;
    //is true once the model has been loaded
    private boolean loaded = false;
    
    //CONSTRUCTOR
    /**Creates a new resource model
    @param resourceIds the resources to use in the model
    @param groups the resource groups the model is in
    @param tex the label of the texture to use for the model
    @param vertexShader the label of the vertex shader to use for the model
    @param fragmentShader the label of the fragmentshader to use for the model*/
    public ModelResource(int resourceIds[], ResourceGroup groups[],
        String tex, String vertexShader, String fragmentShader) {
        
        //initialise the variables
        model = null;
        this.resourceIds = resourceIds;
        this.groups = groups;
        this.tex = tex;
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }
    
    //PUBLIC METHODS
    /**Loads the model into memory
    @param context the android context
    @param resources the resource manager, so we can get the shaders*/
    public void load(final Context context, ResourceManager resources) {
        
        
        //an array list of renderables the model contains
        ArrayList<Renderable> renderables =
            new ArrayList<Renderable>();
        
        //iterate over the files and load the shapes from them
        for (int id : resourceIds) {
            
            renderables.add(ResourceUtil.loadOBJ(context, id,
                resources.getTexture(tex),
                resources.getShader(vertexShader),
                resources.getShader(fragmentShader)));
        }
        
        //create the model
        Log.v(ValuesUtil.TAG, "create model");
        model = new Model(renderables);
        Log.v(ValuesUtil.TAG, "done create model");
        
        //the model has successfully loaded
        loaded = true;
    }
    
    /**@return the loaded model*/
    public Model getModel() {
        
        //check that the model has been loaded
        if (!loaded) {
            
            //report error
            throw new RuntimeException(
                    "Attempted using an un-loaded model");
        }
        
        return model;
    }
    
    /**@return whether the model has been loaded*/
    public boolean isLoaded() {
        
        return loaded;
    }
}
