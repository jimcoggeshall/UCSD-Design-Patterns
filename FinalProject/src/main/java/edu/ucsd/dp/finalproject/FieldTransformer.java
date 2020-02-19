package edu.ucsd.dp.finalproject;

/**
 *
 * "Encapsulate a request as an object, thereby letting you parameterize clients 
 * with different requests, queue or log requests, and support undoable 
 * operations."
 * 
 * @author jcc
 * @param <R>
 * @param <T>
 */
public class FieldTransformer<R, T> {
        
    public T execute(R rawValue) {
        return (T) rawValue;
    }
    
}
