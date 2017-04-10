//@@author A0124591H

package seedu.todoapp.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.todoapp.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the ToDoApp Guarantees: immutable; is valid
 * as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS = "Task's deadline should not start with a whitespace.";

    /*
     * The first character of the start must not be a whitespace, otherwise " "
     * (a blank string) becomes a valid input.
     */
    public static final String DEADLINE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given start.
     *
     * @throws IllegalValueException
     *             if given start string is invalid.
     */
    public Deadline(String deadline) throws IllegalValueException {
        assert deadline != null;
        if (!isValidDeadline(deadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.value = deadline;
    }

    public static boolean isValidDeadline(String test) {
        return test.matches(DEADLINE_VALIDATION_REGEX);
    }

    // @@author A0114395E
    /**
     * Get the date object of Deadline
     * @throws ParseException
     * @returns the date object
     */
    public Date getDate() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss yyyy");
        Date date = df.parse(this.value);
        return date;
    }

    /**
     * Check if Deadline has a date value
     * @returns boolean
     */
    public boolean hasDate() {
        return !(this.value.equals("-") || this.value.length() == 0);
    }
    // @@author

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                        && this.value.equals(((Deadline) other).value)); // state
                                                                         // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
