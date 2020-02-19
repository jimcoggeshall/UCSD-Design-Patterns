package edu.ucsd.dp.finalproject;

import java.util.HashMap;
import java.util.Map;

/**
 * "Separate the construction of a complex object from its representation so 
 * that the same construction process can create different representations."
 * @author jcc
 */
public class InputTransformerBuilder {

    private Map<String, FieldTransformer> transformerMap;

    public InputTransformerBuilder() {
        transformerMap = new HashMap<>();
    }

    public InputTransformerBuilder addFieldTransformer(String field, FieldTransformer trf) {
        transformerMap.put(field, trf);
        return InputTransformerBuilder.this;
    }

    public InputTransformer build() {
        return new InputTransformer(transformerMap);
    }

}
