package de.janik.crashHandler;

import de.janik.crashHandler.view.CrashView;

import java.io.File;

import static java.io.File.*;

// <- Import ->
// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2014]
 */
public final class Main {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->
    // <- Constructor ->
    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->
    public static void main(final String[] args) throws InterruptedException {
        final Thread thread = Thread.currentThread();

        final File workingDirectory = new File(System.getProperty("user.dir"));

        final String[] commands = new String[]{
                System.getProperty("java.home") + separator + "bin" + separator + "java",
                "-jar",
                workingDirectory + separator + "jungleKing.jar"};

        new ProcessLauncher(workingDirectory.getAbsolutePath(), p -> {
            if (p.getExitCode() != 0)
                new CrashView(p).addCloseListener(() -> {
                    synchronized (thread) {
                        thread.notifyAll();
                    }
                });

            synchronized (thread) {
                thread.notifyAll();
            }
        }, commands).start();

        synchronized (thread) {
            thread.wait();
        }
    }
}
