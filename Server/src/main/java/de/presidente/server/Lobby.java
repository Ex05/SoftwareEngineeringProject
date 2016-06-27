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
        synchronized (clients) {
            clients.add(client);
        }
    }

    public void leave(final Client client) {
        if (client != null)
            synchronized (clients) {
                clients.remove(client);
            }
    }

    // <- Getter & Setter ->
    public String[] getGameNames() {

        synchronized (clients) {
            return games.stream().map(Game::getName).toArray(String[]::new);
        }
    }

    public String[] getGameOwner() {
        synchronized (clients) {
            return games.stream().map(g -> g.getOwner().getUserName()).toArray(String[]::new);
        }
    }

    public String[] getConnectedClients() {
        synchronized (clients) {
            return clients.stream().map(Client::getUserName).toArray(String[]::new);
        }
    }

    public boolean isGameNameAvailable(final String name) {
        synchronized (games) {
            return games.stream().filter(g -> g.getName().equals(name)).findFirst().orElse(null) == null;
        }
    }

    public boolean createGame(final Client client, final String name) {
        boolean created = false;

        if (isGameNameAvailable(name)) {

            synchronized (games) {
                games.add(new Game(name, client));
            }

            created = true;
        }

        return created;
    }

    public Byte[] getPlayerCounts() {
        return games.stream().map(Game::getPlayerCount).toArray(Byte[]::new);

    }

    // <- Static ->
}
