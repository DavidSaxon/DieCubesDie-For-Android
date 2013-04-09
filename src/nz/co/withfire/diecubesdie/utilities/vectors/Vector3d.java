/************************\
| Represent a 3d vector. |
|                        |
| @author David Saxon    |
\************************/

package nz.co.withfire.diecubesdie.utilities.vectors;

public class Vector3d {
   
    //VARIABLES
    //static zero vector
    static public final Vector3d zero3d = new Vector3d();
    
    //the values of the variables
    private float x;
    private float y;
    private float z;
    
    //CONSTRUCTORS
    /**Creates a new zero 3d vector*/
    public Vector3d() {
        
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }
    
    /**Creates a new vector from the given values
    @param x the x value of the vector
    @param y the y value of the vector
    @param z the z value of the vector*/
    public Vector3d(float x, float y, float z) {
        
        this.x  = x;
        this.y = y;
        this.z = z;
    }
    
    /**Creates a new vector by copying the values from the other vector
    @param other the other vector to copy from*/
    public Vector3d(final Vector3d other) {
        
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }
    
    //PUBLIC METHODS
    /**Set the values of the vector to be the new given values
    @param x the new x value
    @param y the new y value
    @param z the new z value*/
    public void set(float x, float y, float z) {
        
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**Copy the values from the other vector to this vector
    @param other the other vector to copy from*/
    public void copy(final Vector3d other) {
        
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }
    
    /**Adds the values of the other vector to this vector
    @param other the other vector to add to this vector*/
    public void add(final Vector3d other) {
        
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
    }
    
    /**Subtracts the values of the other vector from this vector
    @param other the other vector to subtract by*/
    public void sub(final Vector3d other) {
        
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
    }
    
    /**Multiplies the values of this vector by the values of the other vector
    @param other the other vector to multiply by*/
    public void mul(final Vector3d other) {
        
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
    }
    
    /**Divides the values of this vector by the values of the other vector
    @param other the other vector to divide by*/
    public void div(final Vector3d other) {
        
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
    }
    
    /**@return the magnitude of this vector*/
    final public float magnitude() {
        
        return distance(zero3d);
    }
    
    
    /**Calculates the dot product of this vector and the other vector
    @param other the other vector to calculate with
    @return the dot product*/
    final public float dot(final Vector3d other) {
        
        return (this.x * other.x) + (this.y * other.y);
    }
    
    /**Calculates this distance between this vector and the other vector
    @param other the other vector to find the distance between
    @return the distance between the two vectors*/
    final public float distance(final Vector3d other) {
        
        return (float) Math.sqrt(Math.pow(this.x - this.y - this.z, 2.0f) +
            Math.pow(other.x - other.y - other.z, 2.0f));
    }
    
    /**Calculate the angle between this vector and the other vector
    @param other the other vector to calculate the angle between
    @return the angle between the vectors*/
    final public float angleBetween(final Vector3d other) {
        
        //TODO: write for 3d vector
        return 0.0f;
        //return (float) (-1.0*(Math.atan2(y-other.y, x-other.x)));
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
}
