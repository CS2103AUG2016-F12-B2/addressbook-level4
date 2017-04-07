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
     * @throws CommandException if it's an invalid command
     * @throws IllegalValueException if any of its value is invalid
     * @throws ParseException if the start/deadline time is not a date
     * @throws TaskInvalidTimestampsException if the deadline is before start
     */
    public void executeCommand()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        this.executeCommand.setData(model);
        this.executeCommand.execute();
    }

    /**
     * Executes the inverse of the command previously entered (for undo)
     * @throws CommandException if it's an invalid command
     * @throws IllegalValueException if any of its value is invalid
     * @throws ParseException if the start/deadline time is not a date
     * @throws TaskInvalidTimestampsException if the deadline is before start
     */
    public void executeInverseCommand()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        this.undoCommand.setData(model);
        this.undoCommand.execute();
    }
}
