package de.presidente.server.chat;
// <- Import ->

// <- Static_Import ->

import de.presidente.server.Client;

import java.time.ZonedDateTime;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class ChatMessage {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Client sender;

    private final String msg;

    private final ZonedDateTime timeStamp;

    // <- Static ->

    // <- Constructor ->
    public ChatMessage(final Client sender, final String msg) {
        this.sender = sender;
        this.msg = msg;

        timeStamp = ZonedDateTime.now();
    }

    // <- Abstract ->
    // <- Object ->

    // <- Getter & Setter ->
    public Client getSender() {
        return sender;
    }

    public String getMsg() {
        return msg;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    // <- Static ->
}
