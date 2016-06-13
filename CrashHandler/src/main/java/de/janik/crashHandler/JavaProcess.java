package de.janik.crashHandler;

// <- Import ->
// <- Static_Import ->

import java.util.Arrays;

import static java.lang.String.*;

/**
 * @author Jan.Marcel.Janik [©2014]
 */
public final class JavaProcess {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Process process;

    private final String[] commands;

    private final ProcessMonitor monitor;

    private final ProcessRunnable runnable;

    // <- Static ->

    // <- Constructor ->
    public JavaProcess(final Process process, final ProcessRunnable runnable, final String... commands) {
        this.process = process;
        this.runnable = runnable;
        this.commands = commands;

        this.monitor = new ProcessMonitor(this);
        monitor.start();
    }

    // <- Abstract ->

    // <- Object ->
    public void destroy() {
        process.destroy();
    }

    @Override
    public String toString() {
        return format("%s[commands:%s, running:%s]", getClass().getSimpleName(), Arrays.toString(commands), isRunning() + "");
    }

    // <- Getter & Setter ->
    public boolean isRunning() {
        try {
            getExitCode();
        } catch (final IllegalThreadStateException ex) {
            return true;
        }

        return false;
    }

    public ProcessRunnable getRunnable() {
        return runnable;
    }

    public Process getNativeProcess() {
        return process;
    }

    public String[] getCommands() {
        return commands;
    }

    public ProcessMonitor getMonitor() {
        return monitor;
    }

    public int getExitCode() throws IllegalThreadStateException {
        return process.exitValue();
    }

    // <- Static ->
}
