package smoke;

import java.awt.*;

public class Cell {

    protected static CellColor[] typeColors;

    private float temperature;
    private Vector3D velocity;
    private float[] densities;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Vector3D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    public float[] getDensities() {
        return densities;
    }

    public void setDensities(float[] densities) {
        this.densities = densities;
    }

    public CellColor getColor() {
        float sum = 0f;
        float[] ratio = new float[densities.length];
        for (float d: densities)
            sum += d;
        int i = 0;
        for (float d: densities) {
            ratio[i] = d / sum;
            if (!(ratio[i] >= 0)) {
                ratio[i] = 0;
            }
            i++;
        }
        CellColor color = new CellColor(0f, 0f, 0f);
        for (int j = 0; j < ratio.length; j++)
            color = color.add(typeColors[j].multiply(ratio[j]));
        return color;
    }

    public float getDensity() {
        float sum = 0f;
        for (float d: densities)
            sum += d;
        return sum;
    }

    public void reset() {
        temperature = 0;
        velocity.change(0f, 0f, 0f);
        for (int clear = 0; clear < densities.length; clear++)
            densities[clear] = 0;
    }

    public Cell(float temperature, Vector3D velocity, float[] densities) {
        this.temperature = temperature;
        this.velocity = velocity;
        this.densities = densities;
    }
}
