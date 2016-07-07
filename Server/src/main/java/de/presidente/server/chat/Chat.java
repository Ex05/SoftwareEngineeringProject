package de.presidente.server.chat;
// <- Import ->

// <- Static_Import ->

import de.presidente.net.Packet_020_ChatMsgReceive;
import de.presidente.server.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Chat {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final List<Client> clients;

    private final List<ChatMessage> messages;
    // <- Static ->

    // <- Constructor ->
    public Chat() {
        clients = new ArrayList<>(5);

        messages = new ArrayList<>();
    }

    // <- Abstract ->

    // <- Object ->
    public void join(final Client client) {
        if (clients.add(client))
            clients.forEach(c -> c.send(new Packet_020_ChatMsgReceive(client.getUserName(), "Joined the room.")));
    }

    public void leave(final Client client) {
        if (clients.remove(client))
            clients.forEach(c -> c.send(new Packet_020_ChatMsgReceive(client.getUserName(), "Left the room.")));
    }

    public void addMessage(final Client client, final String msg) {
        messages.add(new ChatMessage(client, msg));

        clients.forEach(c -> c.send(new Packet_020_ChatMsgReceive(client.getUserName(), msg)));
    }

    // <- Getter & Setter ->
    // <- Static ->
}
