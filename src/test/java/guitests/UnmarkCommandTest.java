// @@author: A0124591H

package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.todoapp.logic.commands.MarkCommand;
import seedu.todoapp.logic.commands.UnmarkCommand;
import seedu.todoapp.testutil.TaskBuilder;
import seedu.todoapp.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower
// level tests.
public class UnmarkCommandTest extends ToDoAppGuiTest {

    // The list of persons in the person list panel is expected to match this
    // list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void unmark_unmarkedTask_success() throws Exception {
        int toDoAppIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Alice Pauline").withStart("Thu Mar 30 12:43:24 2017")
                .withDeadline("Sat Apr 1 12:43:24 2017").withPriority(1).withTags("friends").withNotes("person")
                .withVenue("NUS").withCompletion("false").build();

        assertUnmarkSuccess(toDoAppIndex, toDoAppIndex, editedTask);
    }

    @Test
    public void unmark_markedTask_success() throws Exception {
        int toDoAppIndex = 1;
        commandBox.runCommand("mark 1");

        TestTask editedTask = new TaskBuilder().withName("Alice Pauline").withStart("Thu Mar 30 12:43:24 2017")
                .withDeadline("Sat Apr 1 12:43:24 2017").withPriority(1).withTags("friends").withNotes("person")
                .withVenue("NUS").withCompletion("false").build();

        assertUnmarkSuccess(toDoAppIndex, toDoAppIndex, editedTask);
    }

    @Test
    public void unmark_missingTaskIndex_failure() {
        commandBox.runCommand("unmark");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void unmark_invalidTaskIndex_failure() {
        commandBox.runCommand("unmark " + expectedTasksList.length);
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void unmark_invalidValues_failure() {
        commandBox.runCommand("unmark abc");
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param toDoAppIndex index of task to edit in the To Do App. Must refer to
     *            the same task as {@code filteredTaskListIndex}
     * @param unmarkedTask the expected task after editing the person's details
     */
    private void assertUnmarkSuccess(int filteredTaskListIndex, int toDoAppIndex, TestTask unmarkedTask) {
        commandBox.runCommand("unmark " + filteredTaskListIndex);

        // confirm the new card contains the right data
        TaskCardHandle unmarkedCard = taskListPanel.navigateToTask(unmarkedTask.getName().fullName);
        assertMatching(unmarkedTask, unmarkedCard);

        // confirm the list now contains all previous persons plus the person
        // with updated details
        expectedTasksList[toDoAppIndex - 1] = unmarkedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, unmarkedTask));
    }
}
