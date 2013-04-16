/*************************\
| Represents a 4d vector. |
|                         |
| @author David Saxon     |
\*************************/

package nz.co.withfire.diecubesdie.utilities.vectors;

public class Vector4d {

    //VARIABLES
    //static zero vector
    static public final Vector4d zero4d = new Vector4d();
    
    //values
    private float x;
    private float y;
    private float z;
    private float w;
    
    //CONSTRUCTORS
    /**Creates a new zero 4d vector*/
    public Vector4d() {
        
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
    }
    
    /**Creates a new 4d vector from the given values
    @param x the x value of the vector
    @param y the y value of the vector
    @param z the z value of the vector
    @param w the w value of the vector*/
    public Vector4d(float x, float y, float z, float w) {
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**Creates a new 4d vector by copying the values from the other vector
    @param other the other vector to copy from*/
    public Vector4d(final Vector4d other) {
        
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }
    
    //PUBLIC METHODS
    /**Set the values of the vector to be the new given values
    @param x the new x value
    @param y the new y value
    @param z the new z value
    @param w the new w value*/
    public void set(float x, float y, float z, float w) {
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    /**Copy the values from the other vector to this vector
    @param other the other vector to copy from*/
    public void copy(final Vector4d other) {
        
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }
    
    /**Adds the values of the other vector to this vector
    @param other the other vector to add to this vector*/
    public void add(final Vector4d other) {
        
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        this.w += other.w;
    }
    
    /**Subtracts the values of the other vector from this vector
    @param other the other vector to subtract by*/
    public void sub(final Vector4d other) {
        
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        this.w -= other.w;
    }
    
    /**Multiplies the values of this vector by the values of the other vector
    @param other the other vector to multiply by*/
    public void mul(final Vector4d other) {
        
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        this.w *= other.w;
    }
    
    /**Divides the values of this vector by the values of the other vector
    @param other the other vector to divide by*/
    public void div(final Vector4d other) {
        
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        this.w /= other.w;
    }
    
    /**@return the magnitude of this vector*/
    final public float magnitude() {
        
        return distance(zero4d);
    }
    
    
    /**Calculates the dot product of this vector and the other vector
    @param other the other vector to calculate with
    @return the dot product*/
    final public float dot(final Vector4d other) {
        
        return (this.x * other.x) + (this.y * other.y) +
            (this.z * other.z) + (this.w * other.w);
    }
    
    /**Calculates this distance between this vector and the other vector
    @param other the other vector to find the distance between
    @return the distance between the two vectors*/
    final public float distance(final Vector4d other) {
        
        return (float) Math.sqrt(
            Math.pow(this.x - other.x, 2.0f) +
            Math.pow(this.y - other.y, 2.0f) +
            Math.pow(this.z - other.z, 2.0f) +
            Math.pow(this.w - other.w, 2.0f));
    }
    
    /**Calculate the angle between this vector and the other vector
    @param other the other vector to calculate the angle between
    @return the angle between the vectors*/
    final public float angleBetween(final Vector4d other) {
        
        //TODO: write for 4d vector
        return 0.0f;
        //return (float) (-1.0*(Math.atan2(y-other.y, x-other.x)));
    }
    
    /**@return the values of the vector as an array*/
    final public float[] toArray() {
        
        float array[] = {x, y, z, w};
        
        return array;
    }
    
    //GETTERS
    /**@return the x value of the vector*/
    final public float getX() {
        
        return x;
    }
    
    /**@return the y value of the vector*/
    final public float getY() {
        
        return y;
    }
    
    /**@return the z value of the vector*/
    final public float getZ() {
        
        return z;
    }
    
    /**@return the w value of the vector*/
    final public float getW() {
        
        return w;
    }
    
    //SETTERS
    /**Sets the new x value
    @param x the new x value*/
    public void setX(float x) {
        
        this.x = x;
    }
    
    /**Sets the new y value
    @param y the new y value*/
    public void setY(float y) {
        
        this.y = y;
    }
    
    /**Sets the new z value
    @param z the new z value*/
    public void setZ(float z) {
        
        this.z = z;
    }
    
    /**Sets the new w value
    @param w the new w value*/
    public void setW(float w) {
        
        this.w = w;
    }
}
