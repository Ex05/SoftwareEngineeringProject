package de.janik.crashHandler;// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
@FunctionalInterface
public interface ProcessRunnable {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->
    // <- Constructor ->

    // <- Abstract ->
    void onExit(final JavaProcess process);

    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
