package edu.ucsd.dp.finalproject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;
import org.junit.Test;

/**
 *
 * @author jcc
 */
public class TestCsvInputSourceFactory {

    @Test
    public void testTransformFields() throws IOException  {
        Properties properties = new Properties();
        properties.put("delimiter", ",");
        properties.put("inputFile", "src/test/resources/PurchasingCardsSmall.csv");
        InputSourceFactory factory = new CsvInputSourceFactory(properties);
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
                System.out.println("Raw " + r);
                return inputParser.parseInputRecord((String) r);
            })
            .map(r -> {
                System.out.println("Parsed " + r);
                return inputTransformer.transformInput((Map<String, Object>) r);
            })
            .forEachOrdered(r -> System.out.println("Transformed: " + r));
        
    }

}
