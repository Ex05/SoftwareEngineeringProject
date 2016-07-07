package de.presidente.server;
// <- Import ->

// <- Static_Import ->

import de.presidente.net.Packet_021_PreGameLobbyClientUpdate;
import de.presidente.server.chat.Chat;

import java.util.ArrayList;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Game {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Lobby lobby;

    private final String name;

    private final Client owner;

    private final ArrayList<Client> clients;

    private final Chat chat;

    private byte playerCount;
    // <- Static ->

    // <- Constructor ->
    public Game(final Lobby lobby, final String name, final Client owner) {
        this.lobby = lobby;
        this.name = name;
        this.owner = owner;

        clients = new ArrayList<>(5);

        chat = new Chat();

        enter(owner);
    }

    // <- Abstract ->

    // <- Object ->
    public boolean enter(final Client client) {
        if (clients.size() >= 5)
            return false;
        else {
            final boolean entered;

            synchronized (clients) {
                entered = clients.add(client);
            }

            if (entered) {
                client.setCurrentGame(this);

                playerCount++;

                chat.join(client);
            }

            return entered;
        }
    }

    public void leave(final Client client) {
        final boolean remove;

        synchronized (clients) {
            remove = clients.remove(client);
        }

        if (remove) {
            playerCount--;

            chat.leave(client);

            updatePreGameLobby();
        }

        if (playerCount <= 0)
            lobby.remove(this);
    }

    // <- Getter & Setter ->
    public String getName() {
        return name;
    }

    public Client getOwner() {
        return owner;
    }

    public byte getPlayerCount() {
        return playerCount;
    }

    public Chat getChat() {
        return chat;
    }

    public void updatePreGameLobby() {
        synchronized (clients) {
         clients.forEach(client -> client.send(new Packet_021_PreGameLobbyClientUpdate(clients.stream().map(Client::getUserName).toArray(String[]::new))));
        }
    }

// <- Static ->
}
