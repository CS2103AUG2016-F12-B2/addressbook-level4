//@@author A0124153U

package seedu.todoapp.logic.parser;

import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_COMPLETION;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_START;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.NoSuchElementException;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.Command;
import seedu.todoapp.logic.commands.FindCommand;
import seedu.todoapp.logic.commands.IncorrectCommand;
import seedu.todoapp.logic.commands.SortCommand;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {
    
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     */
    public Command parse(String args) {
        final String keyword = args.trim();
        if(!isValidSorting(keyword)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(keyword);
    }
    
    private static boolean isValidSorting(String input) {
        return input.equals("priority");
    }

}
