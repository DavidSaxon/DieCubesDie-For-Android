//the model view projection matrix
uniform mat4 u_MVPMatrix;
//vertex information that will be passed in
attribute vec4 v_Position;

//the main loop
void main() {

    //set the position
    gl_Position = u_MVPMatrix * v_Position;
}