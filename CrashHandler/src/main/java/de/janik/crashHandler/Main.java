package de.janik.crashHandler;

import de.janik.crashHandler.view.CrashView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.io.File.separator;

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

        Path path = null;
        try {
            path = Files.list(
                    workingDirectory.toPath()).
                    filter(e -> e.getFileName().toString().endsWith(".jar") &&
                            !e.getFileName().toString().equals(
                                    new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName()))
                    .findFirst().get();
        } catch (final IOException e) {
            e.printStackTrace();

            System.exit(-1);
        }

        final String[] commands = new String[]{
                System.getProperty("java.home") + separator + "bin" + separator + "java",
                "-jar",
                workingDirectory + separator + path.getFileName()};

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
