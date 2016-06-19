package de.presidente;
// <- Import ->

// <- Static_Import ->

import de.janik.propertyFile.PropertyFile;
import de.janik.propertyFile.exception.NoSuchEntryException;
import de.presidente.server.Server;

import java.io.IOException;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Main {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    private Main() {
        throw new IllegalStateException("Do not instantiate.");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->
    public static void main(final String[] args) {

        PropertyFile properties;
        try {
            properties = PropertyFile.CreateNewFile("./res/options.properties");

            final String propertyServerPort = "server_port";

            if (!properties.contains(propertyServerPort)) {
                properties.addIntProperty(propertyServerPort);

                properties.getProperty(propertyServerPort).asInt().set(5585);
            }

            final int port = properties.getProperty(propertyServerPort).asInt().get();

            final Server server = new Server(port);
            server.start();
        } catch (final IOException | NoSuchEntryException e) {
            e.printStackTrace();
        }
    }
}
