package de.janik.util.resource_loader;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class LaunchEvent {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final LaunchAction launchAction;

    private final int weight;

    private LaunchableComponent parent;

    // <- Static ->

    // <- Constructor ->
    public LaunchEvent(final LaunchAction launchAction, final int weight) {
        this.launchAction = launchAction;
        this.weight = weight;
    }

    // <- Abstract ->

    // <- Object ->
    public void publish(final String msg) {
        parent.publish(msg);
    }

    // <- Getter & Setter ->
    public LaunchAction getLaunchAction() {
        return launchAction;
    }

    public int getWeight() {
        return weight;
    }

    public void setParent(final LaunchableComponent parent) {
        this.parent = parent;
    }

    // <- Static ->
}
