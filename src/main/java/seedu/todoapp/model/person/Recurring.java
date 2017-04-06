//@@author A0114395E

package seedu.todoapp.model.person;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.todoapp.commons.exceptions.IllegalValueException;

/**
 * Represent whether the task is recurring
 * Guarantees: immutable; is valid as declared in {@link #isValidNotes(String)}
 */

public class Recurring {

    public static final String MESSAGE_RECURRING_CONSTRAINTS =
            "Task recurrence";

    /*
     * The first character of the notes must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String RECURRING_VALIDATION_REGEX = "([^\\s])+";

    /*
     * Supported recurring string types
     */
    private static final Set<String> supportedTypes = new HashSet<String>(Arrays.asList(
            new String[] {"-", "daily", "weekly", "monthly", "yearly"}
       ));

    public final String value;

    /**
     * Validates given Recurring.
     *
     * @throws IllegalValueException if given the recurrencing string is invalid
     */
    public Recurring(String recurring) throws IllegalValueException {
        assert recurring != null;
        if (!isValidRecurring(recurring)) {
            throw new IllegalValueException(MESSAGE_RECURRING_CONSTRAINTS);
        }
        this.value = recurring;
    }

    /**
     * Returns true if a given string is a valid recurring string.
     */
    public static boolean isValidRecurring(String value) {
        return (supportedTypes.contains(value));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurring // instanceof handles nulls
                && this.value.equals(((Recurring) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
