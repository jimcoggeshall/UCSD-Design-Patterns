package edu.ucsd.dp.homework2;

import static junit.framework.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author jcc
 */
public class TestGlobalCounter {
    
    private int initialCounterValue;
    
    @Before
    public void setUp() {
        initialCounterValue = GlobalCounter.getInstance().getCounterValue();
    }
    
    
    @Test 
    public void testIncrementByNegativeNumber() {
        GlobalCounter gc = GlobalCounter.getInstance();
        assertEquals(gc.getCounterValue(), initialCounterValue);        
        gc.incrementCounter(-10);
        assertEquals(gc.getCounterValue(), initialCounterValue);
    }
    
    @Test
    public void testMultipleReferences() {
        
        System.out.println("Getting a GlobalCounter instance called gcOne");
        GlobalCounter gcOne = GlobalCounter.getInstance();
        System.out.println("Checking that gcOne.getCounterValue() == 0 (modulo initial offset)");
        assertEquals(gcOne.getCounterValue(), initialCounterValue + 0);
        System.out.println("Calling gcOne.incrementCounter(42)");
        gcOne.incrementCounter(42);
        System.out.println("Checking that gcOne.getCounterValue() == 42 (modulo initial offset)");
        assertEquals(gcOne.getCounterValue(), initialCounterValue + 42);
        
        System.out.println("Getting a GlobalCounter instance called gcTwo");
        GlobalCounter gcTwo = GlobalCounter.getInstance();
        System.out.println("Calling gcTwo.incrementCounter(8)");
        gcTwo.incrementCounter(8);
        System.out.println("Checking that gcTwo.getCounterValue() == 50 (modulo initial offset)");
        assertEquals(gcTwo.getCounterValue(), initialCounterValue + 50);
        System.out.println("Checking that gcOne.getCounterValue() == 50 (modulo initial offset)");
        assertEquals(gcOne.getCounterValue(), initialCounterValue + 50);
        
        System.out.println("Throwing away references to gcOne and gcTwo");
        gcOne = null;
        gcTwo = null;
        
        System.out.println("Getting a GlobalCounter instance called gcThree");
        GlobalCounter gcThree = GlobalCounter.getInstance();
        System.out.println("Checking that gcThree.getCounterValue() == 50 (modulo initial offset)");
        assertEquals(gcThree.getCounterValue(), initialCounterValue + 50);
        
        System.out.println("Calling GlobalCounter.getInstance().incrementCounter(10)");
        GlobalCounter.getInstance().incrementCounter(10);
        System.out.println("Checking that gcThree.getCounterValue() == 60 (modulo initial offset)");
        assertEquals(gcThree.getCounterValue(), initialCounterValue + 60);
        
        System.out.println("All tests OK!");
        
    }
    
}
