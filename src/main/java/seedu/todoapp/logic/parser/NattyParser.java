//@@author A0114395E
package seedu.todoapp.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.joestelmach.natty.DateGroup;

/**
 * Parses loose user input for dates into proper date format
 */
public class NattyParser {
    private static NattyParser instance = null;
    private static final String EMPTY_VALUE = "-";
    private static final int TIMEZONE_IDX = 4;
    // Exists only to defeat instantiation.
    protected NattyParser() {
    }

    // Returns the singleton instance
    public static NattyParser getInstance() {
        if (instance == null) {
            instance = new NattyParser();
        }
        return instance;
    }


    /**
     * Parses, analyses and converts 'rich text' into a timestamp
     *
     * @param String - e.g. 'Tomorrow'
     * @return String - Timestamp
     */
    public String parseNLPDate(String argsString) {
        if (argsString.trim().equals(EMPTY_VALUE)) {
            return EMPTY_VALUE;
        }
        com.joestelmach.natty.Parser nParser = new com.joestelmach.natty.Parser();
        List<DateGroup> groups = nParser.parse(argsString);
        String output = "";
        for (DateGroup group : groups) {
            List<String> dateRepArr = this.getDateFromString(group);
            output = String.join(" ", dateRepArr);
        }
        return output;
    }

    /*
     * Helper function for parsing NLP date
     * @param Takes in a dategroup object parsed from Natty Library
     * @return List<String> excluding timezone
     */
    private List<String> getDateFromString(DateGroup group) {
        List<String> outputArr = new ArrayList<>(Arrays.asList(group.getDates().get(0).toString().split(" ")));
        // Remove time-zone string
        outputArr.remove(TIMEZONE_IDX);
        return outputArr;
    }
}
