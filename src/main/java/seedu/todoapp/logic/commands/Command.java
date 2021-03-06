package seedu.todoapp.logic.commands;

import java.text.ParseException;

import seedu.todoapp.commons.core.Messages;
import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.Model;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;

/**
 * Represents a command with hidden internal logic and the ability to be
 * executed.
 */
public abstract class Command {
    protected Model model;

    /**
     * Constructs a feedback message to summarise an operation that displayed a
     * listing of persons.
     *
     * @param displaySize
     *            used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException
     *             If an error occurs during command execution.
     * @throws IllegalValueException
     * @throws ParseException
     * @throws TaskInvalidTimestampsException
     */
    public abstract CommandResult execute()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException;

    /**
     * Provides any needed dependencies to the command. Commands making use of
     * any of these should override this method to gain access to the
     * dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }
}
