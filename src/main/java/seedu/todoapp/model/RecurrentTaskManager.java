//@@author A0114395E
package seedu.todoapp.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.model.person.Deadline;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.person.Start;
import seedu.todoapp.model.person.Task;
import seedu.todoapp.model.tag.UniqueTagList;

/**
 * Singleton class to handle Recurrent Tasks
 */
public class RecurrentTaskManager {

    private static RecurrentTaskManager instance = null;
    private Model model = null;

    private static final String DAILY_INTERVAL = "daily";
    private static final String WEEKLY_INTERVAL = "weekly";
    private static final String MONTHLY_INTERVAL = "monthly";
    private static final String YEARLY_INTERVAL = "yearly";

    /*
    * Supported recurring string types
    */
    private static final Set<String> supportedTypes = new HashSet<String>(Arrays.asList(
           new String[] {DAILY_INTERVAL, WEEKLY_INTERVAL, MONTHLY_INTERVAL, YEARLY_INTERVAL}));


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
    public void updateRecurring() throws Exception {
        this.updateAllRecurringTimestamps();
    }

    /*
     * Helper function to update all recurring tasks' timestamps for start/deadline
     */
    private void updateAllRecurringTimestamps() throws Exception {
        // Get all tasks
        ObservableList<ReadOnlyTask> taskList  = this.model.getToDoApp().getTaskList();
        for (int i = 0; i < taskList.size(); i++) {
            ReadOnlyTask task = taskList.get(i);
            // Check if current task has a recurring tag, and update the timestamp if required
            updateTask(i, task);
        }
    }

    /*
     * Helper function to check if task is recurring, and update accordingly if it's not completed
     */
    private void updateTask(int idx, ReadOnlyTask task) throws Exception {
        // Check if task is already completed
        if (task.getCompletion().value) return;
        UniqueTagList tagList = task.getTags();
        for (int j = 0; j < tagList.asObservableList().size(); j++) {
            if (supportedTypes.contains(tagList.asObservableList().get(j).tagName)) {
                // Recurrent tag detected
                Task newTask = this.updateTask(task, tagList.asObservableList().get(j).tagName);
                if (newTask != null) {
                    model.updateTask(idx, newTask);
                }
            }
        }
    }

    /*
     * Helper function to create new task with updated time
     */
    private Task updateTask(ReadOnlyTask oldTask, String recurrentType) throws ParseException, IllegalValueException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss yyyy");
        Date currentTime = new Date();
        if (oldTask.getStart().hasDate() && oldTask.getDeadline().hasDate()) {
            // If got both, update both
            Date oldStart = oldTask.getStart().getDate();
            Date oldDeadline = oldTask.getDeadline().getDate();

            // Update only if past deadline
            if (currentTime.after(oldDeadline)) {
                Date newStartDate = getNextRecurrentDate(currentTime, c, oldStart, recurrentType);
                Date newDeadlineDate = getNextRecurrentDate(currentTime, c, oldDeadline, recurrentType);
                // Make new start/end classes
                Start newStart = new Start(df.format(newStartDate));
                Deadline newDeadline = new Deadline(df.format(newDeadlineDate));
                return new Task(oldTask.getName(), newStart, newDeadline,
                        oldTask.getPriority(), oldTask.getTags(), oldTask.getNotes(),
                        oldTask.getCompletion(), oldTask.getVenue());
            }
        } else if (oldTask.getStart().hasDate()) {
            // Only start
            Date oldStart = oldTask.getStart().getDate();
            // Update only if past start date
            if (currentTime.after(oldStart)) {
                Date newStartDate = getNextRecurrentDate(currentTime, c, oldStart, recurrentType);
                // Make new start/end classes
                Start newStart = new Start(df.format(newStartDate));
                return new Task(oldTask.getName(), newStart, oldTask.getDeadline(),
                        oldTask.getPriority(), oldTask.getTags(), oldTask.getNotes(),
                        oldTask.getCompletion(), oldTask.getVenue());
            }
        } else if (oldTask.getDeadline().hasDate()) {
            // Only deadline
            Date oldDeadline = oldTask.getDeadline().getDate();
            // Update only if past start date
            if (currentTime.after(oldDeadline)) {
                Date newDeadlineDate = getNextRecurrentDate(currentTime, c, oldDeadline, recurrentType);
                // Make new start/end classes
                Deadline newDeadline = new Deadline(df.format(newDeadlineDate));
                return new Task(oldTask.getName(), oldTask.getStart(), newDeadline,
                        oldTask.getPriority(), oldTask.getTags(), oldTask.getNotes(),
                        oldTask.getCompletion(), oldTask.getVenue());
            }
        }
        // No dates: No difference - return old task
        return null;
    }

    /*
     * Helper function to move date forward by recurrent amount
     */
    private Date getNextRecurrentDate(Date currentTime, Calendar c, Date oldDate, String recurrentType) {
        c.setTime(oldDate);
        // Update the time till it's after current time
        switch (recurrentType) {
        case DAILY_INTERVAL:
            while (c.getTime().before(currentTime)) c.add(Calendar.DATE, 1);
            break;
        case WEEKLY_INTERVAL:
            while (c.getTime().before(currentTime)) c.add(Calendar.DATE, 7);
            break;
        case MONTHLY_INTERVAL:
            while (c.getTime().before(currentTime)) c.add(Calendar.MONTH, 1);
            break;
        case YEARLY_INTERVAL:
            while (c.getTime().before(currentTime)) c.add(Calendar.YEAR, 1);
            break;
        default:
            break;
        }
        return c.getTime();
    }
}
