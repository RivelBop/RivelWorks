package com.rivelbop.rivelworks.networking;

import com.badlogic.gdx.utils.ObjectSet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Handles class registration for clients and servers.
 *
 * @author David Jerzak (RivelBop)
 */
@SuppressWarnings({"Class", "rawtypes"})
public class Network {
    private static final String LOG_TAG = Network.class.getSimpleName();

    /**
     * A set of packet classes registered under the network.
     */
    private final ObjectSet<Class> REGISTERED_CLASSES = new ObjectSet<>();

    /**
     * Stores the size of the buffer to send and receive.
     */
    private int BUFFER_SIZE;

    /**
     * The network's IP Address ('localhost' by default).
     */
    private String IP = "localhost";

    /**
     * The network's port number (54555 by default).
     */
    private int port = 54555;

    /**
     * Creates a network with the buffer size of 4377.
     */
    public Network() {
        this.BUFFER_SIZE = 4377;
    }

    /**
     * Creates a network with the specified buffer size.
     *
     * @param bufferSize The size of the buffer.
     */
    public Network(int bufferSize) {
        this.BUFFER_SIZE = bufferSize;
    }

    /**
     * Adds the provided classes as registered packet data for the network.
     *
     * @param classes The packet classes to register.
     */
    public void addClass(Class... classes) {
        REGISTERED_CLASSES.addAll(classes);
    }

    /**
     * Registers all the network registered packet classes to the provided {@link EndPoint} (Client/Server).
     *
     * @param endPoint The point to register the packet classes to.
     */
    public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        for (Class c : REGISTERED_CLASSES) {
            kryo.register(c);
        }
    }

    /**
     * Connects the provided client to a server by using all the necessary Network details (IP, Port).
     *
     * @param client The client to connect to the Network's server.
     * @throws IOException Client is unable to connect to the server/network.
     */
    public void connectClient(Client client) throws IOException {
        client.connect(5000, IP, port, port);
        Log.info(LOG_TAG, "New client connected to {" + IP + ":" + port + "}.");
    }

    /**
     * Creates and returns a new client with the Network data.
     *
     * @param listener The listener to add to the client.
     * @return Client with all Network data (IP, Port, Classes).
     */
    public Client newClient(Listener listener) {
        Client client = new Client(BUFFER_SIZE, BUFFER_SIZE);
        register(client);
        client.addListener(listener);
        return client;
    }

    /**
     * Creates, binds, and returns a new server with the Network data.
     *
     * @param listener The listener to add to the server.
     * @return Server with all Network data (IP, Port, Classes).
     * @throws IOException Server is unable to bind to the Network's IP and Port.
     */
    public Server newServer(Listener listener) throws IOException {
        Server server = new Server(BUFFER_SIZE, BUFFER_SIZE);
        register(server);
        server.addListener(listener);

        InetSocketAddress inetSocketAddress = new InetSocketAddress(IP, port);
        server.bind(inetSocketAddress, inetSocketAddress);
        return server;
    }

    /**
     * Sets the buffer size of the network.
     *
     * @param bufferSize The size of the buffer to set to, must be 0 or more!
     */
    public void setBufferSize(int bufferSize) {
        if (bufferSize > -1) {
            this.BUFFER_SIZE = bufferSize;
            return;
        }

        Log.error(LOG_TAG, "Buffer size must be 0 or positive!");
    }

    /**
     * @param ip The new IP address to set the Networks IP to.
     */
    public void setIP(String ip) {
        this.IP = ip;
    }

    /**
     * @param port The new port to set the Network port to.
     */
    public void setPort(int port) {
        if (port > -1 && port <= 65535) {
            this.port = port;
            return;
        }

        Log.error(LOG_TAG, "Port must be between 0 to 65535!");
    }

    /**
     * @param ip   The new IP address to set the Networks IP to.
     * @param port The new port to set the Network port to.
     */
    public void set(String ip, int port) {
        setIP(ip);
        setPort(port);
    }

    /**
     * @return The Network's stored IP address.
     */
    public String getIP() {
        return IP;
    }

    /**
     * @return The Network's stored port number.
     */
    public int getPort() {
        return port;
    }
}