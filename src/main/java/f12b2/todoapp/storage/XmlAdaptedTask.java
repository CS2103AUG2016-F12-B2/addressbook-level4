package f12b2.todoapp.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.model.person.Completion;
import f12b2.todoapp.model.person.Deadline;
import f12b2.todoapp.model.person.Name;
import f12b2.todoapp.model.person.Notes;
import f12b2.todoapp.model.person.Priority;
import f12b2.todoapp.model.person.ReadOnlyTask;
import f12b2.todoapp.model.person.Start;
import f12b2.todoapp.model.person.Task;
import f12b2.todoapp.model.tag.Tag;
import f12b2.todoapp.model.tag.UniqueTagList;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private String start;
    @XmlElement
    private String deadline;
    @XmlElement
    private int priority;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private String notes;
    @XmlElement
    private String completion;

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        start = source.getStart().value;
        deadline = source.getDeadline().value;
        priority = source.getPriority().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        notes = source.getNotes().value;
        completion = String.valueOf(source.getCompletion().value);
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Start start = new Start(this.start);
        final Deadline deadline = new Deadline(this.deadline);
        final Priority priority = new Priority(this.priority);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        final Notes notes = new Notes(this.notes);
        final Completion completion = new Completion(this.completion);
        return new Task(name, start, deadline, priority, tags, notes, completion);
    }
}
