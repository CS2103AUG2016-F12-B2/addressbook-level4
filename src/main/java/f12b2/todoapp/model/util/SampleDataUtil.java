package f12b2.todoapp.model.util;

import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.model.ReadOnlyToDoApp;
import f12b2.todoapp.model.ToDoApp;
import f12b2.todoapp.model.person.Completion;
import f12b2.todoapp.model.person.Deadline;
import f12b2.todoapp.model.person.Name;
import f12b2.todoapp.model.person.Notes;
import f12b2.todoapp.model.person.Priority;
import f12b2.todoapp.model.person.Start;
import f12b2.todoapp.model.person.Task;
import f12b2.todoapp.model.person.UniqueTaskList.DuplicateTaskException;
import f12b2.todoapp.model.tag.UniqueTagList;

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
