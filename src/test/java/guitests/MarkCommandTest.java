// @@author: A0124591H

package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.todoapp.logic.commands.MarkCommand;
import seedu.todoapp.testutil.TaskBuilder;
import seedu.todoapp.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower
// level tests.
public class MarkCommandTest extends ToDoAppGuiTest {

    // The list of persons in the person list panel is expected to match this
    // list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void mark_unmarkedTask_success() throws Exception {
        int toDoAppIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Alice Pauline").withStart("Thu Mar 30 12:43:24 2017")
                .withDeadline("Sat Apr 1 12:43:24 2017").withPriority(1).withTags("friends").withNotes("")
                .withCompletion("true").build();

        assertMarkSuccess(toDoAppIndex, toDoAppIndex, editedTask);
    }

    @Test
    public void mark_markedTask_success() throws Exception {
        int toDoAppIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Alice Pauline").withStart("Thu Mar 30 12:43:24 2017")
                .withDeadline("Sat Apr 1 12:43:24 2017").withPriority(1).withTags("friends").withNotes("")
                .withCompletion("true").build();

        assertMarkSuccess(toDoAppIndex, toDoAppIndex, editedTask);
    }

    @Test
    public void mark_missingTaskIndex_failure() {
        commandBox.runCommand("mark");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void mark_invalidTaskIndex_failure() {
        commandBox.runCommand("mark " + expectedTasksList.length);
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void mark_invalidValues_failure() {
        commandBox.runCommand("mark abc");
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Checks whether the edited task has the correct updated details.
     * @param filteredTaskListIndex index of task to edit in filtered list
     * @param toDoAppIndex index of task to edit in the To Do App. Must refer to
     *            the same task as {@code filteredTaskListIndex}
     * @param markedTask the expected task after editing the person's details
     */
    private void assertMarkSuccess(int filteredTaskListIndex, int toDoAppIndex, TestTask markedTask) {
        commandBox.runCommand("mark " + filteredTaskListIndex);

        // confirm the new card contains the right data
        TaskCardHandle markedCard = taskListPanel.navigateToTask(markedTask.getName().fullName);
        assertMatching(markedTask, markedCard);

        // confirm the list now contains all previous persons plus the person
        // with updated details
        expectedTasksList[toDoAppIndex - 1] = markedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, markedTask));
    }
}
