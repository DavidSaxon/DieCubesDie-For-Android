/**********************************************************************\
| Represents a coloured triangle that can be rendered by the renderer. |
|                                                                      |
| @author David Saxon                                                  |
\**********************************************************************/

package nz.co.withfire.diecubesdie.renderer.shapes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.util.Log;

import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class GLTriangleCol implements Shape {

    //VARIABLES
    //the number of position co-ordinates per vertex
    private final int coordsPerVertex = 3;
    //the number of colour values per Vertex
    private final int colValsPerVertex = 4;
    //the stride of a vertex
    private final int vertexStride = 
        coordsPerVertex * ValuesUtil.FLOAT_SIZE;
    //the stride of a colour
    private final int colourStride =
        colValsPerVertex * ValuesUtil.FLOAT_SIZE;
    
    //the number of vertex points the triangle has
    private int vertexCount;
    
    //the vertex buffer
    private final FloatBuffer vertexBuffer;
    //the colour buffer
    private FloatBuffer colourBuffer;
    //the OpenGL program
    private int program;
    
    //the co-ordinates of the triangle
    private float coords[];
    //the colour values of the triangle
    private Vector4d colour;
    
    //the vertex shader of the triangle
    private int vertexShader;
    //the fragment shader of the triangle
    private int fragmentShader;
    
    //CONSTRUCTOR
    /**Create a new gl triangle
    @param coords the co-ordinate data of the triangle
    @param colour the colour data of the the triangle
    @param vertexShader the vertex shader to use for the triangle
    @param fragmentShader the fragment shader to use for the triangle*/
    public GLTriangleCol(float coords[], Vector4d colour,
        int vertexShader, int fragmentShader) {
        
        //initialise the variables
        this.coords = coords;
        this.colour = colour;
        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
        
        //calculate the number of vertexes
        vertexCount = this.coords.length / coordsPerVertex;
        
        //initialise the byte buffer for the vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(
            coords.length * ValuesUtil.FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        
        //initialise the vertex buffer and insert the co-ordinates
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);
        
        //initialise the byte buffer for the colour buffer
        ByteBuffer cb = ByteBuffer.allocateDirect(
            4 * ValuesUtil.FLOAT_SIZE);
        cb.order(ByteOrder.nativeOrder());
        
        //initialise the vertex buffer and insert the co-ordinates
        colourBuffer = cb.asFloatBuffer();
        colourBuffer.put(colour.toArray());
        colourBuffer.position(0);
        
        //create the openGL program
        program = GLES20.glCreateProgram();
        //attach the vertex shader to the program
        GLES20.glAttachShader(program, this.vertexShader);
        //attach the fragment shader to the program
        GLES20.glAttachShader(program, this.fragmentShader);
        //create openGL program executables
        GLES20.glLinkProgram(program);
    }
    
    @Override
    public void draw(float[] mvpMatrix) {
        
        //set the blending function
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        
        //Set the OpenGL program to use
        GLES20.glUseProgram(program);

        //get a handle the vertex positions and enable them
        int positionHandle = GLES20.glGetAttribLocation(program, "v_Position");
        GLES20.glEnableVertexAttribArray(positionHandle);

        //prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, coordsPerVertex,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);

        //get a handle to colour and enable it
        int colourHandle = GLES20.glGetUniformLocation(program, "v_Colour");

        //set the colour for drawing
        GLES20.glUniform4fv(colourHandle, 1, colour.toArray(), 0);

        //get handle to the transformation matrix
        int mvpMatrixHandle = GLES20.glGetUniformLocation(
            program, "u_MVPMatrix");

        //apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        //draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        //disable the vertex and colour arrays
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
    
    @Override
    public void setFragmentShader(int shader) {
        
        //detach the current shader
        GLES20.glDetachShader(program, this.fragmentShader);
        
        //set the new shader
        this.fragmentShader = shader;
        
        //attach the new shader
        GLES20.glAttachShader(program, this.fragmentShader);
        //create openGL program executables
        GLES20.glLinkProgram(program);
    }
    
    /**@return the colour of the triangle*/
    public Vector4d getColour() {
        
        return colour;
    }
    
    /**@param colour the new colour of the triangle*/
    public void setColour(Vector4d colour) {
        
        this.colour.copy(colour);
    }
    
    /**Reloads the colour of the triangle*/
    public void reloadColour() {
        
        //initialise the byte buffer for the colour buffer
        ByteBuffer cb = ByteBuffer.allocateDirect(
            4 * ValuesUtil.FLOAT_SIZE);
        cb.order(ByteOrder.nativeOrder());
        
        //initialise the vertex buffer and insert the co-ordinates
        colourBuffer = cb.asFloatBuffer();
        colourBuffer.put(colour.toArray());
        colourBuffer.position(0);
    }
}
