//use medium precision
precision mediump float;

//the input texture
uniform sampler2D u_Texture;

//the texture
varying vec2 v_texCoord;
        
void main() {

    //get the texture colour
    vec4 texCol = texture2D(u_Texture, v_texCoord);
    
    //dim the colour
    texCol.r = texCol.r / 3.0;
    texCol.g = texCol.g / 3.0;
    texCol.b = texCol.b / 3.0;
    
    //set the colour
    gl_FragColor = texCol;
}