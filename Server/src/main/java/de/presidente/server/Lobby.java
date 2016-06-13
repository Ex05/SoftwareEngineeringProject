package de.presidente.server;
// <- Import ->

// <- Static_Import ->

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Lobby {

    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final List<Game> games;

    private final List<Client> clients;
    // <- Static ->

    // <- Constructor ->
    public Lobby() {
        games = new ArrayList<>(128);

        clients = new ArrayList<>(128);
    }

    // <- Abstract ->

    // <- Object ->
    public void enter(final Client client) {
        clients.add(client);
    }

    public void leave(final Client client) {
        clients.remove(client);
    }

    // <- Getter & Setter ->
    public String[] getGameNames() {
        return  games.stream().map(Game::getName).toArray(String[]::new);
    }

    // <- Static ->
}
