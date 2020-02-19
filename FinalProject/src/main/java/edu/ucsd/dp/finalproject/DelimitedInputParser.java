package edu.ucsd.dp.finalproject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jcc
 */
public class DelimitedInputParser implements InputParser {
    
    private final String[] fieldNames;
    private final String delimiter;
    
    public DelimitedInputParser(String[] fieldNames, String delimiter) {
        this.fieldNames = fieldNames;
        this.delimiter = delimiter;
    }

    @Override
    public Map parseInputRecord(String rawInput) {
        Map<String, Object> parsed = new HashMap<>();
        String[] splitInput = rawInput.split(delimiter);
        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];
            parsed.put(fieldName, splitInput[i]);
        }
        return parsed;
    }
    
}
