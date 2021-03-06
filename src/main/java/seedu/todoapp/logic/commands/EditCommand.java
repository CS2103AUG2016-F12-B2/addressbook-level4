//@@author A0124591H

package seedu.todoapp.logic.commands;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import seedu.todoapp.commons.core.Messages;
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
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;
import seedu.todoapp.model.person.Venue;
import seedu.todoapp.model.tag.UniqueTagList;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NAME] [p/PRIORITY] [s/START TIMESTAMP] [d/DEADLINE] [n/NOTES] [t/TAG]...\n" + "Example: " + COMMAND_WORD
            + " 1 p/3 d/tomorrow t/Important";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp.";
    public static final String MESSAGE_INVALID_START_END = "The task deadline cannot be before the start time";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex
     *            the index of the person in the filtered person list to edit
     * @param editTaskDescriptor
     *            details to edit the person with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    //@@author A0114395E
    @Override
    public CommandResult execute() throws CommandException, ParseException, TaskInvalidTimestampsException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            // Ensure that Deadline is not before Start
            if (editedTask.getStart().hasDate() && editedTask.getDeadline().hasDate() &&
                    editedTask.getStart().getDate().after(editedTask.getDeadline().getDate())) {
                throw new UniqueTaskList.TaskInvalidTimestampsException();
            }
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (UniqueTaskList.TaskInvalidTimestampsException e) {
            throw new CommandException(MESSAGE_INVALID_START_END);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Start updatedStart = editTaskDescriptor.getStart().orElseGet(taskToEdit::getStart);
        Deadline updatedDeadline = editTaskDescriptor.getDeadline().orElseGet(taskToEdit::getDeadline);
        Priority updatedPriority = editTaskDescriptor.getPriority().orElseGet(taskToEdit::getPriority);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);
        Notes updatedNotes = editTaskDescriptor.getNotes().orElseGet(taskToEdit::getNotes);
        Completion updatedCompletion = editTaskDescriptor.getCompletion().orElseGet(taskToEdit::getCompletion);
        Venue updatedVenue = editTaskDescriptor.getVenue().orElseGet(taskToEdit::getVenue);

        return new Task(updatedName, updatedStart, updatedDeadline, updatedPriority, updatedTags, updatedNotes,
                updatedCompletion, updatedVenue);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value
     * will replace the corresponding field value of the person.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Start> start = Optional.empty();
        private Optional<Deadline> deadline = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();
        private Optional<Notes> notes = Optional.empty();
        private Optional<Completion> completion = Optional.empty();
        private Optional<Venue> venue = Optional.empty();

        public EditTaskDescriptor() {
        }

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.start = toCopy.getStart();
            this.deadline = toCopy.getDeadline();
            this.priority = toCopy.getPriority();
            this.tags = toCopy.getTags();
            this.notes = toCopy.getNotes();
            this.completion = toCopy.getCompletion();
            this.venue = toCopy.getVenue();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.start,
                    this.deadline, this.priority, this.notes, this.tags,
                    this.venue);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setStart(Optional<Start> start) {
            assert start != null;
            this.start = start;
        }

        public Optional<Start> getStart() {
            return start;
        }

        public void setDeadline(Optional<Deadline> deadline) {
            assert deadline != null;
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return deadline;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }

        public void setNotes(Optional<Notes> notes) {
            assert notes != null;
            this.notes = notes;
        }

        public Optional<Notes> getNotes() {
            return notes;
        }

        public void setCompletion(Optional<Completion> completion) {
            assert completion != null;
            this.completion = completion;
        }

        public Optional<Completion> getCompletion() {
            return completion;
        }

        public void setVenue(Optional<Venue> venue) {
            assert venue != null;
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return venue;
        }
    }
}
