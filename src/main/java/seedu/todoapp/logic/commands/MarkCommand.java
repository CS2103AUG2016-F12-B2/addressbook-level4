//@@author A0124591H

package seedu.todoapp.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.todoapp.commons.core.Messages;
import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.commons.util.CollectionUtil;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.person.Completion;
import seedu.todoapp.model.person.Deadline;
import seedu.todoapp.model.person.Name;
import seedu.todoapp.model.person.Notes;
import seedu.todoapp.model.person.Priority;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.person.Start;
import seedu.todoapp.model.person.Task;
import seedu.todoapp.model.person.UniqueTaskList;
import seedu.todoapp.model.tag.UniqueTagList;

/**
 * Marks the details of an existing person in the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the task identified "
            + "by the index number used in the last task listing as completed. "
            + "Marking will set completion to true.\n" + "Parameters: INDEX (must be a positive integer) " + "[NAME]\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    public static final String MESSAGE_NOT_MARKED = "At least one field to mark must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp.";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex
     *            the index of the person in the filtered person list to mark
     * @param markTaskDescriptor
     *            details to mark the person with
     */
    public MarkCommand(int filteredTaskListIndex) {
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

        ReadOnlyTask taskToMark = lastShownList.get(filteredTaskListIndex);
        Task markedTask = createMarkedTask(taskToMark);

        try {
            model.updateTask(filteredTaskListIndex, markedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToMark}
     * marked with {@code markTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createMarkedTask(ReadOnlyTask taskToMark) throws IllegalValueException {
        assert taskToMark != null;

        return new Task(taskToMark.getName(), taskToMark.getStart(), taskToMark.getDeadline(), taskToMark.getPriority(),
                taskToMark.getTags(), taskToMark.getNotes(), new Completion("true"), taskToMark.getVenue());
    }
}
