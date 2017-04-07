// @@author A0124591H
package seedu.todoapp.logic.commands;

import java.io.IOException;

import seedu.todoapp.commons.core.Config;
import seedu.todoapp.commons.exceptions.DataConversionException;
import seedu.todoapp.commons.util.ConfigUtil;
import seedu.todoapp.storage.XmlToDoAppStorage;

/**
 * Changes the file path of the ToDoApp
 */
public class SpecifyPathCommand extends Command {

    public static final String COMMAND_WORD = "cd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the file path for the ToDoApp."
            + "Parameters: FILE_PATH\n" + "Example: " + COMMAND_WORD + " ~/ToDoApp/ToDoApp.xml";

    public static final String MESSAGE_SUCCESS = "File path successfully changed to: %1$s";
    public static final String MESSAGE_FAIL = "Error changing file path";
    public static final String MESSAGE_IO = "Error with i/o";
    public static final String MESSAGE_DATA_CONVERSION = "Error changing configuration";

    private final String filePath;

    public SpecifyPathCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        XmlToDoAppStorage newFile = new XmlToDoAppStorage(filePath);
        try {
            newFile.saveToDoApp(model.getToDoApp(), filePath);
            model.indicateFilePathChanged(filePath);
            Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
            config.setToDoAppFilePath(filePath);
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        } catch (IOException e) {
            return new CommandResult(MESSAGE_IO);
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_DATA_CONVERSION);
        }
    }
}
