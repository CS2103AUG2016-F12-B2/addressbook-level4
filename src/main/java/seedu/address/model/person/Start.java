//@@author A0124153U


package seedu.address.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start in the ToDoApp
 * must contain a date, starting time is optional
 * Guarantees: immutable; is valid as declared in {@link #isValidStart(String)}
 */
public class Start {

    public static final String MESSAGE_START_CONSTRAINTS =
            "Task start";

    /*
     * The first character of the start must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String START_VALIDATION_REGEX = "[A-Za-z0-9 ]+";

    public final String value;

    /**
     * Validates given start.
     *
     * @throws IllegalValueException if given start string is invalid.
     */
    public Start(String start) throws IllegalValueException {
        assert start != null;
        if (!isValidStart(start) && start.length() > 0) {
            throw new IllegalValueException(MESSAGE_START_CONSTRAINTS);
        }
        this.value = start;
    }

    /**
     * Returns true if a given string is a valid start.
     */
    public static boolean isValidStart(String test) {
        return test.matches(START_VALIDATION_REGEX);
    }

    //@@author A0114395E
    /**
     * Get the date object of Start
     * @throws ParseException
     * @returns the date object
     */
    public Date getDate() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss yyyy");
        Date date = df.parse(this.value);
        return date;
    }

    /**
    * Check if Start has a date value
    * @returns boolean
    */
    public boolean hasDate() {
        return !(this.value.equals("-") || this.value.length() == 0);
    }
    //@@author

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Start // instanceof handles nulls
                && this.value.equals(((Start) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
