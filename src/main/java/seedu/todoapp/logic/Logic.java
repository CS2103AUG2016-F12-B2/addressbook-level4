package seedu.todoapp.logic;

import java.text.ParseException;

import javafx.collections.ObservableList;
import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.CommandResult;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;

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
     * @throws Exception
     */
    CommandResult execute(String commandText)
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException, Exception;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
