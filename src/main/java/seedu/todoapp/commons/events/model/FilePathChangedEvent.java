//@@author A0124591H

package seedu.todoapp.commons.events.model;

import seedu.todoapp.commons.events.BaseEvent;

/** Indicates the AddressBook in the model has changed*/
public class FilePathChangedEvent extends BaseEvent {

    public final String filePath;

    public FilePathChangedEvent(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "File path of ToDoApp changed to: " + filePath;
    }
}
