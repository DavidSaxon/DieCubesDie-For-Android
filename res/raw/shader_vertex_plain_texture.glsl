//the model view projection matrix
uniform mat4 u_MVPMatrix;
//vertex information that will be passed in
attribute vec4 a_Position;
//texture information that will be passed in
attribute vec2 a_texCoord;

//texture data that will be passed to the fragment shader
varying vec2 v_texCoord;
        
void main() {

    //pass the tex coords through to the fragment shader
    v_texCoord = a_texCoord;
    
    //set the position
    gl_Position = u_MVPMatrix * a_Position;
}