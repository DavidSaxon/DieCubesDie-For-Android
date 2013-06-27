//use medium precision
precision mediump float;

//the input texture
uniform sampler2D u_Texture;

//the texture
varying vec2 v_texCoord;
        
void main() {

    //get the texture colour
    vec4 texCol = texture2D(u_Texture, v_texCoord);
    
    if (texCol.r > 0.4 && texCol.b < 0.4) {
            
        texCol.a = 0.0;
    }
    
    //if not fully alpha then invert
    if (texCol.a < 1.0) {
    
        texCol.r = 1.0 - texCol.r;
        texCol.g = 1.0 - texCol.g;
        texCol.b = 1.0 - texCol.b;
    }
    
    //set the colour
    gl_FragColor = texCol;
}