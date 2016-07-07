package de.janik.softengine;
// <- Import ->

// <- Static_Import ->

import de.janik.windowing.Window;
import de.janik.windowing.WindowComponent;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import static java.awt.Color.BLACK;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.System.*;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class Canvas extends WindowComponent<JPanel> {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final java.awt.Canvas canvas;

    private final int width, height;

    private final BufferedImage img;

    private final Window window;

    private int[] pixel;

    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;

    // <- Static ->

    // <- Constructor ->
    public Canvas(final Window window, final int scale) {
        super(new JPanel());

        this.window = window;

        this.width = window.getWidth() / scale;
        this.height = window.getHeight() / scale;

        canvas = new java.awt.Canvas();

        getAWT_Component().setLayout(new BorderLayout());
        getAWT_Component().setFocusable(false);
        getAWT_Component().add(canvas);

        canvas.setFocusable(true);
        canvas.setBackground(new Color(115, 195, 90));
        canvas.setIgnoreRepaint(true);
        canvas.setFocusTraversalKeysEnabled(false);
        canvas.requestFocusInWindow();
        canvas.requestFocus();

        img = new BufferedImage(width, height, TYPE_INT_ARGB);

        pixel = ((DataBufferInt) (img.getRaster().getDataBuffer())).getData();
    }

    // <- Abstract ->

    // <- Object ->
    public void addKeyListener(final InputManager keyListener) {
        canvas.addKeyListener(keyListener);
    }

    public void addMouseListener(final InputManager mouseListener) {
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
    }

    public void render(final Screen screen) {
        final int[] pixel = screen.getPixel();

        arraycopy(pixel, 0, this.pixel, 0, this.pixel.length);

        final BufferStrategy bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(3);

            return;
        }

        final Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.drawImage(img, 0, 0, getAWT_Component().getWidth(), getAWT_Component().getHeight(), null);

        g.dispose();
        bs.show();
    }

    // <- Getter & Setter ->
    public void setDragableByMouse(final boolean dragAble, final Window window) {
        final Point pressed = new Point();
        final Point location = new Point();

        if (dragAble) {
            if (mouseMotionListener == null)
                mouseMotionListener = new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(final MouseEvent e) {
                        final int x = (int) (e.getLocationOnScreen().getX() - pressed.getX());
                        final int y = (int) (e.getLocationOnScreen().getY() - pressed.getY());

                        window.setLocation((int) location.getX() + x, (int) location.getY() + y);
                    }
                };

            if (mouseListener == null)
                mouseListener = new MouseAdapter() {
                    @Override
                    public void mousePressed(final MouseEvent e) {
                        pressed.setLocation(e.getLocationOnScreen());
                        location.setLocation(window.getLocation());
                    }
                };

            canvas.addMouseMotionListener(mouseMotionListener);
            canvas.addMouseListener(mouseListener);

        } else {
            if (mouseListener != null)
                canvas.removeMouseListener(mouseListener);

            if (mouseMotionListener != null)
                canvas.removeMouseMotionListener(mouseMotionListener);
        }
    }

    public BufferedImage getOffScreenImage() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // <- Static ->
}
