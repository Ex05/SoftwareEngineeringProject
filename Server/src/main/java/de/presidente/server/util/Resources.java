package de.presidente.server.util;
// <- Import ->

// <- Static_Import ->

import java.sql.Connection;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Resources {
    // <- Public ->
    public static Connection DB_CONNECTION;

    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    private Resources() {
        throw new IllegalStateException("Do not instantiate.");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
