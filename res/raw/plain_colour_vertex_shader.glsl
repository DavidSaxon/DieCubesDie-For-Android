//the model view projection matrix
uniform mat4 uMVPMatrix;
//vertex information that will be passed in
attribute vec4 vPosition;
//colour information that will be passed in
attribute vec4 a_Color;
//this will be passed to the fragment shader
varying vec4 v_Color;

//the main loop
void main() {

    //pass the colour through to the fragment shader
    v_Color = a_Color;
    //set the position
    gl_Position = uMVPMatrix * vPosition;
}