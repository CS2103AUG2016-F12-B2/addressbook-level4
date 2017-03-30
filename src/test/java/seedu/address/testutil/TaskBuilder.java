package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Block;
import seedu.address.model.person.Completion;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Start;
import seedu.address.model.person.UniqueBlockList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    //@@author: A0124591H
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

    public TaskBuilder withBlocks(String... blocks) throws IllegalValueException {
        task.setBlocks(new UniqueBlockList());
        for (String block : blocks) {
            task.getBlocks().add(new Block(block));
        }
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
