package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_008_CheckUsernameAvailability extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String userName;

    // <- Static ->

    // <- Constructor ->
    public Packet_008_CheckUsernameAvailability(final String userName) {
        this.userName = userName;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public String getUserName() {
        return userName;
    }

    // <- Static ->
}
