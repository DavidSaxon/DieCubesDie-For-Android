//use medium precision
precision mediump float;

//the input texture
uniform sampler2D u_Texture;

//the texture
varying vec2 v_texCoord;
        
void main() {

    //get the texture colour
    vec4 texCol = texture2D(u_Texture, v_texCoord);
    
    //check if the colour is black (or close to it)
    if (texCol.r <= 0.15 && texCol.g <= 0.15 && texCol.b <= 0.15) {
    
        //set to relative white
        texCol.r = 1.0 - texCol.r;
        texCol.g = 1.0 - texCol.g;
        texCol.b = 1.0 - texCol.b;
    }
    else {
    
        //dim the colour
        texCol.r = texCol.r / 3.0;
        texCol.g = texCol.g / 3.0;
        texCol.b = texCol.b / 3.0;
    }
    
    gl_FragColor = texCol;
}