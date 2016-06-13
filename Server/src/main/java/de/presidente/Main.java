package de.presidente;
// <- Import ->

// <- Static_Import ->

import de.presidente.server.Server;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Main {
    // <- Public ->
    // <- Protected ->
    // <- Private->
    // <- Static ->

    // <- Constructor ->
    private Main(){
        throw new IllegalStateException("Do not instantiate.");
    }

    // <- Abstract ->
    // <- Object ->
    // <- Getter & Setter ->

    // <- Static ->
    public static void main(final String[] args){
        final Server server = new Server(5585);
        server.start();
    }
}
