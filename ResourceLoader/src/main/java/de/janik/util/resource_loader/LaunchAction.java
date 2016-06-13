package de.janik.util.resource_loader;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
@FunctionalInterface
public interface LaunchAction {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    // <- Static ->
    // <- Constructor ->

    // <- Abstract ->
    void perform(final LaunchEvent parent);

    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
