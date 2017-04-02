//@@author A0114395E
package seedu.todoapp.model;

import java.text.ParseException;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.Command;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;

/**
 * Class to store an action, and its inverse
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
     * @throws TaskInvalidTimestampsException
     */
    public void executeCommand()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        this.executeCommand.setData(model);
        this.executeCommand.execute();
    }

    /**
     * Executes the inverse of the command previously entered (for undo)
     * @throws CommandException
     * @throws IllegalValueException
     * @throws ParseException
     * @throws TaskInvalidTimestampsException
     */
    public void executeInverseCommand()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        System.out.println("State Pair - executing undo");
        this.undoCommand.setData(model);
        this.undoCommand.execute();
    }
}
