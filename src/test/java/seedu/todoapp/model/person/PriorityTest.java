//@@author A0124153U

package seedu.todoapp.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PriorityTest {

    @Test
    public void isValidPriority() {
        // valid priority
        assertTrue(Priority.isValidPriority(0)); // zero
        assertTrue(Priority.isValidPriority(10)); // integer

    }
}
