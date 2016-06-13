package de.presidente.server;
// <- Import ->

// <- Static_Import ->

import de.presidente.net.Packet;
import de.presidente.net.Packet_000_ConnectionClosed;
import de.presidente.net.Packet_001_Login;
import de.presidente.net.Packet_003_Permission;
import de.presidente.net.Packet_004_LobbyEnter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static de.presidente.net.Permission.DENIED;
import static de.presidente.net.Permission.GUARANTED;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Client implements Runnable {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final Server server;

    private final Socket socket;

    private volatile boolean running = false;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private String userName;

    private long uID;

    // <- Static ->

    // <- Constructor ->
    public Client(final Server server, final Socket socket) {
        this.server = server;

        this.socket = socket;

        try {
            ois = new ObjectInputStream(this.socket.getInputStream());
            oos = new ObjectOutputStream(this.socket.getOutputStream());

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    // <- Abstract ->

    // <- Object ->
    private Packet accept() {
        Packet packet = null;

        try {
            packet = (Packet) ois.readObject();
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return packet;
    }

    private void close() {
        try {
            if (ois != null)
                ois.close();

            if (ois != null)
                oos.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        server.disconnect(this);
    }

    @Override
    public void run() {
        running = true;

        // Login.
        do {
            final Packet packet = accept();

            if (packet instanceof Packet_001_Login) {
                final Packet_001_Login loginPacket = (Packet_001_Login) packet;

                final boolean loggedIn = server.login(this, loginPacket.getLoginCredentials());

                loginPacket.getLoginCredentials().errase();

                if (loggedIn) {
                    send(new Packet_003_Permission(GUARANTED));

                    break;
                } else
                    send(new Packet_003_Permission(DENIED));
            } else
                send(new Packet_000_ConnectionClosed());
        } while (true);


        while (running) {
            send(new Packet_004_LobbyEnter(server.getLobby().getGameNames()));

            server.enterLobby(this);

            // TODO:(jan) Handle Lobby actions.
        }
    }

    private boolean send(final Packet packet) {
        boolean successfulWrite = false;

        try {
            oos.writeObject(packet);

            successfulWrite = true;
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return successfulWrite;
    }

    // <- Getter & Setter ->


    // <- Static ->
}
