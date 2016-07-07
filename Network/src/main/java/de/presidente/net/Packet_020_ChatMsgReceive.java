package de.presidente.net;
// <- Import ->

// <- Static_Import ->

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Packet_020_ChatMsgReceive extends Packet {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final String sender;

    private final String msg;

    // <- Static ->

    // <- Constructor ->
    public Packet_020_ChatMsgReceive(final String sender, final String msg) {
        this.sender = sender;
        this.msg = msg;
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public String getSender() {
        return sender;
    }

    public String getMsg() {
        return msg;
    }

    // <- Static ->

}