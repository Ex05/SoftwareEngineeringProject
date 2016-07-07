package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_021_PreGameLobbyClientUpdate extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String[] clientNames;

    // <- Static ->

    // <- Constructor ->
    public Packet_021_PreGameLobbyClientUpdate(final String[] clientNames) {
        this.clientNames = clientNames;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public String[] getClientNames() {
        return clientNames;
    }

    // <- Static ->
}
