package de.presidente.net;
// <- Import ->

// <- Static_Import ->

import java.io.Serializable;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public abstract class Packet implements Serializable {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final long serialVersionUID = 1L;

    // <- Static ->
    // <- Constructor ->
    // <- Abstract ->

    // <- Object ->
    @Override
    public String toString() {
        return String.format("%s", getClass().getSimpleName());
    }

    // <- Getter & Setter ->
    // <- Static ->
}
