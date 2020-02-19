package edu.ucsd.dp.finalproject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author jcc
 */
public class Driver {
    
    /**
     * Simple demonstration of the intended use of this software. 
     * <p>
     * We have an example dataset containing payment card transactions in which 
     * most of the transactions are in a csv file. However, transactions for the
     * single most frequently-used card did not make it into the csv file and 
     * are instead put into a different file, in which each transaction is 
     * represented by a simple json object (one "json record" per transaction).
     * <p>
     * The problem at hand is to calculate the total number of cards that are 
     * active after June 2010. 
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException, ParseException {
        
        System.out.println("Creating an InputSourceFactory for input file src/main/resources/PurchasingCards.json...");
        Properties jsonProperties = new Properties();
        jsonProperties.put("inputFile", "src/main/resources/PurchasingCards.json");
        InputSourceFactory jsonFactory = new JsonInputSourceFactory(jsonProperties);
        System.out.println("ABSTRACT FACTORY: Using this InputSourceFactory to create InputParser and Stream objects...");
        InputParser jsonParser = jsonFactory.createInputParser();
        Stream jsonStream = jsonFactory.createInputStream();
        System.out.println("Created InputParser jsonParser via InputSourceFactory.createInputParser(): " + jsonParser);
        System.out.println("Created Stream jsonStream via InputSourceFactory.createInputStream(): " + jsonStream);
        
        System.out.println("ABSTRACT FACTORY: Creating an InputSourceFactory for input file src/main/resources/PurchasingCards.csv...");
        Properties csvProperties = new Properties();
        csvProperties.put("inputFile", "src/main/resources/PurchasingCards.csv");
        InputSourceFactory csvFactory = new CsvInputSourceFactory(csvProperties); 
        InputParser csvParser = csvFactory.createInputParser();
        Stream csvStream = csvFactory.createInputStream();
        System.out.println("Created InputParser csvParser via InputSourceFactory.createInputParser(): " + csvParser);
        System.out.println("Created Stream csvStream via InputSourceFactory.createInputStream(): " + csvStream);
        
        System.out.println("COMMAND: Creating a FieldTransformer to convert the \"DATE\" field in both input files to a Date object...");
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        FieldTransformer dateTransformer = new FieldTransformer<String, Date>() {    
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
        };
        
        System.out.println("BUILDER: Building an InputTransformer containing the existing FieldTransformer...");
        InputTransformer inputTransformer = new InputTransformerBuilder()
            .addFieldTransformer("DATE", dateTransformer)
            .build();
        

        
        System.out.println("Executing on the dataset:");
        System.out.println("((jsonInput ->(parseJson)-> parsedMap) ++ (csvInput ->(parseCsv)-> parsedMap))");
        System.out.println("    ->(transform)-> parsedTransformedMap");
        System.out.println("    ->(filter)-> parsedTransformedFilteredMap");
        System.out.println("    ->(map)-> accountNumber");
        System.out.println("    ->(reduce)-> uniqueAccountNumber");
        System.out.println("    ->(count)-> total");
        Date startDate = format.parse("6/1/2010");
        long numAccountsAfterStart = Stream
            .concat(
                jsonStream.map(r -> 
                    jsonParser.parseInputRecord((String) r)
                ), 
                csvStream.map(r -> 
                    csvParser.parseInputRecord((String) r)
                )
            )
            .map(r -> 
                inputTransformer.transformInput((Map) r)
            )
            .filter(r ->
                !((Date) ((Map) r).get("DATE")).before(startDate)
            )
            .map(r -> (String) ((Map) r).get("CARDNUM"))
            .distinct()
            .count();
        
        
        
        
        System.out.println("Number of cards active on or after June 1 2010 = " 
            + numAccountsAfterStart);
        
    }
    
}
