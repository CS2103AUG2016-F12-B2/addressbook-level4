//@@author A0124153U

package seedu.todoapp.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VenueTest {

    @Test
    public void isValidVenue() {

        //valid notes
        assertTrue(Venue.isValidVenue("")); // empty string
        assertTrue(Venue.isValidVenue(" ")); // space
        assertTrue(Venue.isValidVenue("nus")); // string
        assertTrue(Venue.isValidVenue("science canteen")); // string with space
        assertTrue(Venue.isValidVenue("com2"));  // string with space and integer
    }
}
