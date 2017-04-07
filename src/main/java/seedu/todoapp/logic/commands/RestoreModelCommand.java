//@@author A0114395E
package seedu.todoapp.logic.commands;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.StateManager;

/**
 * Mainly used for undo-ing clear commands
 * Not exposed externally to the user ( no command word )
 */
public class RestoreModelCommand extends Command {

    public static final String MESSAGE_SUCCESS = "ToDoApp has been restored!";

    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        assert model != null;
        StateManager stateManager = StateManager.getInstance();
        stateManager.restoreData();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
