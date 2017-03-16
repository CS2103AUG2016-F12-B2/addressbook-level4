package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartTest {
    
    @Test
    public void isValidStart() {
        
        //valid start
        assertTrue(Start.isValidStart("3 Mar")); // specific date
        assertTrue(Start.isValidStart("")); // empty string
        assertTrue(Start.isValidStart("tomorrow")); // general word
        assertTrue(Start.isValidStart("3 am")); // specific time 
    }

}
