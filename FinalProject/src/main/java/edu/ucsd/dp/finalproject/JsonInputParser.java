package edu.ucsd.dp.finalproject;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jcc
 */
public class JsonInputParser implements InputParser {
    
    private static final Gson gson = new Gson();
    
    public JsonInputParser() {}

    @Override
    public Map parseInputRecord(String rawInput) {
        Map<String, Object> parsed = gson.fromJson(rawInput, HashMap.class);
        return parsed;
    }
    
}
