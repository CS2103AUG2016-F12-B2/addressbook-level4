//@@author A0114395E
package seedu.todoapp.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.ObservableList;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.tag.UniqueTagList;

/**
 * Singleton class to handle Recurrent Tasks
 */
public class RecurrentTaskManager {

    private static RecurrentTaskManager instance = null;
    private Model model = null;
    private boolean isRunning = false;
    private Timer timer = new Timer();
    private final long interval = 5 * 1000; // Currently 30secs interval

    private static final String DAILY_INTERVAL = "daily";
    private static final String WEEKLY_INTERVAL = "weekly";
    private static final String MONTHLY_INTERVAL = "monthly";
    private static final String YEARLY_INTERVAL = "yearly";

    /*
    * Supported recurring string types
    */
   private static final Set<String> supportedTypes = new HashSet<String>(Arrays.asList(
           new String[] {DAILY_INTERVAL, WEEKLY_INTERVAL, MONTHLY_INTERVAL, YEARLY_INTERVAL}
           ));


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
        // Get all tasks
        ObservableList<ReadOnlyTask> taskList  = this.model.getToDoApp().getTaskList();
        for (int i = 0; i < taskList.size(); i++) {
            ReadOnlyTask task = taskList.get(i);
            // Check if current task has a recurring tag, and update the timestamp if required
            updateTask(task);
        }
    }

    /*
     * Helper function to check if task is recurring, and update accordingly
     */
    private void updateTask(ReadOnlyTask task) {
        UniqueTagList tagList = task.getTags();
        for (int j = 0 ; j < tagList.asObservableList().size(); j++) {
            if (supportedTypes.contains(tagList.asObservableList().get(j).tagName)) {
                // Recurrent tag detected
                switch (tagList.asObservableList().get(j).tagName) {
                    case DAILY_INTERVAL:
                        System.out.println('d');
                        break;
                    case WEEKLY_INTERVAL:
                        System.out.println('w');
                        break;
                    case MONTHLY_INTERVAL:
                        System.out.println('m');
                        break;
                    case YEARLY_INTERVAL:
                        System.out.println('y');
                        break;
                    default:
                        break;
                }    
            }
        }
    }
}
