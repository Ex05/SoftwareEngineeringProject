package de.presidente.server;
// <- Import ->

// <- Static_Import ->

import java.util.ArrayList;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Game {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String name;

    private final Client owner;

    private final ArrayList<Client> clients;

    private byte playerCount;
    // <- Static ->

    // <- Constructor ->
    public Game(final String name, final Client owner) {
        this.name = name;
        this.owner = owner;

        clients = new ArrayList<>(5);

        clients.add(owner);

        playerCount = 1;
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

    public byte getPlayerCount() {
        return playerCount;
    }

    public boolean enter(final Client client) {
        if (clients.size() >= 5)
            return false;
        else {
            final boolean entered = clients.add(client);

            if (entered)
                playerCount++;

            return entered;
        }
    }

    // <- Static ->
}
