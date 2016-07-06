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
import de.presidente.net.Packet_008_CheckUserNameAvailability;
import de.presidente.net.Packet_009_UserNameAvailable;
import de.presidente.net.Packet_010_RegistrationConfirmation;
import de.presidente.net.Packet_011_CheckGameName;
import de.presidente.net.Packet_012_GameNameAvailable;
import de.presidente.net.Packet_013_CreateNewGame;
import de.presidente.net.Packet_014_CreateNewGameConfirmation;
import de.presidente.net.Packet_015_EnterPreGameLobby;
import de.presidente.net.Packet_016_EnterPreGameLobbyConfirmation;
import de.presidente.net.Packet_017_LeavePreGameLobby;
import de.presidente.net.Packet_019_ChatMsgSend;
import de.presidente.net.Packet_020_ChatMsgReceive;

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

    private Game currentGame;
    private Game currentgame;

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

            if (packet.getClass().equals(Packet_005_ReceiveSalt.class))
                handlePacket_005_ReceiveSalt((Packet_005_ReceiveSalt) packet);
            else if (packet.getClass().equals(Packet_008_CheckUserNameAvailability.class))
                handlePacket_008_CheckUserNameAvailability((Packet_008_CheckUserNameAvailability) packet);
            else if (packet.getClass().equals(Packet_007_Register.class))
                handelPacket_007_Register((Packet_007_Register) packet);
            else if (packet.getClass().equals(Packet_001_Login.class))
                handlePacket_001_Login((Packet_001_Login) packet);
            else if (packet.getClass().equals(Packet_011_CheckGameName.class))
                handlePacket_011_CheckGameName((Packet_011_CheckGameName) packet);
            else if (packet.getClass().equals(Packet_013_CreateNewGame.class))
                handlePacket_013_CreateNewGame((Packet_013_CreateNewGame) packet);
            else if (packet.getClass().equals(Packet_015_EnterPreGameLobby.class))
                handlePacket_015_EnterPreGameLobby((Packet_015_EnterPreGameLobby) packet);
            else if (packet.getClass().equals(Packet_017_LeavePreGameLobby.class))
                handlePacket_017_LeavePreGameLobby((Packet_017_LeavePreGameLobby) packet);
            else if (packet.getClass().equals(Packet_019_ChatMsgSend.class))
                handlePacket_019_ChatMsgSend((Packet_019_ChatMsgSend) packet);
        }
        while (true);
    }

    private void handlePacket_019_ChatMsgSend(final Packet_019_ChatMsgSend packet) {
        currentGame.getChat().addMessage(this, packet.getMsg());
    }

    private void handlePacket_017_LeavePreGameLobby(final Packet_017_LeavePreGameLobby packet) {
        currentGame.leave(this);

        currentGame = null;

        enterLobby();
    }

    private void enterLobby() {
        final Lobby lobby = server.getLobby();

        server.getLobby().enter(this);

        send(new Packet_004_LobbyEnter(lobby.getGames(), lobby.getConnectedClients()));
    }

    private void handlePacket_015_EnterPreGameLobby(final Packet_015_EnterPreGameLobby packet) {
        send(new Packet_016_EnterPreGameLobbyConfirmation(server.getLobby().enterGame(this, packet.getGameName())));
    }

    private void handlePacket_013_CreateNewGame(final Packet_013_CreateNewGame packet) {
        send(new Packet_014_CreateNewGameConfirmation(server.getLobby().createGame(this, packet.getGameName())));
    }

    private void handlePacket_011_CheckGameName(final Packet_011_CheckGameName packet) {
        send(new Packet_012_GameNameAvailable(packet.getGameName(), server.getLobby().isGameNameAvailable(packet.getGameName())));
    }

    private boolean handlePacket_001_Login(final Packet_001_Login packet) {
        final boolean loggedIn = server.login(this, packet.getLoginCredentials());

        packet.getLoginCredentials().erase();

        if (loggedIn) {
            send(new Packet_003_Permission(GRANTED));

            enterLobby();
        } else
            send(new Packet_003_Permission(DENIED));

        return loggedIn;
    }

    private void handelPacket_007_Register(final Packet_007_Register packet) {
        final boolean registered = server.register(packet.getLoginCredentials(), packet.getSalt());

        send(new Packet_010_RegistrationConfirmation(registered));
    }

    private void handlePacket_008_CheckUserNameAvailability(final Packet_008_CheckUserNameAvailability packet) {
        final boolean available = server.checkUserNameAvailability(packet.getUserName());

        send(new Packet_009_UserNameAvailable(available ? GRANTED : DENIED));
    }

    private void handlePacket_005_ReceiveSalt(final Packet_005_ReceiveSalt salt1) {
        final byte[] salt = server.receiveSalt(salt1.getUserName());

        if (salt == null)
            send(new Packet_003_Permission(DENIED));
        else
            send(new Packet_006_Salt(salt));
    }

    public boolean send(final Packet packet) {
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

    public void setU_ID(final long uID) {
        this.uID = uID;
    }

    public Game getGame() {
        return currentGame;
    }

    public void setCurrentGame(final Game currentGame) {
        this.currentGame = currentGame;
    }

    // <- Static ->
}
