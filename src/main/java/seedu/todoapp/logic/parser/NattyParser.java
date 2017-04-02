//@@author A0114395E
package seedu.todoapp.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.joestelmach.natty.DateGroup;

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
            // Remove timezone string
            List<String> outputArr = new ArrayList<>(Arrays.asList(group.getDates().get(0).toString().split(" ")));
            outputArr.remove(TIMEZONE_IDX);
            output = String.join(" ", outputArr);
        }
        return output;
    }
}
