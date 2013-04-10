/**********************************************************************\
| Represents a coloured triangle that can be rendered by the renderer. |
|                                                                      |
| @author David Saxon                                                  |
\**********************************************************************/

package nz.co.withfire.diecubesdie.renderer.renderable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

import nz.co.withfire.diecubesdie.utilities.ValuesUtil;

public class GLTriangleCol {

    //VARIABLES
    //the number of position co-ordinates per vertex
    private final int coordsPerVertex = 3;
    //the number of colour values per Vertex
    private final int colValsPerVertex = 4;
    //the stride of a vertex
    private final int vertexStride = 
        coordsPerVertex * ValuesUtil.sizeOfFloat;
    //the stride of a colour
    private final int colourStride =
        colValsPerVertex * ValuesUtil.sizeOfFloat;
    
    //the vertex buffer
    private final FloatBuffer vertexBuffer;
    //the colour buffer
    private final FloatBuffer colourBuffer;
    //the opengl program
    //private final int program;
    
    //the co-ordinates of the triangle
    private float coords[];
    //the colour values of the triangle
    private float colour[];
    
    //TODO: move shaders from here
    //shaders
    private final String vertexShaderCode =

        //the model view projection matrix
        "uniform mat4 uMVPMatrix;" +
        //vertex information that will be passed in
        "attribute vec4 vPosition;" +
        //colour information that will be passed in
        "attribute vec4 a_Color;\n" +
        //this will be passed to the fragment shader
        "varying vec4 v_Color;\n" +
                
        "void main() {" +
        
            //pass the colour through to the fragment shader
            "v_Color = a_Color\n;" +
            //set the position
        "   gl_Position = uMVPMatrix * vPosition;" +
        "}";

    private final String fragmentShaderCode =
        
        //use medium precision
        "precision mediump float;" +
        //the colour
        "varying vec4 v_Color;" +
                
        "void main() {" +
        
            //set the colour
        "   gl_FragColor = v_Color;" +
        "}";
    
    //CONSTRUCTOR
    /**Create a new gl triangle
    @param coords the co-ordinate data of the triangle
    @param colour the colour data of the the triangle*/
    public GLTriangleCol(float coords[], float colour[]) {
        
        //initialise the variables
        this.coords = coords;
        this.colour = colour;
        
        //initialise the byte buffer for the vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(
            coords.length * ValuesUtil.sizeOfFloat);
        bb.order(ByteOrder.nativeOrder());
        
        //initialise the vertex buffer and insert the co-ordinates
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
        
        //initialise the byte buffer for the colour buffer
        ByteBuffer cb = ByteBuffer.allocateDirect(
            colour.length * ValuesUtil.sizeOfFloat);
        cb.order(ByteOrder.nativeOrder());
        
        //initialise the vertex buffer and insert the co-ordinates
        colourBuffer = cb.asFloatBuffer();
        colourBuffer.put(colour);
        colourBuffer.position(0);
        
        //TODO: create a resource for shaders
//        //prepare the shaders and the openGL program
//        int vertexShader = ShaderLoader.loadShader(GLES20.GL_VERTEX_SHADER,
//                                                   vertexShaderCode);
//        int fragmentShader = ShaderLoader.loadShader(GLES20.GL_FRAGMENT_SHADER,
//                                                     fragmentShaderCode)
    }
}
