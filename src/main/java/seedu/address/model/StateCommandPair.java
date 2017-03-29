//@@author A0114395E
package seedu.address.model;

import java.text.ParseException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Class to store an action, and it's inverse
 */
public class StateCommandPair {
    private Command executeCommand;
    private Command undoCommand;
    private Model model;

    public void setModel(Model model) {
        this.model = model;
    }

    public StateCommandPair(Command cmd, Command inverseCmd) {
        this.executeCommand = cmd;
        this.undoCommand = inverseCmd;
    }

    /**
     * Executes the command previously entered (for redo)
     * @throws CommandException
     * @throws IllegalValueException
     * @throws ParseException 
     */
    public void executeCommand() throws CommandException, IllegalValueException, ParseException {
        this.executeCommand.setData(model);
        this.executeCommand.execute();
    }

    /**
     * Executes the inverse of the command previously entered (for undo)
     * @throws CommandException
     * @throws IllegalValueException
     * @throws ParseException 
     */
    public void executeInverseCommand() throws CommandException, IllegalValueException, ParseException {
        System.out.println("State Pair - executing undo");
        this.undoCommand.setData(model);
        this.undoCommand.execute();
    }
}
