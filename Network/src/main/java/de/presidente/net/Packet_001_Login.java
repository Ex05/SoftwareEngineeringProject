package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public class Packet_001_Login extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final long serialVersionUID = 1L;

    private final LoginCredentials loginCredentials;

    // <- Static ->

    // <- Constructor ->
    public Packet_001_Login(final LoginCredentials loginCredentials) {
        this.loginCredentials = loginCredentials;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public LoginCredentials getLoginCredentials() {
        return loginCredentials;
    }

    // <- Static ->
}
