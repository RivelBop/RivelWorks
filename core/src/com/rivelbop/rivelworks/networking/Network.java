package com.rivelbop.rivelworks.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Handles class registration for clients and servers.
 *
 * @author David Jerzak (RivelBop)
 */
@SuppressWarnings({"Class", "rawtypes"})
public class Network {
    /**
     * A set of packet classes registered under the network.
     */
    private final HashSet<Class> REGISTERED_CLASSES = new HashSet<>();

    /**
     * The network's IP Address ('localhost' by default).
     */
    private String IP = "localhost";

    /**
     * The network's port number (54555 by default).
     */
    private int port = 54555;

    /**
     * Adds the provided classes as registered packet data for the network.
     *
     * @param classes The packet classes to register.
     */
    public void addClass(Class... classes) {
        REGISTERED_CLASSES.addAll(Arrays.asList(classes));
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
     */
    public void connectClient(Client client) throws IOException {
        client.connect(5000, IP, port, port);
    }

    /**
     * Creates and returns a new client with the Network data.
     *
     * @param listener The listener to add to the client.
     * @return Client with all Network data (IP, Port, Classes).
     */
    public Client newClient(Listener listener) {
        Client client = new Client();
        register(client);
        client.addListener(listener);
        return client;
    }

    /**
     * Creates, binds, and returns a new server with the Network data.
     *
     * @param listener The listener to add to the server.
     * @return Server with all Network data (IP, Port, Classes).
     */
    public Server newServer(Listener listener) throws IOException {
        Server server = new Server();
        register(server);
        server.addListener(listener);
        server.bind(new InetSocketAddress(IP, port), new InetSocketAddress(IP, port));
        return server;
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

        System.err.println("Port must be between 0 to 65535!");
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