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

    private final String[] games;
    private final String[] clients;

    public Packet_004_LobbyEnter(final String[] games, final String[] clients) {
        this.games = games;
        this.clients = clients;
    }

    // <- Static ->
    // <- Constructor ->
    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public String[] getGames() {
        return games;
    }

    public String[] getClients() {
        return clients;
    }

    // <- Static ->

}