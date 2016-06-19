package de.janik.crashHandler;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultCaret;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.awt.Color.BLACK;
import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Font.BOLD;
import static java.awt.Font.MONOSPACED;
import static java.lang.String.*;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.text.DefaultCaret.ALWAYS_UPDATE;
import static javax.swing.text.SimpleAttributeSet.EMPTY;
import static javax.swing.text.StyleConstants.Foreground;

// <- Import ->
// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2014]
 */
public final class ProcessMonitor {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Thread threadStdOut;
    private final Thread threadErrOut;

    private final JFrame frame;

    private final JTextPane console;
    // <- Static ->

    // <- Constructor ->
    public ProcessMonitor(final JavaProcess process) {
        frame = new JFrame();
        frame.setTitle("CrashHandler");
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);

        console = new JTextPane();
        console.setBackground(BLACK);
        console.setForeground(LIGHT_GRAY);
        console.setFont(new Font(MONOSPACED, BOLD, 18));

        final JScrollPane scrollPane = new JScrollPane(console);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);

        final DefaultCaret caret = (DefaultCaret) console.getCaret();
        caret.setUpdatePolicy(ALWAYS_UPDATE);

        frame.add(scrollPane);

        threadStdOut = new Thread(() -> {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(process.getNativeProcess().getInputStream()))) {
                String line;

                while (process.isRunning())
                    while ((line = br.readLine()) != null)
                        append(format("Process -> %s\n", line), LIGHT_GRAY);
            } catch (IOException e) {
                e.printStackTrace();
            }

            append(format("Process -> Exit-Status (%d)", process.getExitCode()), new Color(0, 255, 255));

            final ProcessRunnable runnable = process.getRunnable();

            if (runnable != null)
                runnable.onExit(process);

            try {
                Thread.sleep(500);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

            if (process.getExitCode() == 0)
                frame.dispose();

        }, "ProcessMonitor_System.out");
        threadStdOut.setDaemon(true);

        threadErrOut = new Thread(() -> {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(process.getNativeProcess().getErrorStream()))) {
                String line;

                while (process.isRunning())
                    while ((line = br.readLine()) != null)
                        append(format("Process -> %s\n", line), new Color(229, 84, 81));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "ProcessMonitor_System.err");
        threadErrOut.setDaemon(true);
    }

    // <- Abstract ->

    // <- Object ->
    private void append(final String msg, final Color color) {
        final StyleContext sc = StyleContext.getDefaultStyleContext();
        final AttributeSet attributeSet = sc.addAttribute(EMPTY, Foreground, color);

        final int len = console.getDocument().getLength();

        console.setCaretPosition(len);
        console.setCharacterAttributes(attributeSet, false);
        console.replaceSelection(msg);
    }

    public synchronized void start() {
        threadStdOut.start();
        threadErrOut.start();

        frame.setVisible(true);
    }

    // <- Getter & Setter ->
    // <- Static ->
}
