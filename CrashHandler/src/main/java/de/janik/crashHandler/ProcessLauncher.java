package de.janik.crashHandler;

import java.io.File;
import java.io.IOException;

// <- Import ->
// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2014]
 */
public final class ProcessLauncher {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final File workingDirectory;

    private final String[] commands;

    private final ProcessRunnable runnable;

    // <- Static ->

    // <- Constructor ->
    public ProcessLauncher(final String workingDirectory, final ProcessRunnable runnable, final String... commands) {
        this(new File(workingDirectory), runnable, commands);
    }

    public ProcessLauncher(final File workingDirectory, final ProcessRunnable runnable, final String... commands) {
        this.workingDirectory = workingDirectory;
        this.runnable = runnable;
        this.commands = commands;
    }

    // <- Abstract ->

    // <- Object ->
    public ProcessMonitor start() {
        ProcessMonitor monitor = null;

        try {
            monitor = new JavaProcess(
                    new ProcessBuilder(getCommands()).directory(workingDirectory).redirectErrorStream(false).start(),
                    runnable, getCommands()).getMonitor();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return monitor;
    }

    // <- Getter & Setter ->
    public String[] getCommands() {
        return commands;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }
    // <- Static ->
}
