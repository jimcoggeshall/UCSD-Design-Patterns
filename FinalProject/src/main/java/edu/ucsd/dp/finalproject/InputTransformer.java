package edu.ucsd.dp.finalproject;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author jcc
 */
public class InputTransformer {
    
    private Map<String, FieldTransformer> transformerMap;
    private FieldTransformer defaultTransformer = new FieldTransformer<String, String>();
    
    public InputTransformer(Map<String, FieldTransformer> transformerMap) {
        this.transformerMap = transformerMap;
    }

    private Entry<String, Object> transformElement(Entry<String, Object> e) {
        e.setValue(
            transformerMap.getOrDefault(
                e.getKey(), defaultTransformer
            ).execute(
                e.getValue()
            )
        );
        return e;
    }
    
    public Map<String, Object> transformInput(Map<String, Object> input) {
        return input.entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    e -> e.getKey(), 
                    e -> transformElement(e).getValue()
                )
            );
    }
    
}
