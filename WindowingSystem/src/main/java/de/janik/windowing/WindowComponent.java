package de.janik.windowing;

// <- Import ->
import java.awt.Component;

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class WindowComponent<T extends Component> {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final T c;

    // <- Static ->

    // <- Constructor ->
    protected WindowComponent(final T c) {
        this.c = c;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public final T getAWT_Component() {
        return c;
    }

    // <- Static ->
}
