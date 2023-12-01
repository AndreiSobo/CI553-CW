package clients;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class CatPawButton extends JButton {
    private Shape shape;

    // Constants for paw positions and sizes
    private static final int PAW_X = 10;
    private static final int PAW_Y = 10;
    private static final int PAW_SIZE = 30;
    private static final int SMALL_PAD_SIZE = 15;

    // Constants for colors
    private static final Color PAW_COLOR = Color.gray;
    private static final Color SMALL_PAD_COLOR = Color.pink; // You can adjust the color

    // Constructor
    public CatPawButton(String label) {
        super(label);
        setContentAreaFilled(false);
    }

    // Override paintComponent method to customize the button appearance
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(PAW_COLOR);
        }
        Graphics2D g2d = (Graphics2D) g;
        // Draw the shape of the cat paw here
        Path2D.Double path = createPawPath();
        g2d.fill(path);
        super.paintComponent(g);
    }

    // Override paintBorder method to customize the button border (none in this case)
    @Override
    protected void paintBorder(Graphics g) {
        // No border for simplicity
    }

    // Override contains method to define the clickable area of the button
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = createPawPath();
        }
        return shape.contains(x, y);
    }

    // Create the path for the cat paw print
    private Path2D.Double createPawPath() {
        Path2D.Double path = new Path2D.Double();

        // Main paw body
        path.append(new Ellipse2D.Double(PAW_X, PAW_Y, PAW_SIZE, PAW_SIZE), false);

        // Top left small pad
        path.append(new Ellipse2D.Double(PAW_X - SMALL_PAD_SIZE / 2, PAW_Y - SMALL_PAD_SIZE / 2, SMALL_PAD_SIZE, SMALL_PAD_SIZE), false);

        // Top right small pad
        path.append(new Ellipse2D.Double(PAW_X + PAW_SIZE - SMALL_PAD_SIZE / 2, (PAW_Y - SMALL_PAD_SIZE / 2), SMALL_PAD_SIZE, SMALL_PAD_SIZE), false);

        // Bottom small pad
        path.append(new Ellipse2D.Double(PAW_X + (PAW_SIZE - SMALL_PAD_SIZE) / 2, (PAW_Y + PAW_SIZE - SMALL_PAD_SIZE / 2)-35, SMALL_PAD_SIZE, SMALL_PAD_SIZE), false);

        return path;
    }
}
