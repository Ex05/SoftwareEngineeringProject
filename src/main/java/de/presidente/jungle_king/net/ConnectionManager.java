package de.presidente.jungle_king.net;
// <- Import ->

// <- Static_Import ->

import de.presidente.net.Packet;
import de.presidente.net.Packet_000_ConnectionClosed;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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

        final Thread threadInbound = new Thread(this::inBoundHandler, "Async_Network Thread (Inbound)");
        threadInbound.setDaemon(true);

        try {
            socket = new Socket(InetAddress.getByName(address), port);

        } catch (final IOException e) {
            e.printStackTrace();
        }

        threadInbound.start();
        threadOutbound.start();
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

    public Packet retriev() {
        Packet packet = null;
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
        write(new Packet_000_ConnectionClosed());

        try {
            socket.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    // <- Getter & Setter ->
    // <- Static ->
}
