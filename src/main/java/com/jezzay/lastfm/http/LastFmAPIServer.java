package com.jezzay.lastfm.http;


import java.io.IOException;
import java.net.ServerSocket;

/**
 * API server that listens for GET requests to its registered API paths.
 */
public class LastFmAPIServer {

    private int port = 8080;

    public LastFmAPIServer(int port) {
        this.port = port;
    }

    public void acceptConnections() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                new Thread(new LastFmHttpConnectionHandler(serverSocket.accept())).start();
            }
        }
    }
}
