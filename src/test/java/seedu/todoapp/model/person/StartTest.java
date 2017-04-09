//@@author A0124153U

package seedu.todoapp.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StartTest {

    @Test
    public void isValidStart() {
        // blank start
    	assertFalse(Start.isValidStart("")); // empty string
    	assertFalse(Start.isValidStart(" ")); // empty string
        // valid start
        assertTrue(Start.isValidStart("3 Mar")); // specific date
        assertTrue(Start.isValidStart("4 pm")); // specific time
        assertTrue(Start.isValidStart("tomorrow")); // general word
    }
}
