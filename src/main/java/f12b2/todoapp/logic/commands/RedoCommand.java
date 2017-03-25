package f12b2.todoapp.logic.commands;

import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.logic.commands.exceptions.CommandException;
import f12b2.todoapp.model.StateManager;

/**
 * Redo most recent command
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redid most recent command";
    public static final String MESSAGE_FAIL = "No command to redo";

    private final StateManager stateManager = StateManager.getInstance();

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        if (stateManager.redoStackHasCommands()) {
            stateManager.redo();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAIL);
        }
    }
}
