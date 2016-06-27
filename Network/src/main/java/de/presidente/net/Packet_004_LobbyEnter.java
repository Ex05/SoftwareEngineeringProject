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
    private final String[] owners;
    private final String[] clients;

    private final Byte[] player;

    public Packet_004_LobbyEnter(final String[] games, final String[] owners, final Byte[] player, final String[] clients) {
        this.games = games;
        this.owners = owners;
        this.player = player;
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

    public String[] getOwners() {
        return owners;
    }

    public Byte[] getPlayerCounts() {
        return player;
    }

    // <- Static ->

}