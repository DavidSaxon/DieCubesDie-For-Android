//use medium precision
precision mediump float;
//the colour
varying vec4 v_Colour;

//the main loop
void main() {

    //set the colour
    gl_FragColor = v_Colour;
}