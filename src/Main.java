import smoke.*;

public class Main {

    public strictfp static void main(String[] args) {
        Domain domain = new Domain(8, 8, 1, new CellColor[] { new CellColor(0.0f, 1.0f, 0.2f), new CellColor(1.0f, 0.25f, 0.0f) });
        Cell[][][] voxel = domain.getLatest();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                for (int z = 0; z < 1; z++) {
                    voxel[x][y][z] = new Cell(0f, new Vector3D(0, 0, 0), new float[] { 0, 0 });
                }
            }
        }
        voxel[1][1][0] = new Cell(30, new Vector3D(1, 1, 0), new float[] { 1.1f, 0.4f });
        voxel[2][1][0] = new Cell(30, new Vector3D(1, 1, 0), new float[] { 1.1f, 0.4f });
        voxel[2][5][0] = new Cell(30, new Vector3D(1, 1, 0), new float[] { 1.1f, 0.4f });
        domain.update();
        domain.update();
        domain.update();
        domain.update();
        Visualizer visualizer = new Visualizer(domain);
    }
}
