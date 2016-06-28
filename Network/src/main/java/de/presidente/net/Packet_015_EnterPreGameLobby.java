package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_015_EnterPreGameLobby extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String gameName;

    // <- Static ->

    // <- Constructor ->
    public Packet_015_EnterPreGameLobby(final String gameName) {
        this.gameName = gameName;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public String getGameName() {
        return gameName;
    }

    // <- Static ->
}
