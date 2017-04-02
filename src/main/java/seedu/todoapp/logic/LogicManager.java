package seedu.todoapp.logic;

import java.text.ParseException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.todoapp.commons.core.ComponentManager;
import seedu.todoapp.commons.core.LogsCenter;
import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.Command;
import seedu.todoapp.logic.commands.CommandResult;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.logic.parser.Parser;
import seedu.todoapp.model.Model;
import seedu.todoapp.model.StateCommandPair;
import seedu.todoapp.model.StateManager;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;
import seedu.todoapp.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final StateManager stateManager = StateManager.getInstance();

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = Parser.getInstance();
    }

    @Override
    public CommandResult execute(String commandText)
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        // Evaluate inverse of command
        Command inverseCommand = parser.parseInverseCommand(commandText, model);
        // Check if inverse of command exist
        if (inverseCommand != null) {
            // Store the command
            StateCommandPair stateCommandPair = new StateCommandPair(command, inverseCommand);
            stateCommandPair.setModel(model);
            stateManager.onNewCommand(stateCommandPair);
        }
        // Execute the command
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
