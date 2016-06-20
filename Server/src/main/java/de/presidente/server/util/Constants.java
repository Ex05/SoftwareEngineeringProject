package de.presidente.server.util;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Constants {
    // <- Public ->
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PASSWD;
    public static final String DB_NAME;

    public static final int DB_PORT;

    // <- Protected ->
    // <- Private->

    // <- Static ->
    static {
        DB_URL = "janik-bau.nrw";
        DB_USER = "luebbe";
        DB_PASSWD = "r7QMJJjbiVlJfz7BTtkU";
        DB_NAME = "swt_ss2016";

        DB_PORT = 3306;
    }

    // <- Constructor ->
    private Constants() {
        throw new IllegalStateException("Do not instantiate.");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->
    // <- Static ->
}
