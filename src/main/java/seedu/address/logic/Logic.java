package seedu.address.logic;

import java.text.ParseException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniqueTaskList.TaskInvalidTimestampsException;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText
     *            The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException
     *             If an error occurs during command execution.
     * @throws IllegalValueException
     * @throws ParseException
     * @throws TaskInvalidTimestampsException
     */
    CommandResult execute(String commandText)
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
