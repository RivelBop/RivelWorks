package com.rivelbop.rivelworks.networking;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Handles server creation, connection, and handling.
 *
 * @author David Jerzak (RivelBop)
 */
public abstract class NetworkServer implements Listener, Disposable {
    /**
     * Handles server connection and packet data.
     */
    protected Server server;

    /**
     * Used to register packets and start up a server.
     */
    protected Network network;

    /**
     * Called when a client connects to the server.
     *
     * @param connection The connection information of the connected client.
     */
    @Override
    public abstract void connected(Connection connection);

    /**
     * Called when the server receives a packet from a client.
     *
     * @param connection The connection information of the client sending the packet.
     * @param object     The data packet received, utilize instanceof to read data.
     */
    @Override
    public abstract void received(Connection connection, Object object);

    /**
     * Called when a client disconnects from the server.
     *
     * @param connection The connection information of the disconnected client.
     */
    @Override
    public abstract void disconnected(Connection connection);

    /**
     * Creates a new server using the provided {@link Network}.
     *
     * @param network The network used to create and register the server.
     */
    public NetworkServer(Network network) {
        this.server = network.newServer(this);
        this.network = network;
    }

    /**
     * Starts the server.
     */
    public void start() {
        server.start();
    }

    /**
     * Stops the server.
     */
    public void stop() {
        server.stop();
    }

    /**
     * Stops and disposes of the server.
     */
    public void dispose() {
        stop();
        try {
            server.dispose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return The server itself.
     */
    public Server getServer() {
        return server;
    }

    /**
     * @return The network used to register the server.
     */
    public Network getNetwork() {
        return network;
    }
}