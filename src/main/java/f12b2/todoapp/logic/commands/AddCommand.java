package f12b2.todoapp.logic.commands;

import java.util.HashSet;
import java.util.Set;

import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.logic.commands.exceptions.CommandException;
import f12b2.todoapp.model.person.Completion;
import f12b2.todoapp.model.person.Deadline;
import f12b2.todoapp.model.person.Name;
import f12b2.todoapp.model.person.Notes;
import f12b2.todoapp.model.person.Priority;
import f12b2.todoapp.model.person.Start;
import f12b2.todoapp.model.person.Task;
import f12b2.todoapp.model.person.UniqueTaskList;
import f12b2.todoapp.model.tag.Tag;
import f12b2.todoapp.model.tag.UniqueTagList;

/**
 * Adds a task to the ToDoApp.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the ToDoApp. "
            + "Parameters: NAME [d/DEADLINE] [p/PRIORITY] [t/TAG] [n/NOTES]...\n"
            + "Example: " + COMMAND_WORD
            + " Buy Printer";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the ToDoApp";

    private final Task toAdd;
    private final int idx; // Optional adding of index

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String start, String deadline,
                        Integer priority, Set<String> tags, String notes, String completion, int idx)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Start(start),
                new Deadline(deadline),
                new Priority(priority),
                new UniqueTagList(tagSet),
                new Notes(notes),
                new Completion(completion)
        );
        this.idx = idx;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            if (this.idx >= 0) {
                model.addTask(toAdd, idx);
            } else {
                model.addTask(toAdd);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
