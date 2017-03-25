//@@author A0114395E
package f12b2.todoapp.model;

import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.logic.commands.Command;
import f12b2.todoapp.logic.commands.exceptions.CommandException;

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
     */
    public void executeCommand() throws CommandException, IllegalValueException {
        this.executeCommand.setData(model);
        this.executeCommand.execute();
    }

    /**
     * Executes the inverse of the command previously entered (for undo)
     * @throws CommandException
     * @throws IllegalValueException
     */
    public void executeInverseCommand() throws CommandException, IllegalValueException {
        System.out.println("State Pair - executing undo");
        this.undoCommand.setData(model);
        this.undoCommand.execute();
    }
}
