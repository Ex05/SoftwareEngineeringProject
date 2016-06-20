package de.presidente.server.util;
// <- Import ->

// <- Static_Import ->

import java.sql.Connection;

import static de.presidente.server.util.Constants.DB_NAME;
import static de.presidente.server.util.Constants.DB_PASSWD;
import static de.presidente.server.util.Constants.DB_PORT;
import static de.presidente.server.util.Constants.DB_URL;
import static de.presidente.server.util.Constants.DB_USER;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Resources {
    // <- Public ->
    public static Connection DB_CONNECTION;

    // <- Protected ->
    // <- Private->

    // <- Static ->
    static {
        DB_CONNECTION = DB_Manager.OpenConnection(null, DB_URL, DB_PORT, DB_NAME, DB_USER, DB_PASSWD);
    }

    // <- Constructor ->
    private Resources() {
        throw new IllegalStateException("Do not instantiate.");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
