package edu.ucsd.dp.homework2;

/**
 * A simple Singleton class that maintains an internal state in the form of a 
 * counter. The counter value may be increased but never decreased, regardless 
 * of the number of references to the instance of this class. 
 * 
 * @author jcc
 */
public class GlobalCounter {

    private int counter;
    
    private GlobalCounter() {
        this.counter = 0;
    }

    /**
     * Point of access for the single instance of this class.
     * @return GlobalCounter
     */
    public static GlobalCounter getInstance() {
        return GlobalCounterHolder.INSTANCE;
    }
    
    /**
     * Increments the global counter managed by this class. 
     * <br><br>
     * The counter value may not decrease; thus attempts to increment by a 
     * negative number will have no effect.
     * 
     * @param i The positive amount by which to increment the counter. 
     */
    public void incrementCounter(int i) {
        if (i < 0) {
            return;
        }
        this.counter += i;
    }
    
    /**
     * Get the value of the global counter managed by this class.
     * @return The counter value.
     */
    public int getCounterValue() {
        return this.counter;
    }

    private static class GlobalCounterHolder {
        private static final GlobalCounter INSTANCE = new GlobalCounter();
    }
}
