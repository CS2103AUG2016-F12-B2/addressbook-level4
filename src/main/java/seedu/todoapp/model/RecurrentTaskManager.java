//@@author A0114395E
package seedu.todoapp.model;

import java.util.Stack;

/**
 * Singleton class to handle Recurrent Tasks
 */
public class RecurrentTaskManager {

    private static RecurrentTaskManager instance = null;

    /**
     * Exists only to defeat instantiation.
     */
    protected RecurrentTaskManager() {
    }

    /**
     * @return the singleton instance
     */
    public static RecurrentTaskManager getInstance() {
        if (instance == null) {
            instance = new RecurrentTaskManager();
        }
        return instance;
    }
}
