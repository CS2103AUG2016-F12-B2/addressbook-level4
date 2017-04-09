//@@author A0124153U

package seedu.todoapp.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDeadline() {
        // blank deadline
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // empty string
        // valid deadline
        assertTrue(Deadline.isValidDeadline("17 Mar"));
        assertTrue(Deadline.isValidDeadline("3 am"));
        assertTrue(Deadline.isValidDeadline("tomorrow"));

    }
}
