//@@author A0114395E
package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.StateManager;

/**
 * Mainly used for undo-ing clear commands
 * Not exposed externally to the user ( no command word )
 */
public class LoadModelCommand extends Command {

    public static final String MESSAGE_SUCCESS = "ToDoApp has been restored!";
    
    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        assert model != null;
        StateManager stateManager = StateManager.getInstance();
        stateManager.restoreData();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
