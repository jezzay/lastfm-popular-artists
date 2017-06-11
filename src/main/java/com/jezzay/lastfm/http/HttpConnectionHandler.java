package com.jezzay.lastfm.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HttpConnectionHandler implements Runnable {
    private final Socket socket;
    private final RequestHandler requestHandler;

    HttpConnectionHandler(Socket socket, RequestHandler requestHandler) {
        this.socket = socket;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

                OutputStreamWriter writer
                        = new OutputStreamWriter(this.socket.getOutputStream())
        ) {
            this.requestHandler.handleRequest(reader, writer);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
