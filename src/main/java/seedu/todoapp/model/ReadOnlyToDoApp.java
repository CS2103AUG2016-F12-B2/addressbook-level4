package seedu.todoapp.model;


import javafx.collections.ObservableList;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.tag.Tag;

/**
 * Unmodifiable view of a ToDoApp
 */
public interface ReadOnlyToDoApp {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
