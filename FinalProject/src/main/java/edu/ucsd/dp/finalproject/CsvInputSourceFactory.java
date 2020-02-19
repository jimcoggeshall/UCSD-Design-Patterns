package edu.ucsd.dp.finalproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * The first line of the CSV file is used as a header. 
 * 
 * @author jcc
 */
public class CsvInputSourceFactory implements InputSourceFactory {
    
    private final String inputFile;
    private final String[] fieldNames;
    private final String delimiter;
        
    public CsvInputSourceFactory(Properties properties) throws FileNotFoundException, IOException {
        delimiter = properties.getProperty("delimiter", ",");
        inputFile = properties.getProperty(
            "inputFile", 
            "src/test/resources/PurchasingCards2010.csv"
        );
        fieldNames = new BufferedReader(new FileReader(inputFile))
            .readLine()
            .split(delimiter);
    }

    /**
     * Does not include the first line of the file, which is used as the header
     * 
     * @return input stream
     */
    @Override
    public Stream createInputStream() {
        Stream s;
        try {
            s = new BufferedReader(new FileReader(inputFile)).lines();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return s.skip(1);
    }

    @Override
    public InputParser createInputParser() {
        return new DelimitedInputParser(fieldNames, delimiter);
    }
    
}
