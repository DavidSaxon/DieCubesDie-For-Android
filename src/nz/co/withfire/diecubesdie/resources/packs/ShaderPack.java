/******************************************\
| Resource pack that contains all shaders. |
|                                          |
| @author David Saxon                     |
\******************************************/

package nz.co.withfire.diecubesdie.resources.packs;

import android.opengl.GLES20;
import nz.co.withfire.diecubesdie.R;
import nz.co.withfire.diecubesdie.resources.ResourceManager;
import nz.co.withfire.diecubesdie.resources.types.ShaderResource;

public class ShaderPack {

    /**Builds all shader resources
    @param resources the resource manager*/
    public static void build(ResourceManager resources) {
        
        resources.addShader("plain_colour_vertex",
            new ShaderResource(GLES20.GL_VERTEX_SHADER,
            R.raw.shader_vertex_plain_colour));
        resources.addShader("colour_no_lighting_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_colour_no_lighting));
        resources.addShader("plain_texture_vertex",
                new ShaderResource(GLES20.GL_VERTEX_SHADER,
                R.raw.shader_vertex_plain_texture));
        resources.addShader("texture_no_lighting_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_texture_no_lighting));
        resources.addShader("texture_dim_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_texture_dim));
        resources.addShader("texture_black_to_white_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_texture_black_to_white));
        resources.addShader("texture_dim_btw_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_texture_dim_btw));
        resources.addShader("texture_half_dim_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_texture_half_dim));
        resources.addShader("texture_quater_dim_fragment",
                new ShaderResource(GLES20.GL_FRAGMENT_SHADER,
                R.raw.shader_fragment_texture_quater_dim));
    }
}
