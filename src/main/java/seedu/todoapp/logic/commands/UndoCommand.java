package seedu.todoapp.logic.commands;

import java.text.ParseException;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.StateManager;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;

/**
 * Undo most recent command
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undid most recent command";
    public static final String MESSAGE_FAIL = "No command to undo";

    private final StateManager stateManager = StateManager.getInstance();

    @Override
    public CommandResult execute()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        if (stateManager.undoStackHasCommands()) {
            stateManager.undo();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_FAIL);
        }
    }
}
