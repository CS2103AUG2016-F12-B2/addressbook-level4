package f12b2.todoapp.logic;

import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.logic.commands.CommandResult;
import f12b2.todoapp.logic.commands.exceptions.CommandException;
import f12b2.todoapp.model.person.ReadOnlyTask;
import javafx.collections.ObservableList;

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
     */
    CommandResult execute(String commandText) throws CommandException, IllegalValueException;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
