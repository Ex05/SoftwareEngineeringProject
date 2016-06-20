package de.presidente.server;
// <- Import ->

// <- Static_Import ->

import de.janik.passwd.PasswordService;
import de.presidente.net.LoginCredentials;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static de.presidente.server.util.Constants.DB_NAME;
import static de.presidente.server.util.Constants.DB_PASSWD;
import static de.presidente.server.util.Constants.DB_PORT;
import static de.presidente.server.util.Constants.DB_URL;
import static de.presidente.server.util.Constants.DB_USER;
import static de.presidente.server.util.Constants.PREPARED_STATEMENT_SELECT_ID;
import static de.presidente.server.util.Constants.PREPARED_STATEMENT_SELECT_PASSWORD;
import static de.presidente.server.util.Constants.PREPARED_STATEMENT_SELECT_SALT;
import static de.presidente.server.util.Constants.TABLE_USER_COLUMN_PASSWORD;
import static de.presidente.server.util.Constants.TABLE_USER_COLUMN_SALT;
import static de.presidente.server.util.DB_Manager.OpenConnection;
import static de.presidente.server.util.Resources.DB_CONNECTION;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class Server {
    // <- Public ->
    // <- Protected ->

    // <- Private->
    private final int port;

    private ServerSocket socket;

    private volatile boolean running = false;

    private final ServerThreadFactory threadFactory;

    private final ExecutorService threadPool;

    private final List<Client> clients;

    private final Lobby lobby;

    private long numConnectedClients;

    // <- Static ->

    // <- Constructor ->
    public Server(final int port) {
        this.port = port;

        threadFactory = new ServerThreadFactory("ClientThreads", 0, "ClientThread");

        threadPool = Executors.newCachedThreadPool(threadFactory);

        clients = new ArrayList<>(128);

        numConnectedClients = 0;

        lobby = new Lobby();
    }

    // <- Abstract ->

    // <- Object ->
    private Client accept() {
        Client client = null;

        try {
            final Socket clientSocket = socket.accept();

            System.out.printf("Client[%s] opened connection.\n", clientSocket.getRemoteSocketAddress());

            client = new Client(this, clientSocket);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return client;
    }

    private void close() {
        try {
            if (socket != null)
                socket.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(final Client client) {
        System.out.printf("Client[%s] closed connection.\n", client.getSocket().getRemoteSocketAddress());

        synchronized (clients) {
            clients.remove(client);

            numConnectedClients--;
        }
    }

    public void enterLobby(final Client client) {
        synchronized (lobby) {
            lobby.enter(client);
        }
    }

    public void leaveLobby(final Client client) {
        synchronized (lobby) {
            lobby.leave(client);
        }
    }

    public boolean login(final LoginCredentials credentials) {
        boolean loggedIn = false;

        DB_CONNECTION = OpenConnection(DB_CONNECTION, DB_URL, DB_PORT, DB_NAME, DB_USER, DB_PASSWD);

        try (final PreparedStatement ps = DB_CONNECTION.prepareStatement(PREPARED_STATEMENT_SELECT_PASSWORD)) {
            ps.setString(1, credentials.getUserName());

            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                final byte[] password = rs.getBytes(TABLE_USER_COLUMN_PASSWORD);

                loggedIn = PasswordService.GetInstance().validate(password, credentials.getSaltedPasswordHash());
            }

        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return loggedIn;
    }

    public byte[] receiveSalt(final String userName) {
        DB_CONNECTION = OpenConnection(DB_CONNECTION, DB_URL, DB_PORT, DB_NAME, DB_USER, DB_PASSWD);

        byte[] salt = null;

        try (final PreparedStatement ps = DB_CONNECTION.prepareStatement(PREPARED_STATEMENT_SELECT_SALT)) {
            ps.setString(1, userName);

            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                salt = rs.getBytes(TABLE_USER_COLUMN_SALT);
            }

        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return salt;
    }

    public boolean checkUserNameAvailability(final String userName) {
        boolean available = false;

        DB_CONNECTION = OpenConnection(DB_CONNECTION, DB_URL, DB_PORT, DB_NAME, DB_USER, DB_PASSWD);

        try (final PreparedStatement ps = DB_CONNECTION.prepareStatement(PREPARED_STATEMENT_SELECT_ID)) {
            ps.setString(1, userName);

            final ResultSet rs = ps.executeQuery();

            if (!rs.next())
                available = true;

        } catch (final SQLException e) {
            e.printStackTrace();
        }

        return available;
    }

    public boolean register(final LoginCredentials loginCredentials, final byte[] salt) {
        boolean registered = false;

        if (checkUserNameAvailability(loginCredentials.getUserName())) {

            DB_CONNECTION = OpenConnection(DB_CONNECTION, DB_URL, DB_PORT, DB_NAME, DB_USER, DB_PASSWD);

            try (final PreparedStatement ps = DB_CONNECTION.prepareStatement(PREPARED_STATEMENT_SELECT_ID)) {
                ps.setString(1, loginCredentials.getUserName());
                ps.setBytes(2, loginCredentials.getSaltedPasswordHash());
                ps.setBytes(3, salt);

                if (ps.executeUpdate() == 1)
                    registered = true;

            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }

        return registered;
    }

    private void run() {
        System.out.println("Starting server at port:" + port);

        try {
            socket = new ServerSocket(port);


        } catch (final IOException e) {
            e.printStackTrace();

            running = false;
        }

        while (running) {
            final Client client = accept();

            synchronized (clients) {
                clients.add(client);

                numConnectedClients++;
            }

            threadPool.execute(client);
        }

        close();
    }

    public synchronized void start() {
        if (running)
            return;

        running = true;

        run();
    }

    public synchronized void stop() {
        if (!running)
            return;

        running = false;
    }

    // <- Getter & Setter ->
    public boolean isRunning() {
        return running;
    }

    public int getPort() {
        return port;
    }

    public long getNumConnectedClients() {
        return numConnectedClients;
    }

    public Lobby getLobby() {
        return lobby;
    }

    // <- Static ->
}
