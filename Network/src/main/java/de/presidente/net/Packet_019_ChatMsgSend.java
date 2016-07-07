package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_019_ChatMsgSend extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String msg;

    // <- Static ->

    // <- Constructor ->
    public Packet_019_ChatMsgSend(final String msg) {
        this.msg = msg;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public String getMsg() {
        return msg;
    }

    // <- Static ->

}