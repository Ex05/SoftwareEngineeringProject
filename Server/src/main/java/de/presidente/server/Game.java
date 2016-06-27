package de.presidente.server;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Game {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String name;

    private final Client owner;

    // <- Static ->

    // <- Constructor ->
    public Game(final String name, final Client owner) {
        this.name = name;
        this.owner = owner;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public String getName() {
        return name;
    }

    public Client getOwner() {
        return owner;
    }

    // <- Static ->
}
