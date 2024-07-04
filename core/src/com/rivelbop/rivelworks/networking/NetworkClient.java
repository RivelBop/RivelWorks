package com.rivelbop.rivelworks.networking;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

/**
 * Handles client connection and handling.
 *
 * @author David Jerzak (RivelBop)
 */
public abstract class NetworkClient implements Listener, Disposable {
    private static final String LOG_TAG = NetworkClient.class.getSimpleName();

    /**
     * Handles client connection and packet data.
     */
    protected Client client;

    /**
     * Used to register packets and connect the client.
     */
    protected Network network;

    /**
     * Called when the client connection receives a packet.
     *
     * @param connection The connection data from which the packet was sent from.
     * @param object     The data packet received, utilize instanceof to read data.
     */
    @Override
    public abstract void received(Connection connection, Object object);

    /**
     * Creates a new client using the provided {@link Network}.
     *
     * @param network The network used to create and register the client.
     */
    public NetworkClient(Network network) {
        this.client = network.newClient(this);
        this.network = network;
    }

    /**
     * Starts and connects the client to the port data provided by the {@link #network}.
     *
     * @throws IOException Client is unable to connect to the server/network.
     */
    public void connect() throws IOException {
        client.start();
        network.connectClient(client);
    }

    /**
     * Disconnects the client by calling stopping the client.
     */
    public void disconnect() {
        client.stop();
        Log.info(LOG_TAG, "Disconnected.");
    }

    /**
     * Disconnects and disposes of the client.
     */
    public void dispose() {
        disconnect();
        try {
            client.dispose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return The client itself.
     */
    public Client getClient() {
        return client;
    }

    /**
     * @return The network used to register the client.
     */
    public Network getNetwork() {
        return network;
    }
}