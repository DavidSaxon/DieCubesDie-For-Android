/****************************************\
| Is used to render text to the display. |
|                                        |
| @author David Saxon                   |
\****************************************/

package nz.co.withfire.diecubesdie.renderer.text;

import java.util.ArrayList;
import java.util.Scanner;

import android.opengl.Matrix;
import android.util.Log;

import nz.co.withfire.diecubesdie.renderer.shapes.GLTriangleTex;
import nz.co.withfire.diecubesdie.utilities.ValuesUtil;
import nz.co.withfire.diecubesdie.utilities.vectors.Vector2d;

public class Text {
    
    //ENUMERATORS
    //the style the text is aligned
    public enum Align {
        RIGHT,
        LEFT,
        CENTRE
    }
    
    //VARIABLES
    //always keep a static reference to the texture for the text
    public static int texture;
    //static reference to the shaders
    public static int vertexShader;
    public static int fragmentShader;
    
    //the coordinates for the quad for drawing a letter to
    private final float[] quadCoords = {
        
        -1.0f,  1.0f, 0.0f,
         1.0f,  1.0f, 0.0f,
         1.0f, -1.0f, 0.0f,
        -1.0f, -1.0f, 0.0f,
        -1.0f,  1.0f, 0.0f,
         1.0f, -1.0f, 0.0f
    };
    
    //all the the characters that can be printed
    private final char[][] characters = {
            
        {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G',
        'H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V'},
        {'W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m',
        'n','o','p','q','r','s','t','u','v','w','x','y','z','.',','},
        {'\"','\'','(',')','_','!','?','@','%','+','-', ' '}
    };
    
    //the size of the characters in the texture
    private final float charSize = 64.0f / 2056.0f;
    
    //the position of the text
    private Vector2d pos = new Vector2d();
    //the size of the text
    private float size;
    //the alignment of the text
    private Align alignment = Align.RIGHT;
    //the string of the text
    private String text;
    
    //list of the shapes of the letters
    private ArrayList<GLTriangleTex> letterShapes =
        new ArrayList<GLTriangleTex>();
    //list of position for each letter
    private ArrayList<Vector2d> letterPositions =
        new ArrayList<Vector2d>();
    
    //Matrix
    //the model view projection matrix
    private float[] mvpMatrix = new float[16];
    //the transformation matrix
    private float[] tMatrix = new float[16];
    
    //CONSTRUCTOR
    /**Creates a new rendered text
    @param pos the position of the text
    @param size the size of the text
    @param alignment the alignment of the text
    @param text the string of the text*/
    public Text (Vector2d pos, float size, Align alignment, String text) {
        
        //initialise variables
        this.pos.copy(pos);
        this.size = size;
        this.alignment = alignment;
        this.text = text;
        
        //build the text based on the string
        buildText();
    }
    
    //PUBLIC METHODS  
    /**Renders the text
    @param viewMatrix the view matrix
    @param projectionMatrix the projection matrix*/
    public void draw(float viewMatrix[], float projectionMatrix[]) {
        
        //iterate over all the letters and draw them
        for (int i = 0; i < letterShapes.size(); ++i) {
            
            Matrix.setIdentityM(tMatrix, 0);
            Matrix.translateM(tMatrix, 0, pos.getX(), pos.getY(), 0);
            Matrix.scaleM(tMatrix, 0, size, size, size);
            Matrix.translateM(tMatrix, 0, letterPositions.get(i).getX(),
                    letterPositions.get(i).getY(), 0);
            
            //multiply the matrix
            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, tMatrix, 0);
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
            
            //draw the cube
            letterShapes.get(i).draw(mvpMatrix);
        }
    }
    
    /**@return the position of the text*/
    public Vector2d getPos() {
        
        return pos;
    }
    
    /**@return the size of the text*/
    public float getSize() {
        
        return size;
    }
    
    /**@return the alignment of the text*/
    public Align getAlign() {
        
        return alignment;
    }
    
    /**@return the text string*/
    public String toString() {
        
        return text;
    }
    
    /**@param pos the new position for the text*/
    public void setPos(Vector2d pos) {
        
        this.pos.copy(pos);
        buildText();
    }
    
    /**@param size the new size of the text*/
    public void setSize(float size) {
        
        this.size = size;
        buildText();
    }
    
    /**@param alignment the new alignment for the text*/
    public void setAlignment(Align alignment) {
        
        this.alignment = alignment;
        buildText();
    }
    
    /**@param text the new text string*/
    public void setText(String text) {
        
        this.text = text;
        buildText();
    }
    
    /**@param shader the new fragment shader to use*/
    public void setFragmentShader(int shader) {
        
        fragmentShader = shader;
        
        //iterate over the letters and change their shaders
        for (GLTriangleTex s : letterShapes) {
            
            s.setFragmentShader(fragmentShader);
        }
    }
    
    //PRIVATE METHODS
    /**Builds the shapes and the positions for the text*/
    private void buildText() {
        
        //clear any current data
        letterShapes.clear();
        letterPositions.clear();
        
        //read the string with a scanner
        Scanner scan = new Scanner(text);
        
        //the current letter position
        Vector2d letterPos = new Vector2d();
        
        //read each line of the text
        while(scan.hasNextLine()) {
            
            //get the line
            String line = scan.nextLine();
            
            //calculate the start point if centered
            if (alignment == Align.CENTRE) {
                
                letterPos.setX((line.length() / 2.0f) - 0.5f);
            }
            else if (alignment == Align.RIGHT) {
                
                letterPos.setX(line.length());
            }
            
            //add the letter shapes for this line
            for (int i = 0; i < line.length(); ++i) {
                
                //get the character
                char c = line.charAt(i);
                
                //find where the character is in texture
                int charPosX = 0;
                int charPosY = 0;
                boolean loopExit = false;
                for (; charPosX < characters.length; ++charPosX) {
                    
                    charPosY = 0;
                    
                    for (; charPosY < characters[charPosX].length; ++charPosY) {
                        
                        //found the character
                        if (c == characters[charPosX][charPosY]) {
                            
                            loopExit = true;
                            break;
                        }
                    }
                    
                    if (loopExit) {
                        
                        break;
                    }
                }
                
                //calculate the texture position for the character
                float texXPos = charPosX * charSize;
                float texYPos = charPosY * charSize;
                
                //create the texture co-ordinates                
                float[] texCoords = {
                        
                    texXPos + charSize, texYPos,
                    texXPos           , texYPos,
                    texXPos           , texYPos + charSize,
                    texXPos + charSize, texYPos + charSize,
                    texXPos + charSize, texYPos,
                    texXPos           , texYPos + charSize
                };
                
                //create the shape and add it to the list
                letterShapes.add(new GLTriangleTex(quadCoords, texCoords,
                    texture, vertexShader, fragmentShader));
                
                //add the position for this letter
                letterPositions.add(new Vector2d(letterPos));
                
                //increment the the position
                letterPos.setX(letterPos.getX() - 1.0f);
            }
            
            //increment to the next line
            letterPos.setX(0.0f);
            letterPos.setY(letterPos.getY() - 2.0f);
        }
    }
}
