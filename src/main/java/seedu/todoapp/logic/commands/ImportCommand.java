//@@author A0124591H
package seedu.todoapp.logic.commands;

import java.io.IOException;

import seedu.todoapp.commons.exceptions.DataConversionException;
import seedu.todoapp.storage.XmlToDoAppStorage;

/**
 * Imports a ToDoApp xml file and replaces current data
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Imports from the provided file path for the ToDoApp."
            + "Parameters: FILE_PATH\n" + "Example: " + COMMAND_WORD + " ~/ToDoApp/ToDoApp.xml";

    public static final String MESSAGE_SUCCESS = "File successfully imported from: %1$s";
    public static final String MESSAGE_INVALID_FILE = "File path provided is not valid!";
    public static final String MESSAGE_DATA_CONVERSION = "Error changing configuration";

    private final String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            XmlToDoAppStorage newFile = new XmlToDoAppStorage(filePath);
            model.resetData(newFile.readToDoApp().orElseThrow(() -> new IOException(MESSAGE_INVALID_FILE)));
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        } catch (IOException e) {
            return new CommandResult(MESSAGE_INVALID_FILE);
        } catch (DataConversionException e) {
            return new CommandResult(MESSAGE_DATA_CONVERSION);
        }
    }
}
