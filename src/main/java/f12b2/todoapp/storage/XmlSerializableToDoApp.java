package f12b2.todoapp.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import f12b2.todoapp.commons.core.UnmodifiableObservableList;
import f12b2.todoapp.commons.exceptions.IllegalValueException;
import f12b2.todoapp.model.ReadOnlyToDoApp;
import f12b2.todoapp.model.person.ReadOnlyTask;
import f12b2.todoapp.model.person.Task;
import f12b2.todoapp.model.tag.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * An Immutable ToDoApp that is serializable to XML format
 */
@XmlRootElement(name = "todoapp")
public class XmlSerializableToDoApp implements ReadOnlyToDoApp {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableToDoApp.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableToDoApp() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableToDoApp(ReadOnlyToDoApp src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<Task> persons = this.tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(persons);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tags);
    }

}
