package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class Packet_011_CheckGameName extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String gameName;

    // <- Static ->

    // <- Constructor ->
    public Packet_011_CheckGameName(final String gameName) {
        this.gameName = gameName;
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public String toString() {
        return String.format("%s[GameName:%s]", getClass().getSimpleName(), gameName);
    }


    // <- Getter & Setter ->
    public String getGameName() {
        return gameName;
    }

    // <- Static ->
}
