package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_007_Register extends Packet_001_Login {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final byte[] salt;

    // <- Static ->

    // <- Constructor ->
    public Packet_007_Register(final LoginCredentials loginCredentials, final byte[] salt) {
        super(loginCredentials);

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
