package de.janik.crashHandler.view;

// <- Import ->

import de.janik.crashHandler.JavaProcess;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window.Type;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Font.BOLD;
import static java.awt.Font.MONOSPACED;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.SOUTHEAST;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2014]
 */
public final class CrashView {
    // <- Public ->
    // <- Protected ->

    private final JFrame frame;

    private final List<CloseListener> listener;

    // <- Static ->

    // <- Constructor ->
    public CrashView(final JavaProcess process) {
        listener = new ArrayList<>(1);

        frame = new JFrame();
        frame.setType(Type.UTILITY);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);

        final JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(e -> exit());

        final JLabel labelExitCode = new JLabel("The application has exited with Exit-Code (" + process.getExitCode() + ").");
        labelExitCode.setFont(new Font(MONOSPACED, BOLD, 16));
        labelExitCode.setForeground(Color.BLACK);

        frame.add(labelExitCode,
                new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0, CENTER, NONE, new Insets(5, 5, 5, 5), 0, 0));
        frame.add(buttonOK,
                new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, SOUTHEAST, NONE, new Insets(5, 5, 5, 5), 0, 0));

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                exit();
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // <- Abstract ->

    // <- Object ->
    private void exit() {
        listener.forEach(CloseListener::onCLose);

        frame.dispose();
    }

    public void addCloseListener(final CloseListener listener) {
        this.listener.add(listener);
    }

    // <- Getter & Setter ->
    // <- Static ->
}
