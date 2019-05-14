package smoke;

public class Vector3D {

    private float x, y, z;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void change(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D add(Vector3D other) {
        return new Vector3D(x + other.x, y + other.y, z + other.z);
    }

    public Vector3D subtract(Vector3D other) {
        return new Vector3D(x - other.x, y - other.y, z - other.z);
    }

    public Vector3D multiply(float scalar) {
        return new Vector3D(x * scalar, y * scalar, z * scalar);
    }

    public Vector3D divide(float scalar) {
        return new Vector3D(x / scalar, y / scalar, z / scalar);
    }

    public float magnitude() {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2) + Math.pow(z, 2));
    }

    public Vector3D normalize() {
        float length = magnitude();
        return new Vector3D(x / length, y / length, z / length);
    }

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
