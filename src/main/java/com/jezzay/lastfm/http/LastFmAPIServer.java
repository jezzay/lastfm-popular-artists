package com.jezzay.lastfm.http;


import java.io.IOException;
import java.net.ServerSocket;

/**
 * API server that listens for GET requests to its registered API paths.
 */
public class LastFmAPIServer {

    private final RequestHandler requestHandler;
    private int port = 8080;

    public LastFmAPIServer(int port) {
        this.port = port;
        this.requestHandler = new RequestHandler();
    }

    public void acceptConnections() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (true) {
                new Thread(new HttpConnectionHandler(serverSocket.accept(), requestHandler))
                        .start();
            }
        }
    }
}
