package de.presidente.server;
// <- Import ->

// <- Static_Import ->

import de.presidente.net.Packet;
import de.presidente.net.Packet_000_ConnectionClosed;
import de.presidente.net.Packet_001_Login;
import de.presidente.net.Packet_003_Permission;
import de.presidente.net.Packet_004_LobbyEnter;
import de.presidente.net.Packet_005_ReceiveSalt;
import de.presidente.net.Packet_006_Salt;
import de.presidente.net.Packet_007_Register;
import de.presidente.net.Packet_008_CheckUsernameAvailability;

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

            running = true;
        } catch (final IOException e) {
            // running = false;
        }
    }

    // <- Abstract ->

    // <- Object ->
    private Packet accept() {
        Packet packet = null;

        try {
            packet = (Packet) ois.readObject();
        } catch (final IOException | ClassNotFoundException | NullPointerException e) {
            // Ignored
        }

        if (packet != null)
            System.out.printf("<< %s\n", packet.getClass().getSimpleName());

        if (packet instanceof Packet_000_ConnectionClosed)
            try {
                socket.close();

                return null;
            } catch (final IOException e) {
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
        if (!running) {
            close();

            return;
        }

        // Login.
        do {
            final Packet packet = accept();

            if (packet == null) {
                close();

                return;
            }

            if (packet instanceof Packet_005_ReceiveSalt) {
                final byte[] salt = server.receiveSalt(((Packet_005_ReceiveSalt) packet).getUserName());

                if (salt == null)
                    send(new Packet_003_Permission(DENIED));
                else
                    send(new Packet_006_Salt(salt));
            } else if (packet instanceof Packet_008_CheckUsernameAvailability) {
                final boolean available = server.checkUserNameAvailability(((Packet_008_CheckUsernameAvailability) packet).getUserName());

                send(new Packet_003_Permission(available ? GUARANTED : DENIED));
            } else if (packet instanceof Packet_007_Register) {
                final Packet_007_Register registerPacket = (Packet_007_Register) packet;

                final boolean registered = server.register(registerPacket.getLoginCredentials(), registerPacket.getSalt());

                send(new Packet_003_Permission(registered ? GUARANTED : DENIED));
            } else if (packet instanceof Packet_001_Login) {
                final Packet_001_Login loginPacket = (Packet_001_Login) packet;

                final boolean loggedIn = server.login(this, loginPacket.getLoginCredentials());

                loginPacket.getLoginCredentials().erase();

                if (loggedIn) {
                    send(new Packet_003_Permission(GUARANTED));

                    break;
                } else
                    send(new Packet_003_Permission(DENIED));
            } else
                send(new Packet_000_ConnectionClosed());
        } while (true);

        while (running) {
            final Lobby lobby = server.getLobby();

            send(new Packet_004_LobbyEnter(lobby.getGameNames(), lobby.getConnectedClients()));

            server.enterLobby(this);

            // TODO:(jan) Handle Lobby actions.
        }
    }

    private boolean send(final Packet packet) {
        boolean successfulWrite = false;

        try {
            oos.writeObject(packet);

            System.out.printf(">> %s\n", packet.getClass().getSimpleName());

            successfulWrite = true;
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return successfulWrite;
    }

    // <- Getter & Setter ->
    public long getuID() {
        return uID;
    }

    public String getUserName() {
        return userName;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setuID(final long uID) {
        this.uID = uID;
    }

    // <- Static ->
}
