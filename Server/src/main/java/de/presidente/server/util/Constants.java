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

    public static final String TABLE_USER_COLUMN_ID;
    public static final String TABLE_USER_COLUMN_SALT;
    public static final String TABLE_USER_COLUMN_PASSWORD;
    public static final String PREPARED_STATEMENT_SELECT_SALT;
    public static final String PREPARED_STATEMENT_SELECT_PASSWORD_AND_ID;
    public static final String PREPARED_STATEMENT_SELECT_ID;
    public static final String PREPARED_STATEMENT_INSERT_NEW_USER;

    public static final int DB_PORT;

    // <- Protected ->
    // <- Private->

    // <- Static ->
    static {
        DB_URL = "janik-bau.nrw";
        DB_USER = "luebbe";
        DB_PASSWD = "r7QMJJjbiVlJfz7BTtkU";

        DB_NAME = "swt_ss2016";
        PREPARED_STATEMENT_SELECT_SALT = "SELECT 'salt' FROM 'user' WHERE 'name' = ?";
        PREPARED_STATEMENT_SELECT_PASSWORD_AND_ID = "SELECT 'id', 'password' FROM 'user' WHERE 'name' = ?";
        PREPARED_STATEMENT_SELECT_ID = "SELECT 'id' FROM 'user' WHERE 'name' = ?";
        PREPARED_STATEMENT_INSERT_NEW_USER = "INSERT INTO 'user' (name, password, salt) VALUES (?, ?, ?)";

        TABLE_USER_COLUMN_ID = "id";
        TABLE_USER_COLUMN_SALT = "salt";
        TABLE_USER_COLUMN_PASSWORD = "password";

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
