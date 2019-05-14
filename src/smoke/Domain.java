package smoke;

import java.awt.*;
import java.util.HashMap;

public class Domain {

    private Vector3D gravity;
    private int x, y, z;
    private Cell[][][] old, latest;
    private HashMap<Integer, Color> typeColors;

    public Vector3D getGravity() {
        return gravity;
    }

    public void setGravity(Vector3D gravity) {
        this.gravity = gravity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Cell[][][] getOld() {
        return old;
    }

    public Cell[][][] getLatest() {
        return latest;
    }

    public HashMap<Integer, Color> getTypeColors() {
        return typeColors;
    }

    public void setTypeColors(HashMap<Integer, Color> typeColors) {
        this.typeColors = typeColors;
    }

    public void update() {
        for (int forY = 0; forY < y; forY++) {
            for (int forX = 0; forX < y; forX++) {
                for (int forZ = 0; forZ < z; forZ++) {
                    old[forX][forY][forZ].reset();
                }
            }
        }
        for (int forY = 0; forY < y; forY++) {
            for (int forX = 0; forX < y; forX++) {
                for (int forZ = 0; forZ < z; forZ++) {
                    // Move'n'Spray adjacent cells
                    move(0.5f, forX, forY, forZ, forX + (int) latest[forX][forY][forZ].getVelocity().getX(),
                            forY + (int) latest[forX][forY][forZ].getVelocity().getY(),
                            forZ + (int) latest[forX][forY][forZ].getVelocity().getZ());
                    for (int adjacentY = Math.max(0, forY - 1); adjacentY < Math.min(getY(), forY + 1); adjacentY++) {
                        for (int adjacentX = Math.max(0, forX - 1); adjacentX < Math.min(getX(), forX + 1); adjacentX++) {
                            for (int adjacentZ = Math.max(0, forZ - 1); adjacentZ < Math.min(getZ(), forZ + 1); adjacentZ++) {
                                if (adjacentX == forX && adjacentY == forY && adjacentZ == forZ) continue;
                                Vector3D toPoint = new Vector3D(adjacentX, adjacentY, adjacentZ).subtract(new Vector3D(forX, forY, forZ));
                                float centralDensity = old[forX][forY][forZ].getDensity();
                                float otherDensity = old[adjacentX][adjacentY][adjacentZ].getDensity();
                                System.out.println("Spray");
                                old[adjacentX][adjacentY][adjacentZ]
                                        .setVelocity(old[adjacentX][adjacentY][adjacentZ]
                                                .getVelocity().add(toPoint.multiply(centralDensity - otherDensity)));
                            }
                        }
                    }
                }
            }
        }
        Cell[][][] temporary = old;
        old = latest;
        latest = temporary;
    }

    private void move(float move, int firstX, int firstY, int firstZ, int secondX, int secondY, int secondZ) {
        if (firstX == secondX && firstY == secondY && firstZ == secondZ) {
            old[firstX][firstY][firstZ].setTemperature(latest[firstX][firstY][firstZ].getTemperature());
            for (int i = 0; i < latest[firstX][firstY][firstZ].getDensities().length; i++) {
                old[firstX][firstY][firstZ].getDensities()[i] = latest[firstX][firstY][firstZ].getDensities()[i];
            }
            Vector3D vector = latest[firstX][firstY][firstZ].getVelocity();
            old[firstX][firstY][firstZ].getVelocity().change(vector.getX(), vector.getY(), vector.getZ());
            return;
        }
        // Moving densities, velocity
        boolean first = (firstX >= 0 && firstY >= 0 && firstZ >= 0) && (firstX < x && firstY < x && firstZ < z) ? true : false;
        boolean second = (secondX >= 0 && secondY >= 0 && secondZ >= 0) && (secondX < x && secondY < x && secondZ < z) ? true : false;
        for (int i = 0; i < Cell.typeColors.length; i++) {
            if (first)
                old[firstX][firstY][firstZ].getDensities()[i] = latest[firstX][firstY][firstZ].getDensities()[i] * (1 - move);
            if (second)
            old[secondX][secondY][secondZ].getDensities()[i] = latest[firstX][firstY][firstZ].getDensities()[i] * move;
        }
        // Temperature
        float ratio = 0f;
        if (first && second)
            ratio = latest[firstX][firstY][firstZ].getDensity() / latest[secondX][secondY][secondZ].getDensity();
        if (first)
            old[firstX][firstY][firstZ].setTemperature(latest[firstX][firstY][firstZ].getTemperature());
        if (second)
            old[secondX][secondY][secondZ].setTemperature(latest[firstX][firstY][firstZ].getTemperature() * ratio + latest[secondX][secondY][secondZ].getTemperature() * (1 - ratio));
        // Velocity
        if (first)
            old[firstX][firstY][firstZ].setVelocity(latest[firstX][firstY][firstZ].getVelocity().multiply(1 - move));
        if (second)
            old[secondX][secondY][secondZ].setVelocity(latest[secondX][secondY][secondZ].getVelocity().multiply(move));
        System.out.println("Move");
    }

    public Domain(int x, int y, int z, CellColor[] typeColors) {
        if (x < 0 || y < 0 || z < 0) throw new IllegalArgumentException("Voxel dimensions must not be negative");
        this.x = x;
        this.y = y;
        this.z = z;
        Cell.typeColors = typeColors;
        old = new Cell[x][y][z];
        for (int forY = 0; forY < y; forY++) {
            for (int forX = 0; forX < y; forX++) {
                for (int forZ = 0; forZ < z; forZ++) {
                    old[forX][forY][forZ] = new Cell(0, new Vector3D(0, 0, 0), new float[typeColors.length]);
                }
            }
        }
        latest = new Cell[x][y][z];
    }
}