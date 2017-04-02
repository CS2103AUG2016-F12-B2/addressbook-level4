package seedu.todoapp.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.todoapp.commons.events.model.FilePathChangedEvent;
import seedu.todoapp.commons.events.model.ToDoAppChangedEvent;
import seedu.todoapp.commons.events.storage.DataSavingExceptionEvent;
import seedu.todoapp.commons.exceptions.DataConversionException;
import seedu.todoapp.model.ReadOnlyToDoApp;
import seedu.todoapp.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ToDoAppStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getToDoAppFilePath();

    @Override
    Optional<ReadOnlyToDoApp> readToDoApp() throws DataConversionException, IOException;

    @Override
    void saveToDoApp(ReadOnlyToDoApp toDoApp) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk. Creates
     * the data file if it is missing. Raises {@link DataSavingExceptionEvent}
     * if there was an error during saving.
     */
    void handleToDoAppChangedEvent(ToDoAppChangedEvent tdace);

    // @@author A0124591H
    /**
     * Saves the current version of the Address Book to the hard disk. Creates
     * the data file if it is missing. Raises {@link DataSavingExceptionEvent}
     * if there was an error during saving.
     */
    void handleFilePathChangedEvent(FilePathChangedEvent fpce);
}
