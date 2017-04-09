//@@author A0124153U

package seedu.todoapp.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VenueTest {

    @Test
    public void isValidVenue() {

        // invalid notes
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // space
        // valid notes
        assertTrue(Venue.isValidVenue("nus")); // string
        assertTrue(Venue.isValidVenue("science canteen")); // string with space
        assertTrue(Venue.isValidVenue("com2")); // string with space and integer
        assertTrue(Venue.isValidVenue("Singapore #123-567")); // string with
                                                              // space and
                                                              // integer
    }
}
