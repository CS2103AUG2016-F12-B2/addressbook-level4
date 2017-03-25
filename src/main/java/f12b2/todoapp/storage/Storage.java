package f12b2.todoapp.storage;

import java.io.IOException;
import java.util.Optional;

import f12b2.todoapp.commons.events.model.ToDoAppChangedEvent;
import f12b2.todoapp.commons.events.storage.DataSavingExceptionEvent;
import f12b2.todoapp.commons.exceptions.DataConversionException;
import f12b2.todoapp.model.ReadOnlyToDoApp;
import f12b2.todoapp.model.UserPrefs;

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
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleToDoAppChangedEvent(ToDoAppChangedEvent abce);
}
