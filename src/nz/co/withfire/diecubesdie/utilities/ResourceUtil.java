/******************************************************\
| Functions relating to accessing and using resources. |
|                                                      |
| @author David Saxon                                  |
\******************************************************/

package nz.co.withfire.diecubesdie.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import nz.co.withfire.diecubesdie.renderer.renderable.shape.GLTriangleCol;
import nz.co.withfire.diecubesdie.renderer.renderable.shape.GLTriangleTex;
import nz.co.withfire.diecubesdie.renderer.renderable.shape.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class ResourceUtil {
    
    /**Reads a resource into a string
    @param context the android context
    @param resourceID the id of the resource
    @return a string containing the data of the resource*/
    static public String resourceToString(
        final Context context, int resourceID) {
        
        //open the resource into an input stream
        final InputStream inputStream = context.getResources().
            openRawResource(resourceID);
        //open the input stream in an input stream reader
        final InputStreamReader inputStreamReader =
            new InputStreamReader(inputStream);
        //open the input stream reader in a buffered reader
        final BufferedReader bufferedReader =
            new BufferedReader(inputStreamReader);
        
        //reading variables
        String nextLine;
        final StringBuilder text = new StringBuilder();
        
        try {
            
            while ((nextLine = bufferedReader.readLine()) != null) {
                
                text.append(nextLine);
                text.append('\n');
            }
        } catch (IOException e) {
            
            return null;
        }
        
        return text.toString();
    }
    
    /**Compiles an OpenGL shader from raw source code
    @param shaderType the type of shader being compiled
    @param shaderSource the raw source code of the shader
    @return a handle to the shader*/
    static public int compileShader(final int shaderType,
        final String shaderSource) {
        
        //initialise the handle to the shader
        int shaderHandle = GLES20.glCreateShader(shaderType);
        
        //make sure the shader handle has been created
        if (shaderHandle == 0) {
            
            //throw an error
            throw new RuntimeException("Error creating shader handle");
        }
        
        //pass in the shader source
        GLES20.glShaderSource(shaderHandle, shaderSource);
        
        //compile the shader
        GLES20.glCompileShader(shaderHandle);
        
        //get the compilation status
        final int[] compileStatus= new int[1];
        GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS,
            compileStatus, 0);
        
        //check that the shader compiled correctly
        if (compileStatus[0] == 0) {
            
            //report error
            Log.v(ValuesUtil.TAG, "Shader compilation failure");
            throw new RuntimeException("Shader compilation failure");
        }
        
        return shaderHandle;
    }
    
    /**Loads an .png file as an OpenGL Texture
    @param context the android context
    @param resourceId the id of the .png file
    @return the OpenGL texture*/
    static public int loadPNG(final Context context, int resourceId) {
        
        //create a pointer for the texture
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        
        //if generating the texture fails report error
        if (textureHandle[0] == 0) {
            
            throw new RuntimeException("Error loading texture");
        }
        
        //set up the bitmap details
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        
        //read in the the png file
        final Bitmap bitmap = BitmapFactory.decodeResource(
            context.getResources(), resourceId, options);
        
        //bind the texture in openGL
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
        
        //set filtering
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        
        //load in the texture
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        
        //recycle the bitmap since the data has been loaded into OpenGL
        bitmap.recycle();
        
        return textureHandle[0];
    }
    
    /**Loads a an .obj file as a OpenGl Triangle
    NOTE: the .obj file must consist of triangles for efficiency
    @param context the android context
    @param resourceId the id of the resource to load
    @param tex the texture of the model
    @param vertexShader the vertex shader to use for the shape
    @param fragmentShader the fragment shader to use for the shape
    @return the loaded shape*/
    static public Shape loadOBJ(final Context context, int resourceId,
        int tex, int vertexShader, int fragmentShader) {
        
        //open the file as a string
        String file = resourceToString(context, resourceId);
        
        //load the file into a scanner
        Scanner scanner = new Scanner(file);
        
        //an array list that contains the vertex coords
        ArrayList<Vector3d> vertices = new ArrayList<Vector3d>();
        //an list the contains the texture coords
        ArrayList<Vector2d> texCoords = new ArrayList<Vector2d>();
        //an array of the vertexs in order that make up the model
        ArrayList<Vector3d> faceVertices = new ArrayList<Vector3d>();
        //the array of texture coords in the model
        ArrayList<Vector2d> faceTextures = new ArrayList<Vector2d>();
        
        //read through the entire file
        while(scanner.hasNext()) {
        
            //get the next value
            String next  = scanner.next();
            
            //read the vertex coords
            if (next.equals("v")) {
                
                //add the vertex
                vertices.add(new Vector3d(Float.parseFloat(scanner.next()),
                    Float.parseFloat(scanner.next()),
                    Float.parseFloat(scanner.next())));
            }
            //read the texture coords
            else if (next.equals("vt")) {
                
                //add the coord
                texCoords.add(new Vector2d(Float.parseFloat(scanner.next()),
                        Float.parseFloat(scanner.next())));
            }
            //read face vertices
            else if (next.equals("f")) {
                
                for (int i = 0; i < 3; ++i) {
                    
                    //get the index from the file and split
                    String indices[] = scanner.next().split("/");
                    
                    //parse as ints
                    int vertexIndex = Integer.parseInt(indices[0]) - 1;
                    int textureIndex = Integer.parseInt(indices[1]) - 1;
                    
                    faceVertices.add(vertices.get(vertexIndex));
                    faceTextures.add(texCoords.get(textureIndex));
                }
            }
            else {
                
                //throw away the line
                scanner.nextLine();
            }
        }
        
        Log.v(ValuesUtil.TAG, "swap");
        
        //get the triangle coords
        float coords[] = new float[faceVertices.size() * 3];
        float tCoords[] = new float[faceTextures.size() * 2];
        for (int i = 0; i < faceVertices.size(); ++i) {
            
            //get vertex coords
            coords[(i * 3)]     = faceVertices.get(i).getX();
            coords[(i * 3) + 1] = faceVertices.get(i).getY();
            coords[(i * 3) + 2] = faceVertices.get(i).getZ();
            
            //get texture coords
            tCoords[(i * 2)]     = faceTextures.get(i).getX();
            tCoords[(i * 2) + 1] = faceTextures.get(i).getY();
        }
        
        Log.v(ValuesUtil.TAG, "create triangle");
        
        return new GLTriangleTex(coords, tCoords, tex,
                vertexShader, fragmentShader);
        
        //Log.v(ValuesUtil.TAG, "done create tri");
    }
    
}
