//@@author A0114395E
package seedu.todoapp.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Singleton class to handle Recurrent Tasks
 */
public class RecurrentTaskManager {

    private static RecurrentTaskManager instance = null;
    private Model model = null;
    private boolean isRunning = false;
    private Timer timer = new Timer();
    private final long interval = 30 * 1000; // Currently 30secs interval

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

    /*
     * Updates the Model in RecurrentTaskManager
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /*
     * Start Running to update all timestamps
     */
    public void startRunning() {
        // Don't do anything if we are already updating timestamps
        if (this.isRunning) return;

        // Set manager as running
        this.isRunning = true;

        // Run interval to update recurrent tasks start/deadline
        this.timer.schedule(new TimerTask() {
            public void run() {
                // TODO: Update tasks
                updateAllRecurringTimestamps();
            }
        }, 0, this.interval);
    }

    /*
     * Helper function to update all recurring tasks' timestamps for start/deadline
     */
    private void updateAllRecurringTimestamps() {
        System.out.println("Recur");
    }
}
