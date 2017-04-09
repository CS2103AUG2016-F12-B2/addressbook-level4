//@@author A0124153U

package seedu.todoapp.model.person;

import seedu.todoapp.commons.exceptions.IllegalValueException;

/**
 * Represent the venue of the task(if exist) as a string Guarantees: immutable;
 * is valid as declared in {@link #isValidNotes(String)}
 */

public class Venue {

    public static final String MESSAGE_VENUE_CONSTRAINTS = "Task venue";

    /*
     * The first character of the notes must not be a whitespace, otherwise " "
     * (a blank string) becomes a valid input.
     */
    public static final String VENUE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given Venue.
     *
     * @throws IllegalValueException
     *             if given notes string is invalid.
     */
    public Venue(String venue) throws IllegalValueException {
        assert venue != null;
        if (!isValidVenue(venue)) {
            throw new IllegalValueException(MESSAGE_VENUE_CONSTRAINTS);
        }
        this.value = venue;
    }

    /**
     * Returns true if a given string is a valid venue.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VENUE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                        && this.value.equals(((Venue) other).value)); // state
                                                                      // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
