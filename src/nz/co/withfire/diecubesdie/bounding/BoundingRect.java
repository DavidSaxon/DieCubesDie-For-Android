/******************************************\
| A bounding box that is rectangle shaped. |
|                                          |
| @author David Saxon                     |
\******************************************/

package nz.co.withfire.diecubesdie.bounding;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.util.Log;

import nz.co.withfire.diecubesdie.utilities.DebugUtil;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector4d;

public class BoundingRect extends Bounding {

    //VARIABLES
    //the position of the bounding rectangle
    private Vector2d pos = new Vector2d();
    //the dimensions of the bounding rectangle
    private Vector2d dim = new Vector2d();
    
    //CONSTRUCTOR
    public BoundingRect(Vector2d pos, Vector2d dim) {
        
        this.pos = pos;
        this.dim = dim;
        
        //sort hand
        float dx = dim.getX() / 2.0f;
        float dy = dim.getY() / 2.0f;
        
        //if in debug mode set up drawing of the bounding box
        if (DebugUtil.DEBUG) {
            
            //initialise the variables
            float tcoords[] = {
                -dx,  dy, 0.0f,
                 dx,  dy, 0.0f,
                -dx, -dy, 0.0f,
                 dx, -dy, 0.0f,
                -dx, -dy, 0.0f,
                -dx,  dy, 0.0f,
                 dx, -dy, 0.0f,
                 dx,  dy, 0.0f,
            };
            
            this.coords = tcoords;
            
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
            colourBuffer.put(DebugUtil.boundingColour.toArray());
            colourBuffer.position(0);
            
            //create the openGL program
            program = GLES20.glCreateProgram();
            //attach the vertex shader to the program
            GLES20.glAttachShader(program, DebugUtil.boundingVertexShader);
            //attach the fragment shader to the program
            GLES20.glAttachShader(program, DebugUtil.boundingFragmentShader);
            //create openGL program executables
            GLES20.glLinkProgram(program);
        }
    }
    
    //PUBLIC METHODS    
    /**@return the dimensions of the vector*/
    public Vector2d getDim() {
        
        return dim;
    }
    
    //-----DEBUG------
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
    private FloatBuffer vertexBuffer = null;
    //the colour buffer
    private FloatBuffer colourBuffer = null;
    //the OpenGL program
    private int program = 0;
    
    //the co-ordinates of the triangle
    private float coords[];
    
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
        GLES20.glUniform4fv(colourHandle, 1,
            DebugUtil.boundingColour.toArray(), 0);

        //get handle to the transformation matrix
        int mvpMatrixHandle = GLES20.glGetUniformLocation(
            program, "u_MVPMatrix");

        //apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        //draw the triangle
        GLES20.glDrawArrays(GLES20.GL_LINES, 0, vertexCount);

        //disable the vertex and colour arrays
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
