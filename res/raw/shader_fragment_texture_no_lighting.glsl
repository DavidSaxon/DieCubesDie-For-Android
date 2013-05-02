//use medium precision
precision mediump float;

//the input texture
uniform sampler2D u_Texture;

//the texture
varying vec2 v_texCoord;
        
void main() {

    //set the colour to the texture
    gl_FragColor = texture2D(u_Texture, v_texCoord);
}