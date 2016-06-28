package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_004_LobbyEnter extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final long serialVersionUID = 1L;

    private final GameInfo[] games;

    private final String[] clients;

    // <- Static ->

    // <- Constructor ->
    public Packet_004_LobbyEnter(final GameInfo[] games, final String[] clients) {
        this.games = games;
        this.clients = clients;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public GameInfo[] getGames() {
        return games;
    }

    public String[] getClients() {
        return clients;
    }

    // <- Static ->

}