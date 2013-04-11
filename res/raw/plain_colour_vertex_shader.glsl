//the model view projection matrix
uniform mat4 u_MVPMatrix;
//vertex information that will be passed in
attribute vec4 v_Position;
//colour information that will be passed in
attribute vec4 a_Colour;
//this will be passed to the fragment shader
varying vec4 v_Colour;

//the main loop
void main() {

    //pass the colour through to the fragment shader
    v_Colour = a_Colour;
    //set the position
    gl_Position = u_MVPMatrix * v_Position;
}