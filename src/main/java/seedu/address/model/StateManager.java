//@@author A0114395E
package seedu.address.model;

import java.util.Stack;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Singleton class to handle Undo/Redo commands
 */
public class StateManager {

    private static StateManager instance = null;
    private Model model = null;
    private Stack<StateCommandPair> undoStack;
    private Stack<StateCommandPair> redoStack;
    private Stack<ReadOnlyToDoApp> previousDataStack;

    // Exists only to defeat instantiation.
    protected StateManager() {
        undoStack = new Stack<StateCommandPair>();
        redoStack = new Stack<StateCommandPair>();
        previousDataStack = new Stack<ReadOnlyToDoApp>();
    }

    // Returns the singleton instance
    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    // Updates the Model in StateManager
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Check if stack exist for redo
     */
    public boolean redoStackHasCommands() {
        return !redoStack.isEmpty();
    }

    /**
     * Check if stack exist for undo
     */
    public boolean undoStackHasCommands() {
        return !undoStack.isEmpty();
    }

    /**
     * Check if stack exist for models
     */
    public boolean previousDataStackHasCommands() {
        return !previousDataStack.isEmpty();
    }

    /**
     * On each new command, add a new command onto the undo stack to track its
     * history and clear the redo history stack
     */
    public void onNewCommand(StateCommandPair newCommandPair) {
        this.undoStack.push(newCommandPair);
        this.redoStack.clear();
    }

    /**
     * Undo the most recent command, then store that undo command in a redo
     * stack
     * @throws CommandException
     * @throws IllegalValueException
     */
    public void undo() throws CommandException, IllegalValueException {
        if (undoStack.isEmpty()) {
            // Can't undo as no history
            System.out.println("No undo commands found");
        } else {
            // Moving command from undo to redo
            StateCommandPair currentCommand = undoStack.pop();
            redoStack.push(currentCommand);
            // Executing undo command
            currentCommand.executeInverseCommand();
        }
    }

    /**
     * Redo the most recently 'undo' command, then store that redo command in
     * the undo stack
     * @throws CommandException
     * @throws IllegalValueException
     */
    public void redo() throws CommandException, IllegalValueException {
        if (redoStack.isEmpty()) {
            // Can't redo as no history
            System.out.println("No redo commands found");
        } else {
            // Moving command from redo to undo
            StateCommandPair currentCommand = redoStack.pop();
            undoStack.push(currentCommand);
            // Executing redo command
            currentCommand.executeCommand();
        }
    }  

    /**
     * Restores previous data (i.e undo a clear command )
     * 
     * @throws CommandException
     * @throws IllegalValueException
     */
    public void restoreData() throws CommandException, IllegalValueException {
        if (previousDataStack.isEmpty()) {
            // Can't undo as no history
            System.out.println("No previous data found");
        } else {
            // Moving command from undo to redo
            ReadOnlyToDoApp previousData = previousDataStack.pop();
            this.model.resetData(previousData);
        }
    }
}
