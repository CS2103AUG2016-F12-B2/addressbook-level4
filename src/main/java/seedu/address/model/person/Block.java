// @@author A0124591H

package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's Block in the ToDoApp must contain a start time and end
 * time Guarantees: immutable; is valid as declared in
 * {@link #isValidStart(String)}
 */
public class Block {

    public static final String MESSAGE_START_CONSTRAINTS = "Task blocked";

    /*
     * The first character of the start must not be a whitespace, otherwise " "
     * (a blank string) becomes a valid input.
     */
    public static final String DEADLINE_VALIDATION_REGEX = "([^\\s])+";
    public static final String STRING_CONCATENATOR = " to ";

    public final String[] blockPeriod;
    public final String startValue;
    public final String endValue;

    /**
     * Validates given start.
     * @throws IllegalValueException if given start string is invalid.
     */
    public Block(String blockPeriod) throws IllegalValueException {
        assert blockPeriod != null;
        if (!isValidBlock(blockPeriod)) {
            throw new IllegalValueException(MESSAGE_START_CONSTRAINTS);
        }
        this.blockPeriod = blockPeriod.split(" to ");
        this.startValue = this.blockPeriod[0];
        this.endValue = this.blockPeriod[1];
    }

    /**
     * Returns true if a given string is a valid start.
     */
    public static boolean isValidBlock(String test) {
        return true;
    }

    @Override
    public String toString() {
        return startValue + STRING_CONCATENATOR + endValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Block // instanceof handles nulls
                        && this.startValue.equals(((Block) other).startValue)
                        && this.endValue.equals(((Block) other).endValue)); // state
                                                                                // check
    }

    @Override
    public int hashCode() {
        return (startValue + STRING_CONCATENATOR + endValue).hashCode();
    }

}
