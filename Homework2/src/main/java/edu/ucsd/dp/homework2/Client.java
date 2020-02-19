package edu.ucsd.dp.homework2;

/**
 * Driver class for demonstrating that {@link GlobalCounter} is indeed a 
 * singleton class.
 * <br><br>
 * This is done by creating multiple references to {@link GlobalCounter} objects, 
 * performing operations using the various references, and demonstrating that 
 * each operation affects all references. Thus each reference points to the
 * same underlying object. 
 * 
 * @author jcc
 */
public class Client {

    /**
     * Main entry point for demonstrating {@link GlobalCounter} behavior.
     * 
     * @param args None.
     */
    public static void main(String[] args) {
        
        System.out.println("Getting a GlobalCounter instance called gcOne");
        GlobalCounter gcOne = GlobalCounter.getInstance();
        System.out.println("gcOne.getCounterValue() = " +
            gcOne.getCounterValue() + " - should be 0");
        System.out.println("Calling gcOne.incrementCounter(42)");
        gcOne.incrementCounter(42);
        System.out.println("gcOne.getCounterValue() = " + 
            gcOne.getCounterValue() + " - should be 42");
        
        System.out.println("Getting a GlobalCounter instance called gcTwo");
        GlobalCounter gcTwo = GlobalCounter.getInstance();
        System.out.println("Calling gcTwo.incrementCounter(8)");
        gcTwo.incrementCounter(8);
        System.out.println("gcTwo.getCounterValue() = " + 
            gcTwo.getCounterValue() + " - should be 50");
        System.out.println("gcOne.getCounterValue() = " + 
            gcOne.getCounterValue() + " - should be 50");
        
        System.out.println("Throwing away references to gcOne and gcTwo");
        gcOne = null;
        gcTwo = null;
        
        System.out.println("Getting a GlobalCounter instance called gcThree");
        GlobalCounter gcThree = GlobalCounter.getInstance();
        System.out.println("gcThree.getCounterValue() = " + 
            gcThree.getCounterValue() + " - should be 50");
        
        System.out.println(
            "Calling GlobalCounter.getInstance().incrementCounter(10)"
        );
        GlobalCounter.getInstance().incrementCounter(10);
        System.out.println("gcThree.getCounterValue() = " + 
            gcThree.getCounterValue() + " - should be 60");
        
        System.out.println("Finished");
        
    }
    
}
