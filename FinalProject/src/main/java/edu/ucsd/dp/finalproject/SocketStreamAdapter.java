package edu.ucsd.dp.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.stream.Stream;

/**
 *
 * "Convert the interface of a class into another interface clients expect. 
 * Adapter lets classes work together that couldn't otherwise because of 
 * incompatible interfaces."
 * 
 * @author jcc
 */
public class SocketStreamAdapter {
    
    private ServerSocket serverSocket;
    
    public SocketStreamAdapter(int socketPort) throws IOException {   
        serverSocket = new ServerSocket(socketPort);
        serverSocket.setSoTimeout(10000);
    }
    
    public Stream getStream() throws IOException {
        if (!serverSocket.isBound()) {
            throw new RuntimeException("Could not read from socket!");
        }
        return new BufferedReader(
            new InputStreamReader(
                serverSocket.accept().getInputStream()
            )
        )
        .lines();
    }
    
}
