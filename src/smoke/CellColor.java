package smoke;

import java.awt.*;

public class CellColor {

    private float red, green, blue;

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public CellColor add(CellColor other) {
        return new CellColor(other.red + this.red, other.green + this.green, other.blue + this.blue);
    }

    public CellColor subtract(CellColor other) {
        return new CellColor(this.red - other.red, this.green - other.green, this.blue - other.blue);
    }

    public CellColor multiply(CellColor other) {
        return new CellColor(this.red * other.red, this.green * other.green, this.blue * other.blue);
    }

    public CellColor multiply(float scalar) {
        return new CellColor(this.red * scalar, this.green * scalar, this.blue * scalar);
    }

    public CellColor divide(CellColor other) {
        return new CellColor(this.red / other.red, this.green / other.green, this.blue / other.blue);
    }

    public CellColor divide(float scalar) {
        return new CellColor(this.red / scalar, this.green / scalar, this.blue / scalar);
    }

    public CellColor interpolate(CellColor other, float mix) {
        float otherMix = 1 - mix;
        return new CellColor(this.red * mix + other.red * otherMix,
                this.green * mix + other.green * otherMix,
                this.blue * mix + other.blue * otherMix);
    }

    public CellColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color toAWTColor() {
        return new Color((int) Math.max(0, Math.min(red * 255, 255)),(int) Math.max(0, Math.min(green * 255, 255)), (int) Math.max(0, Math.min(blue * 255, 255)));
    }
}
