package edu.ucsd.dp.finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jcc
 */
public class TestAbstractInputFactory {
    

    private List<Map> getAsList(Stream inputStream, InputParser inputParser) {
        return (List<Map>) inputStream.map(r -> inputParser.parseInputRecord((String) r))
            .collect(Collectors.toList());
    }
    
    @Test
    public void testCompareFactoriesFile() throws IOException  {
        
        Properties firstProperties = new Properties();
        firstProperties.put("inputFile", "src/test/resources/PurchasingCards2010.json");
        InputSourceFactory firstFactory = new JsonInputSourceFactory(firstProperties);
        
        Properties secondProperties = new Properties();
        secondProperties.put("inputFile", "src/test/resources/PurchasingCards2010.csv");
        InputSourceFactory secondFactory = new CsvInputSourceFactory(secondProperties);
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        
        List<Map> outFromJson = getAsList(firstFactory.createInputStream(), firstFactory.createInputParser());
        List<Map> outFromCsv = getAsList(secondFactory.createInputStream(), secondFactory.createInputParser());
        
        int numRecordsJson = outFromJson.size();
        int numRecordsCsv = outFromCsv.size();
        assertEquals(numRecordsJson, numRecordsCsv);
        
        for (int i = 0; i < numRecordsJson; i++) {
            Map<String, Object> recordFromJson = outFromJson.get(i);
            Map<String, Object> recordFromCsv = outFromCsv.get(i);
            assertTrue(recordFromJson.equals(recordFromCsv));
        }

    }
    
    @Test
    public void testCompareFactoriesSocket() throws IOException  {
        
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
                        new FileReader("src/test/resources/PurchasingCards2010.json")
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
        
        Properties firstProperties = new Properties();
        firstProperties.put("socketPort", "9999");
        InputSourceFactory firstFactory = new JsonInputSourceFactory(firstProperties);
        
        Properties secondProperties = new Properties();
        secondProperties.put("inputFile", "src/test/resources/PurchasingCards2010.csv");
        InputSourceFactory secondFactory = new CsvInputSourceFactory(secondProperties);
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        
        List<Map> outFromJson = getAsList(firstFactory.createInputStream(), firstFactory.createInputParser());
        List<Map> outFromCsv = getAsList(secondFactory.createInputStream(), secondFactory.createInputParser());
        
        int numRecordsJson = outFromJson.size();
        int numRecordsCsv = outFromCsv.size();
        assertEquals(numRecordsJson, numRecordsCsv);
        
        for (int i = 0; i < numRecordsJson; i++) {
            Map<String, Object> recordFromJson = outFromJson.get(i);
            Map<String, Object> recordFromCsv = outFromCsv.get(i);
            assertTrue(recordFromJson.equals(recordFromCsv));
        }

    }
    
}
