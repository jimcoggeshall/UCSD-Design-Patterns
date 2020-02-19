package edu.ucsd.dp.finalproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

/**
 *
 * @author jcc
 */
public class JsonInputSourceFactory implements InputSourceFactory {
    
    private String inputFile;
    private int socketPort;
        
    /**
     * Properties may not contain both a socket port and an input filename.
     * @param properties
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public JsonInputSourceFactory(Properties properties) throws FileNotFoundException, IOException {
        if (!(properties.containsKey("inputFile") ^ properties.containsKey("socketPort"))) {
            throw new RuntimeException("Must specify either inputFile or socketPort, not both");
        }
        inputFile = properties.getProperty("inputFile", null);
        socketPort = Integer.parseInt(properties.getProperty("socketPort", "-1"));        
    }
    
    /**
     * This is a case of striding across worlds!
     * 
     * @return input The stream. 
     */
    @Override
    public Stream createInputStream() {
        Stream s = null;
        if (inputFile != null) {
            try {
                s = new BufferedReader(new FileReader(inputFile)).lines();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (socketPort >= 0) {
            try {
                SocketStreamAdapter adapter = new SocketStreamAdapter(socketPort);
                s = adapter.getStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return s;
    }

    @Override
    public InputParser createInputParser() {
        return new JsonInputParser();
    }
    
}
