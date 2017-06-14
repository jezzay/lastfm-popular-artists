package com.jezzay.lastfm;


import com.jezzay.lastfm.http.LastFmAPIServer;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        int serverPort = 8080;
        String serverEnvPort = System.getenv("LAST_FM_API_SERVER_PORT");
        if (serverEnvPort!= null) {
            serverPort = Integer.parseInt(serverEnvPort);
        }
        LastFmAPIServer lastFmAPIServer = new LastFmAPIServer(serverPort);
        lastFmAPIServer.acceptConnections();
    }
}

