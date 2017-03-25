package f12b2.todoapp.model;


import f12b2.todoapp.model.person.ReadOnlyTask;
import f12b2.todoapp.model.tag.Tag;
import javafx.collections.ObservableList;

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
