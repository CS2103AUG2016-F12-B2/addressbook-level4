//@@author A0124153U

package seedu.todoapp.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompletionTest {

    @Test
    public void isValidCompletion() {

        //valid notes
        assertTrue(Completion.isValidCompletion("")); // empty string
        assertTrue(Completion.isValidCompletion("true")); // true value
        assertTrue(Completion.isValidCompletion("false")); // false value
    }
}
