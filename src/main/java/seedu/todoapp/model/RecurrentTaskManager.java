//@@author A0114395E
package seedu.todoapp.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Singleton class to handle Recurrent Tasks
 */
public class RecurrentTaskManager {

    private static RecurrentTaskManager instance = null;
    private boolean isRunning = false;
    private Timer timer = new Timer();
    private final long interval = 60 * 1000;

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
            }
        }, 0, this.interval);
    }
}
