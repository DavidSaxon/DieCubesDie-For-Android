//use medium precision
precision mediump float;

//the input texture
uniform sampler2D u_Texture;

//the texture
varying vec2 v_texCoord;
        
void main() {

    //get the texture colour
    vec4 texCol = texture2D(u_Texture, v_texCoord);
    
    //shade
    texCol.a = texCol.a + 0.5;
    
    //invert
    if (texCol.r > 0.4 && texCol.b < 0.4) {
        
        texCol.r = 0.2;
        texCol.g = 0.2;
        texCol.b = 0.2;
    }
    else if (texCol.a > 0.99999) {
    
        texCol.r = 1.0 - texCol.r;
        texCol.g = 1.0 - texCol.g;
        texCol.b = 1.0 - texCol.b;
    }
    
    //set the colour
    gl_FragColor = texCol;
}