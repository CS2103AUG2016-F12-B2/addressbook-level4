//@@author A0124591H


package seedu.todoapp.logic.commands;

import java.util.List;

import seedu.todoapp.commons.core.Messages;
import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.person.Completion;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.person.Task;
import seedu.todoapp.model.person.UniqueTaskList;

/**
 * Unmarks the details of an existing task in the ToDoApp.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unmarks the task identified "
            + "by the index number used in the last task listing as not completed. "
            + "Unmarking will set completion to false.\n" + "Parameters: INDEX (must be a positive integer) "
            + "[NAME]\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked Task: %1$s";
    public static final String MESSAGE_NOT_UNMARKED = "At least one field to mark must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp.";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered
     *            task list to mark
     * @param markTaskDescriptor details to mark the task with
     */
    public UnmarkCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUnmark = lastShownList.get(filteredTaskListIndex);
        Task markedTask = createUnmarkedTask(taskToUnmark);

        try {
            model.updateTask(filteredTaskListIndex, markedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToUnmark));
    }

    /**
     * Creates and returns a {@code Task} with the details of
     * {@code taskToUnmark} marked with {@code markTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createUnmarkedTask(ReadOnlyTask taskToUnmark) throws IllegalValueException {
        assert taskToUnmark != null;

        return new Task(taskToUnmark.getName(), taskToUnmark.getStart(), taskToUnmark.getDeadline(),
                taskToUnmark.getPriority(), taskToUnmark.getTags(), taskToUnmark.getNotes(),
                new Completion("false"), taskToUnmark.getVenue());
    }
}
