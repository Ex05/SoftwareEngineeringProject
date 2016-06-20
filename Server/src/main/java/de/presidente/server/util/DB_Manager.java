package de.presidente.server.util;
// <- Import ->

// <- Static_Import ->

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class DB_Manager {
    // <- Public ->
    // <- Protected ->
    // <- Private->

    // <- Static ->
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // <- Constructor ->
    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->
    public static Connection OpenConnection(final Connection connection, final String url, final int port, final String database, final String user, final String password) {
        try {
            if (connection == null || connection.isClosed())
                return DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + database, user, password);
        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
