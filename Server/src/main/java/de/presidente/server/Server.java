package de.presidente.server;
// <- Import ->

// <- Static_Import ->

import de.presidente.net.LoginCredentials;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    public void disconnect(final Client client){
        synchronized (clients){
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

    public boolean login(final Client client, final LoginCredentials credentials){
        boolean loggedIn = false;
        
        // TODO:(jan) Check user credentials in DB. And set username & user ID in client.\
        System.out.println("Server.login");

        return loggedIn;
    }

    private void run() {
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
