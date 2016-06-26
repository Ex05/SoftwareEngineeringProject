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
import de.presidente.net.Packet_009_UsernameAvailable;
import de.presidente.net.Packet_010_RegistrationConfirmation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static de.presidente.net.Permission.DENIED;
import static de.presidente.net.Permission.GRANTED;
import static java.time.ZonedDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

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
            System.out.printf("%s >> %s\n", now().format(ofPattern("HH:mm:ss_SSS")), packet);

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

            if (packet instanceof Packet_005_ReceiveSalt)
                handlePacket_005_ReceiveSalt((Packet_005_ReceiveSalt) packet);
            else if (packet instanceof Packet_008_CheckUsernameAvailability)
                handlePacket_008_CheckUserNameAvailability((Packet_008_CheckUsernameAvailability) packet);
            else if (packet instanceof Packet_007_Register)
                handelPacket_007_Register((Packet_007_Register) packet);
            else if (packet instanceof Packet_001_Login) {
                if (handlePacket_001_Login((Packet_001_Login) packet))
                    break;
            } else {
                send(new Packet_000_ConnectionClosed());

                close();

                return;
            }
        } while (true);

        while (running) {
            final Lobby lobby = server.getLobby();

            send(new Packet_004_LobbyEnter(lobby.getGameNames(), lobby.getConnectedClients()));

            server.enterLobby(this);

            // TODO:(jan) Handle Lobby actions.
            System.out.println("Client.run[TODO:(jan) Handle Lobby actions.]");

            break;
        }
    }

    private boolean handlePacket_001_Login(final Packet_001_Login packet) {
        final boolean loggedIn = server.login(this, packet.getLoginCredentials());

        packet.getLoginCredentials().erase();

        if (loggedIn)
            send(new Packet_003_Permission(GRANTED));
        else
            send(new Packet_003_Permission(DENIED));

        return loggedIn;
    }

    private void handelPacket_007_Register(final Packet_007_Register packet) {
        final boolean registered = server.register(packet.getLoginCredentials(), packet.getSalt());

        send(new Packet_010_RegistrationConfirmation(registered));
    }

    private void handlePacket_008_CheckUserNameAvailability(final Packet_008_CheckUsernameAvailability packet) {
        final boolean available = server.checkUserNameAvailability(packet.getUserName());

        send(new Packet_009_UsernameAvailable(available ? GRANTED : DENIED));
    }

    private void handlePacket_005_ReceiveSalt(final Packet_005_ReceiveSalt salt1) {
        final byte[] salt = server.receiveSalt(salt1.getUserName());

        if (salt == null)
            send(new Packet_003_Permission(DENIED));
        else
            send(new Packet_006_Salt(salt));
    }

    private boolean send(final Packet packet) {
        boolean successfulWrite = false;

        try {
            oos.writeObject(packet);

            System.out.printf("%s << %s\n", now().format(ofPattern("HH:mm:ss_SSS")), packet);

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
