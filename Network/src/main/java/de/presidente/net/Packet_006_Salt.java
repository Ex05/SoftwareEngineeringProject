package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_006_Salt extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final long serialVersionUID = 1L;

    private final byte[] salt;

    // <- Static ->

    // <- Constructor ->
    public Packet_006_Salt(final byte[] salt) {
        this.salt = salt;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public byte[] getSalt() {
        return salt;
    }

    // <- Static ->
}
