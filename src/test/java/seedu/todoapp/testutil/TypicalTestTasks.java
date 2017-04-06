package seedu.todoapp.testutil;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.model.ToDoApp;
import seedu.todoapp.model.person.Task;
import seedu.todoapp.model.person.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withStart("Thu Mar 30 12:43:24 2017").withDeadline("Sat Apr 1 12:43:24 2017")
                    .withPriority(1).withTags("friends").withNotes("").withVenue("")
                    .withCompletion("false").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withStart("Thu Mar 30 12:43:24 2017").withDeadline("Sat Apr 1 12:43:24 2017")
                    .withTags("owesMoney", "friends")
                    .withPriority(1).withNotes("").withVenue("")
                    .withCompletion("false").build();
            carl = new TaskBuilder().withName("Carl Kurz")
                    .withStart("Thu Mar 30 12:43:24 2017").withDeadline("Sat Apr 1 12:43:24 2017")
                    .withPriority(1).withNotes("").withVenue("").withCompletion("false").build();
            daniel = new TaskBuilder().withName("Daniel Meier")
                    .withStart("Thu Mar 30 12:43:24 2017").withDeadline("Sat Apr 1 12:43:24 2017")
                    .withPriority(1).withNotes("").withVenue("").withCompletion("false").build();
            elle = new TaskBuilder().withName("Elle Meyer")
                    .withStart("Thu Mar 30 12:43:24 2017").withDeadline("Sat Apr 1 12:43:24 2017")
                    .withPriority(1).withNotes("").withVenue("").withCompletion("false").build();
            fiona = new TaskBuilder().withName("Fiona Kunz")
                    .withStart("Thu Mar 30 12:43:24 2017").withDeadline("Sat Apr 1 12:43:24 2017")
                    .withPriority(1).withNotes("").withVenue("").withCompletion("false").build();
            george = new TaskBuilder().withName("George Best")
                    .withStart("Thu Mar 30 12:43:24 2017").withDeadline("Sat Apr 1 12:43:24 2017")
                    .withPriority(1).withNotes("").withVenue("").withCompletion("false").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withNotes("Something").withVenue("NUS").withCompletion("false").build();
            ida = new TaskBuilder().withName("Ida Mueller")
                    .withStart("today").withDeadline("next week")
                    .withPriority(1).withNotes("Another thing").withVenue("NTU").withCompletion("false").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoAppWithSampleData(ToDoApp ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public ToDoApp getTypicalToDoApp() {
        ToDoApp ab = new ToDoApp();
        loadToDoAppWithSampleData(ab);
        return ab;
    }
}
