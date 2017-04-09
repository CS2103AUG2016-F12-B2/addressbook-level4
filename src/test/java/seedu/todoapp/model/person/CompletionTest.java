//@@author A0124153U

package seedu.todoapp.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompletionTest {

    @Test
    public void isValidCompletion() {
        // invalid notes
        assertFalse(Completion.isValidCompletion("")); // empty string
        assertFalse(Completion.isValidCompletion(" ")); // empty string
        assertFalse(Completion.isValidCompletion("! ")); // empty string
        // valid notes
        assertTrue(Completion.isValidCompletion("true")); // true value
        assertTrue(Completion.isValidCompletion("false")); // false value
        assertTrue(Completion.isValidCompletion("f")); // false value
        assertTrue(Completion.isValidCompletion("t")); // false value
    }
}
