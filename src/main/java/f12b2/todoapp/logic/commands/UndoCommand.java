package f12b2.todoapp.logic.commands;

import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.logic.commands.exceptions.CommandException;
import f12b2.todoapp.model.StateManager;

/**
 * Undo most recent command
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undid most recent command";
    public static final String MESSAGE_FAIL = "No command to undo";

    private final StateManager stateManager = StateManager.getInstance();

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        if (stateManager.undoStackHasCommands()) {
            stateManager.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAIL);
        }
    }
}
