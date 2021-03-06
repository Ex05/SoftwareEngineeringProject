package de.presidente.jungle_king.net;
// <- Import ->

// <- Static_Import ->

import de.presidente.net.Packet;
import de.presidente.net.Packet_000_ConnectionClosed;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Jan.Marcel.Janik [©2016]
 */
public final class ConnectionManager {
    // <- Public ->
    // <- Protected ->

    private final LinkedBlockingDeque<Packet> inboundPacketBuffer;

    private final Queue<Packet> outboundPacketBuffer;

    private final Thread threadOutbound;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;

    private Socket socket;

    // <- Static ->

    // <- Constructor ->
    public ConnectionManager(final String address, final int port) {
        inboundPacketBuffer = new LinkedBlockingDeque<>(32);

        outboundPacketBuffer = new ArrayDeque<>(32);

        threadOutbound = new Thread(this::outBoundHandler, "Async_Network Thread (Outbound)");
        threadOutbound.setDaemon(true);

        // TODO:(jan) Check on every send/receive if the connection is alive.
        final Thread threadInbound = new Thread(this::inBoundHandler, "Async_Network Thread (Inbound)");
        threadInbound.setDaemon(true);

        final Thread t1 = new Thread(() -> {
            byte repetitions = 1;
            int timeout = 300;
            do {
                socket = OpenSocket(address, port, timeout);

                timeout += timeout / 2;

                if (socket != null) {
                    System.out.println("Connected to server.");

                    threadOutbound.start();

                    threadInbound.start();
                } else
                    System.out.println("Trying to connect to server.");
            } while (socket == null && repetitions++ < 10);

            if (socket == null) {
                // TODO:(jan) Make this not use OptionPane.
                final JFrame frame = new JFrame();
                frame.setAlwaysOnTop(true);

                JOptionPane.showConfirmDialog(frame, "Unable to reach server[" + address + "]", "Server offline!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);

                System.exit(-1);
            }
        });

        t1.setDaemon(true);
        t1.start();
    }

    // <- Abstract ->

    // <- Object ->
    private void outBoundHandler() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }

        while (true)
            try {
                synchronized (threadOutbound) {
                    threadOutbound.wait();
                }
                synchronized (outboundPacketBuffer) {
                    while (!outboundPacketBuffer.isEmpty()) {
                        outboundPacketBuffer.forEach(this::write);

                        outboundPacketBuffer.clear();
                    }
                }
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void inBoundHandler() {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (final IOException e) {
            e.printStackTrace();
        }
        Packet packet;

        while ((packet = accept()) != null)
            inboundPacketBuffer.add(packet);
    }

    private void write(final Packet packet) {
        try {
            if (oos != null)
                oos.writeObject(packet);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public boolean send(final Packet packet) {
        synchronized (outboundPacketBuffer) {
            outboundPacketBuffer.add(packet);
        }

        synchronized (threadOutbound) {
            threadOutbound.notify();
        }

        return false;
    }

    public Packet retrievePacket() {
        Packet packet = null;
        if (inboundPacketBuffer.size() != 0)
            try {
                packet = inboundPacketBuffer.take();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

        return packet;
    }

    private Packet accept() {
        Packet packet = null;

        try {
            final Object o = ois.readObject();

            if (o instanceof Packet)
                packet = (Packet) o;

        } catch (final IOException | ClassNotFoundException e) {
            // Ignore
        }

        return packet;
    }

    public void close() {
        if (socket != null && !socket.isClosed())
            write(new Packet_000_ConnectionClosed());

        try {
            if (socket != null)
                socket.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    // <- Getter & Setter ->
    public static Socket OpenSocket(final String iNetAddress, final int port, final int timeout) {
        final boolean[] available = {false};

        final Thread callingThread = Thread.currentThread();

        final Socket outgoingConnection = new Socket();
        final Thread connectorThread = new Thread(() -> {
            try {
                outgoingConnection.connect(new InetSocketAddress(iNetAddress, port));
                outgoingConnection.close();
            } catch (final Exception ignored) {
                // Ignore
            }

            available[0] = true;

            synchronized (callingThread) {
                callingThread.notify();
            }
        });
        connectorThread.setDaemon(true);
        connectorThread.start();

        final Thread timer = new Thread(() -> {
            try {
                Thread.sleep(timeout);
            } catch (final Exception e) {
                e.printStackTrace();
            }

            connectorThread.interrupt();
            try {
                outgoingConnection.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }

            if (!available[0])
                synchronized (callingThread) {
                    callingThread.notify();
                }
        });
        timer.setDaemon(true);
        timer.start();

        synchronized (callingThread) {
            try {
                callingThread.wait();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }

        Socket socket = null;
        if (available[0])
            try {
                socket = new Socket(iNetAddress, port);
            } catch (final IOException e) {
                e.printStackTrace();
            }

        return socket;
    }

    // <- Static ->
}
