//@@author A0124591H

package seedu.todoapp.logic.parser;

import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.todoapp.logic.commands.Command;
import seedu.todoapp.logic.commands.IncorrectCommand;
import seedu.todoapp.logic.commands.UnmarkCommand;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class UnmarkCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }

        return new UnmarkCommand(index.get());
    }

}
