package seedu.todoapp.model.util;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.model.ReadOnlyToDoApp;
import seedu.todoapp.model.ToDoApp;
import seedu.todoapp.model.person.Completion;
import seedu.todoapp.model.person.Deadline;
import seedu.todoapp.model.person.Name;
import seedu.todoapp.model.person.Notes;
import seedu.todoapp.model.person.Priority;
import seedu.todoapp.model.person.Start;
import seedu.todoapp.model.person.Task;
import seedu.todoapp.model.person.UniqueTaskList.DuplicateTaskException;
import seedu.todoapp.model.tag.UniqueTagList;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("Buy printer"), new Start("today"), new Deadline("tomorrow"), new Priority(1),
                        new UniqueTagList("shopping"), new Notes("this is a note"), new Completion("true")),
                new Task(new Name("Go to the gym"), new Start(""), new Deadline(""), new Priority(0),
                        new UniqueTagList("exercise"), new Notes(""), new Completion("")), };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyToDoApp getSampleToDoApp() {
        try {
            ToDoApp sampleTDA = new ToDoApp();
            for (Task sampleTask : getSampleTasks()) {
                sampleTDA.addTask(sampleTask);
            }
            return sampleTDA;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        }
    }
}
