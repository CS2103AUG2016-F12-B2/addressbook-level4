//@@author A0114395E
package seedu.todoapp.model;

import java.text.ParseException;
import java.util.Stack;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;

/**
 * Singleton class to handle Undo/Redo commands
 */
public class StateManager {

    private static StateManager instance = null;
    private Model model = null;
    private Stack<StateCommandPair> undoStack;
    private Stack<StateCommandPair> redoStack;
    private Stack<ReadOnlyToDoApp> previousDataStack;

    /**
     * Exists only to defeat instantiation.
     */
    protected StateManager() {
        undoStack = new Stack<StateCommandPair>();
        redoStack = new Stack<StateCommandPair>();
        previousDataStack = new Stack<ReadOnlyToDoApp>();
    }

    /**
     * @return the singleton instance
     */
    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    /*
     * Updates the Model in StateManager
     */
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
     * On each clear command, we store the current model,
     * in case the user wants to undo that clear command
     */
    public void onClearCommand(ReadOnlyToDoApp data) {
        ToDoApp currentData = new ToDoApp(data);
        this.previousDataStack.push(currentData);
    }

    /**
     * Undo the most recent command, then store that undo command in a redo
     * stack
     * @throws CommandException
     * @throws IllegalValueException
     * @throws ParseException
     * @throws TaskInvalidTimestampsException
     */
    public void undo()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        assert(!undoStack.isEmpty());
        // Moving command from undo to redo
        StateCommandPair currentCommand = undoStack.pop();
        redoStack.push(currentCommand);
        // Executing undo command
        currentCommand.executeInverseCommand();
    }

    /**
     * Redo the most recently 'undo' command, then store that redo command in
     * the undo stack
     * @throws CommandException
     * @throws IllegalValueException
     * @throws ParseException
     * @throws TaskInvalidTimestampsException
     */
    public void redo()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        assert(!redoStack.isEmpty());
        // Moving command from redo to undo
        StateCommandPair currentCommand = redoStack.pop();
        undoStack.push(currentCommand);
        // Executing redo command
        currentCommand.executeCommand();
    }

    /**
     * Restores previous data (i.e undo a clear command )
     *
     * @throws CommandException
     * @throws IllegalValueException
     */
    public void restoreData() throws CommandException, IllegalValueException {
        assert(!previousDataStack.isEmpty());
        System.out.println("No previous data found");
        ReadOnlyToDoApp previousData = previousDataStack.pop();
        this.model.resetData(previousData);
    }
}
