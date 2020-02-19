package edu.ucsd.dp.finalproject;

import java.util.stream.Stream;

/**
 * "Provide an interface for creating families of related or dependent objects 
 * without specifying their concrete classes."
 * 
 * Here the input stream and the input parser must in some cases be created 
 * consistently with respect to each other. 
 * 
 * @author jcc
 */
public interface InputSourceFactory {
    
    public Stream createInputStream();
    
    public InputParser createInputParser();
    
}
