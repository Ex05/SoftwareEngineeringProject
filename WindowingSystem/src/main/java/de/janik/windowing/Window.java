package de.janik.windowing;

// <- Import ->

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import static de.janik.windowing.WindowType.DEFAULT;
import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.Window.Type.NORMAL;
import static java.awt.Window.Type.UTILITY;

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik, Gorden.Kappenberg [©2016]
 */
public final class Window {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final JFrame frame;

    private final List<ShutDownAction> actions;

    private WindowType type = DEFAULT;

    // <- Static ->

    // <- Constructor ->
    public Window() {
        frame = new JFrame();

        actions = new ArrayList<>(1);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                actions.forEach(ShutDownAction::onShutDown);
            }
        });
    }

    // <- Abstract ->

    // <- Object ->
    public Window addShutDownAction(final ShutDownAction action) {
        actions.add(action);

        return this;
    }

    public Window removeShutDownAction(final ShutDownAction action) {
        actions.remove(action);

        return this;
    }

    public Window dispose() {
        frame.dispose();

        return this;
    }

    public Window center() {
        frame.setLocationRelativeTo(null);

        return this;
    }

    // <- Getter & Setter ->
    public boolean isDisplayable() {
        return frame.isDisplayable();
    }

    public Window setViewComponent(final WindowComponent c) {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.add(c.getAWT_Component(), new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0));

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);

        c.getAWT_Component().setSize(frame.getSize());

        return this;
    }

    public WindowType getType() {
        return type;
    }

    public Point getLocation() {
        return frame.getLocation();
    }

    public int getX_LocationOnScreen() {
        return (int) frame.getLocationOnScreen().getX();
    }

    public int getY_LocationOnScreen() {
        return (int) frame.getLocationOnScreen().getY();
    }

    public Window setLocation(final int x, final int y) {
        frame.setLocation(x, y);

        return this;
    }

    public Window setType(final WindowType type) {
        switch (type) {
            case DEFAULT: {
                frame.setType(NORMAL);
                frame.setUndecorated(false);

                break;
            }
            case BORDERLESS: {
                frame.setType(NORMAL);
                frame.setUndecorated(true);

                break;
            }
            case BORDERLESS_NO_CONTROLS: {
                frame.setType(UTILITY);
                frame.setUndecorated(true);

                break;
            }
            case UTILITY: {
                frame.setType(UTILITY);
                frame.setUndecorated(false);

                break;
            }
        }

        this.type = type;

        return this;
    }

    public Window setAlwaysOnTop(final boolean alwaysOnTop) {
        frame.setAlwaysOnTop(alwaysOnTop);

        return this;
    }

    public Window setVisible(final boolean visible) {
        frame.setVisible(visible);

        return this;
    }

    public Window setTitle(final String title) {
        frame.setTitle(title);

        return this;
    }

    public Window setSize(final int width, final int height) {
        frame.setSize(width, height);

        return this;
    }

    public int getWidth() {
        return frame.getWidth();
    }

    public int getHeight() {
        return frame.getHeight();
    }

    public boolean isVisible() {
        return frame.isVisible();
    }

    public JFrame getSwingComponent() {
        return frame;
    }

    // <- Static ->
}
