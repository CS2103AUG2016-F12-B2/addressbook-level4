package f12b2.todoapp.testutil;

import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.model.person.Completion;
import f12b2.todoapp.model.person.Deadline;
import f12b2.todoapp.model.person.Name;
import f12b2.todoapp.model.person.Notes;
import f12b2.todoapp.model.person.Priority;
import f12b2.todoapp.model.person.Start;
import f12b2.todoapp.model.tag.Tag;
import f12b2.todoapp.model.tag.UniqueTagList;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withStart(String start) throws IllegalValueException {
        this.task.setStart(new Start(start));
        return this;
    }

    public TaskBuilder withDeadline(String deadline) throws IllegalValueException {
        this.task.setDeadline(new Deadline(deadline));
        return this;
    }

    public TaskBuilder withPriority(int priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withTags(String... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag : tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withNotes(String notes) throws IllegalValueException {
        this.task.setNotes(new Notes(notes));
        return this;
    }

    public TaskBuilder withCompletion(String completion) throws IllegalValueException {
        this.task.setCompletion(new Completion(completion));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
