package de.presidente.server.util;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Constants {
    // <- Public ->

    public static final String TABLE_USER_COLUMN_ID;
    public static final String TABLE_USER_COLUMN_SALT;
    public static final String TABLE_USER_COLUMN_PASSWORD;
    public static final String PREPARED_STATEMENT_SELECT_SALT;
    public static final String PREPARED_STATEMENT_SELECT_PASSWORD_AND_ID;
    public static final String PREPARED_STATEMENT_SELECT_ID;
    public static final String PREPARED_STATEMENT_INSERT_NEW_USER;

    // <- Protected ->
    // <- Private->

    // <- Static ->
    static {
        PREPARED_STATEMENT_SELECT_SALT = "SELECT salt FROM user WHERE name = ?";
        PREPARED_STATEMENT_SELECT_PASSWORD_AND_ID = "SELECT id, password FROM user WHERE name = ?";
        PREPARED_STATEMENT_SELECT_ID = "SELECT id FROM user WHERE name = ?";
        PREPARED_STATEMENT_INSERT_NEW_USER = "INSERT INTO user (name, password, salt) VALUES (?, ?, ?)";

        TABLE_USER_COLUMN_ID = "id";
        TABLE_USER_COLUMN_SALT = "salt";
        TABLE_USER_COLUMN_PASSWORD = "password";
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
