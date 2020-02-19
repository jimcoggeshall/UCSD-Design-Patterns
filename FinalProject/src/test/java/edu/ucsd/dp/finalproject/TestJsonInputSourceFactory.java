package edu.ucsd.dp.finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author jcc
 */
public class TestJsonInputSourceFactory {
    
    @Test
    public void testTransformFields() throws IOException  {
        Properties properties = new Properties();
        properties.put("inputFile", "src/test/resources/PurchasingCardsSmall.json");
        InputSourceFactory factory = new JsonInputSourceFactory(properties);
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        
        Stream inputStream = factory.createInputStream();
        InputParser inputParser = factory.createInputParser();
        
        InputTransformer inputTransformer = new InputTransformerBuilder()
            .addFieldTransformer("DATE", new FieldTransformer<String, Date>() {
                @Override
                public Date execute(String rawValue) {
                Date date;
                    try {
                        date = format.parse(rawValue);
                    } catch (ParseException e) {
                        throw new RuntimeException("Error parsing date ", e);
                    }
                    return date;
                }
            })
            .addFieldTransformer("AMOUNT", new FieldTransformer<String, Double>())
            .build();

        inputStream.map(r -> {
                System.out.println("Raw: " + r);
                return inputParser.parseInputRecord((String) r);
            })
            .map(r -> {
                System.out.println("Parsed: " + r);
                return inputTransformer.transformInput((Map<String, Object>) r);
            })
            .forEachOrdered(r -> System.out.println("Transformed: " + r));
        
    }
    
    @Test
    public void testInvalidConfiguration() {
        Properties properties = new Properties();
        properties.put("inputFile", "src/test/resources/PurchasingCardsSmall.json");
        properties.put("socketPort", "9999");
        boolean caughtException = false;
        try {
            InputSourceFactory factory = new JsonInputSourceFactory(properties);
        } catch (Exception e) {
            caughtException = true;
        }
        assertTrue(caughtException);
    }

    @Test
    public void testTransformFieldsFromSocket() throws IOException  {
        
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = new Socket();
                try {
                    Thread.sleep(1000);
                    SocketAddress address = new InetSocketAddress("127.0.0.1", 9999);
                    socket.connect(address);
                    PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(
                        new FileReader("src/test/resources/PurchasingCardsSmall.json")
                    );
                    String line;
                    while ((line = br.readLine()) != null) {
                        pw.println(line);
                    }
                    br.close();
                    socket.close();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        client.start();
        
        Properties properties = new Properties();
        properties.put("socketPort", "9999");
        InputSourceFactory factory = new JsonInputSourceFactory(properties);
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        
        Stream inputStream = factory.createInputStream();
        InputParser inputParser = factory.createInputParser();
        
        InputTransformer inputTransformer = new InputTransformerBuilder()
            .addFieldTransformer("DATE", new FieldTransformer<String, Date>() {
                @Override
                public Date execute(String rawValue) {
                Date date;
                    try {
                        date = format.parse(rawValue);
                    } catch (ParseException e) {
                        throw new RuntimeException("Error parsing date ", e);
                    }
                    return date;
                }
            })
            .addFieldTransformer("AMOUNT", new FieldTransformer<String, Double>())
            .build();

        
        inputStream.map(r -> {
                System.out.println("Raw: " + r);
                return inputParser.parseInputRecord((String) r);
            })
            .map(r -> {
                System.out.println("Parsed: " + r);
                return inputTransformer.transformInput((Map<String, Object>) r);
            })
            .forEachOrdered(r -> System.out.println("Transformed: " + r));
        
        client.interrupt();

        
    }
    
}
