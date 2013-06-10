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
    texCol.r = texCol.r / 1.5;
    texCol.g = texCol.g / 1.5;
    texCol.b = texCol.b / 1.5;
    
    //set the colour
    gl_FragColor = texCol;
}