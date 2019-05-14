package smoke;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Visualizer extends JFrame {

    private Domain domain;
    public static final CellColor DOMAIN_BACKGROUND = new CellColor(1.0f, 1.0f, 1.0f);

    private JPanel smoke = new JPanel() {

        private float zoom = 24;

        @Override
        public void paint(Graphics g) {
            g.clearRect(0, 0, smoke.getWidth() - 1, smoke.getHeight() - 1);
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, smoke.getWidth() - 1, smoke.getHeight() - 1);
            g.setColor(Color.RED);
            g.drawRect((int) (0 - (domain.getX() / 2) * zoom) + (getWidth() / 2) - 2,
                    (int) (0 - (domain.getY() / 2) * zoom) + (getHeight() / 2) - 2,
                    (int) (domain.getX() * zoom) + 3,
                    (int) (domain.getY() * zoom) + 3);
            if (domain == null) return;
            for (int y = 0; y < domain.getY(); y++) {
                for (int x = 0; x < domain.getX(); x++) {
                    CellColor integrator = DOMAIN_BACKGROUND;
                    for (int z = domain.getZ() - 1; z >= 0; z--) {
                        if (domain.getLatest()[x][y][z] != null) {
                            CellColor nColor = domain.getLatest()[x][y][z].getColor();
                            CellColor fColor = integrator;
                            integrator = (nColor.multiply(Math.min(1, domain.getLatest()[x][y][z].getDensity()))).add(fColor.divide(domain.getLatest()[x][y][z].getDensity() + 1));
                        }
                    }
                    int drawX = (int) (((x - (domain.getX() / 2)) * zoom) + (getWidth() / 2));
                    int drawY = (int) (((y - (domain.getY() / 2)) * zoom) + (getHeight() / 2));
                    g.setColor(integrator.toAWTColor());
                    g.fillRect(drawX, drawY, (int) zoom, (int) zoom);
                }
            }
        }
    };

    public Visualizer(Domain domain) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        this.domain = domain;
        setTitle("Smoke Simulation");
        setSize(480, 480);
        add(smoke, BorderLayout.CENTER);
        show();
    }
}
