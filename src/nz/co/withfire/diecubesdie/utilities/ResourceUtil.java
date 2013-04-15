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
import nz.co.withfire.diecubesdie.renderer.renderable.shape.Shape;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector3d;

import android.content.Context;
import android.opengl.GLES20;
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
    
    /**Loads a an .obj file as a OpenGl Triangle
    NOTE: the .obj file must consist of triangles for efficiency
    @param context the android context
    @param resourceId the id of the resource to load
    @param vertexShader the vertex shader to use for the shape
    @param fragmentShader the fragment shader to use for the shape
    @return the loaded shape*/
    static public Shape loadOBJ(final Context context, int resourceId,
        int vertexShader, int fragmentShader) {
        
        //open the file as a string
        String file = resourceToString(context, resourceId);
        
        //load the file into a scanner
        Scanner scanner = new Scanner(file);
        
        //an array list that contains the vertex coords
        ArrayList<Vector3d> vertices = new ArrayList<Vector3d>();
        //an array of the vertexs in order that make up the model
        ArrayList<Vector3d> faces = new ArrayList<Vector3d>();
        
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
            //read face vertices
            else if (next.equals("f")) {
                
                //get the vertices of the face
                faces.add(vertices.get(Integer.parseInt(scanner.next()) - 1));
                faces.add(vertices.get(Integer.parseInt(scanner.next()) - 1));
                faces.add(vertices.get(Integer.parseInt(scanner.next()) - 1));
            }
            //TODO: texture coords
            else {
                
                //throw away the line
                scanner.nextLine();
            }
        }
        
        Log.v(ValuesUtil.TAG, "done");
        
        //get the triangle coords
        float coords[] = new float[faces.size() * 3];
        for (int i = 0; i < faces.size(); ++i) {
            
            coords[(i * 3)]     = faces.get(i).getX();
            coords[(i * 3) + 1] = faces.get(i).getY();
            coords[(i * 3) + 2] = faces.get(i).getZ();
        }
        
        //temp colour
        float colour[] = {0.2f, 0.0f, 1.0f, 1.0f};
        
        return new GLTriangleCol(coords, colour,
                vertexShader, fragmentShader);
    }
    
}
