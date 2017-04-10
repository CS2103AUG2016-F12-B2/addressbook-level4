// @@author A0124591H
package seedu.todoapp.logic.commands;

import java.io.File;
import java.io.IOException;
import seedu.todoapp.commons.exceptions.DataConversionException;
import seedu.todoapp.model.ReadOnlyToDoApp;
import seedu.todoapp.model.ToDoApp;
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
		try {
			File file = new File(filePath);
			XmlToDoAppStorage newFile = new XmlToDoAppStorage(filePath);
			ReadOnlyToDoApp newToDoApp;
			if (file.isFile()) {
				newToDoApp = newFile.readToDoApp().get();
			} else {
				newToDoApp = new ToDoApp();
			}
			model.resetData(newToDoApp);
			newFile.saveToDoApp(model.getToDoApp(), filePath);
			model.indicateFilePathChanged(filePath);
			return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
		} catch (DataConversionException e) {
			return new CommandResult(MESSAGE_DATA_CONVERSION);
		} catch (IOException e) {
			return new CommandResult(MESSAGE_IO);
		}
	}
}
