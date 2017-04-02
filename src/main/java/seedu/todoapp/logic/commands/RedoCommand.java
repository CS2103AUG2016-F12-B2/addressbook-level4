package seedu.todoapp.logic.commands;

import java.text.ParseException;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.StateManager;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;

/**
 * Redo most recent command
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redid most recent command";
    public static final String MESSAGE_FAIL = "No command to redo";

    private final StateManager stateManager = StateManager.getInstance();

    @Override
    public CommandResult execute()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        if (stateManager.redoStackHasCommands()) {
            stateManager.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAIL);
        }
    }
}
