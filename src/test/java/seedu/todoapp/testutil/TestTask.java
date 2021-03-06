package seedu.todoapp.testutil;

import seedu.todoapp.model.person.Completion;
import seedu.todoapp.model.person.Deadline;
import seedu.todoapp.model.person.Name;
import seedu.todoapp.model.person.Notes;
import seedu.todoapp.model.person.Priority;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.person.Start;
import seedu.todoapp.model.person.Venue;
import seedu.todoapp.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Start start;
    private Deadline deadline;
    private Priority priority;
    private UniqueTagList tags;
    private Notes notes;
    private Completion completion;
    private Venue venue;

    public TestTask() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code taskToCopy}.
     */
    public TestTask(TestTask taskToCopy) {
        this.name = taskToCopy.getName();
        this.start = taskToCopy.getStart();
        this.deadline = taskToCopy.getDeadline();
        this.priority = taskToCopy.getPriority();
        this.tags = taskToCopy.getTags();
        this.notes = taskToCopy.getNotes();
        this.completion = taskToCopy.getCompletion();
        this.venue = taskToCopy.getVenue();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public void setCompletion(Completion completion) {
        this.completion = completion;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Start getStart() {
        return start;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public Notes getNotes() {
        return notes;
    }

    @Override
    public Completion getCompletion() {
        return completion;
    }

    @Override
    public Venue getVenue() {
        return venue;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("s/" + this.getStart().value + " ");
        sb.append("d/" + this.getDeadline().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        sb.append("n/" + this.getNotes().value + " ");
        sb.append("c/" + String.valueOf(this.getCompletion().value) + " ");
        sb.append("v/" + this.getVenue().value + " ");
        return sb.toString();
    }
}
