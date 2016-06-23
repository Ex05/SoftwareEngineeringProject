package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_005_ReceiveSalt extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private static final long serialVersionUID = 1L;

    private final String userName;

    // <- Static ->

    // <- Constructor ->
    public Packet_005_ReceiveSalt(final String userName) {
        this.userName = userName;
    }

    // <- Abstract ->

    // <- Object ->
    @Override
    public String toString() {
        return String.format("%s[Username:%s]", getClass().getSimpleName(), userName);
    }

    // <- Getter & Setter ->
    public String getUserName() {
        return userName;
    }

    // <- Static ->
}
