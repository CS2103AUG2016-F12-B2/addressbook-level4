package f12b2.todoapp.logic;

import java.util.logging.Logger;

import f12b2.todoapp.commons.core.ComponentManager;
import f12b2.todoapp.commons.core.LogsCenter;
import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.logic.commands.Command;
import f12b2.todoapp.logic.commands.CommandResult;
import f12b2.todoapp.logic.commands.exceptions.CommandException;
import f12b2.todoapp.logic.parser.Parser;
import f12b2.todoapp.model.Model;
import f12b2.todoapp.model.StateCommandPair;
import f12b2.todoapp.model.StateManager;
import f12b2.todoapp.model.person.ReadOnlyTask;
import f12b2.todoapp.storage.Storage;
import javafx.collections.ObservableList;

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
    public CommandResult execute(String commandText) throws CommandException, IllegalValueException {
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
