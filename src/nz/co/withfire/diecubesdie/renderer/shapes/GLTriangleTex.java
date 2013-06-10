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

public class GLTriangleTex implements Shape {

    //VARIABLES
    //the number of position co-ordinates per vertex
    private final int coordsPerVertex = 3;
    //the number of colour values per Vertex
    private final int coordsPerTex = 2;
    //the stride of a vertex
    private final int vertexStride = 
        coordsPerVertex * ValuesUtil.FLOAT_SIZE;
    //the stride of a colour
    private final int texStride =
            coordsPerTex * ValuesUtil.FLOAT_SIZE;
    
    //the number of vertex points the triangle has
    private int vertexCount;
    
    //the vertex buffer
    private final FloatBuffer vertexBuffer;
    //the texture buffer
    private final FloatBuffer texBuffer;
    //the OpenGL program
    private int program;
    
    //the co-ordinates of the triangle
    private float coords[];
    //the texture co-ordinates of the triangle
    private float texCoords[];
    
    //the texture of the triangle
    private int tex;
    
    //the vertex shader of the triangle
    private int vertexShader;
    //the fragment shader of the triangle
    private int fragmentShader;
    
    //CONSTRUCTOR
    /**Create a new gl triangle
    @param coords the co-ordinate data of the triangle
    @param texCoords the texture coords for the triangle
    @param tex the texture for the triangle
    @param vertexShader the vertex shader to use for the triangle
    @param fragmentShader the fragment shader to use for the triangle*/
    public GLTriangleTex(float coords[], float texCoords[],
        int tex, int vertexShader, int fragmentShader) {
        
        //initialise the variables
        this.coords = coords;
        this.texCoords = texCoords;
        this.tex = tex;
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
        
        //Initialise the byte buffer for the texture coords
        ByteBuffer tb = ByteBuffer.allocateDirect(
            texCoords.length * ValuesUtil.FLOAT_SIZE);
        tb.order(ByteOrder.nativeOrder());
        
        //initialise the texture buffer and insert the co-ordinates
        texBuffer = tb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);
        
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
        
        //get a handle to the texture
        int textureUniformHandle =
            GLES20.glGetUniformLocation(program, "u_Texture");
        
        //set the active texture unit to texture unit 0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        
        //bind this texture to this unit
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex);
        
        //tell the uniform shader to use this texture
        GLES20.glUniform1i(tex, 0);

        //get a handle the vertex positions and enable them
        int positionHandle = GLES20.glGetAttribLocation(program, "a_Position");
        GLES20.glEnableVertexAttribArray(positionHandle);

        //prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, coordsPerVertex,
                                     GLES20.GL_FLOAT, false,
                                     vertexStride, vertexBuffer);

        //get a handle to the texture coord data
        int texCoordHandle = GLES20.glGetAttribLocation(program, "a_texCoord");
        
        //pass in the texture information
        texBuffer.position(0);
        GLES20.glVertexAttribPointer(texCoordHandle, coordsPerTex,
            GLES20.GL_FLOAT, false, texStride, texBuffer);
        
        GLES20.glEnableVertexAttribArray(texCoordHandle);
        

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
}
