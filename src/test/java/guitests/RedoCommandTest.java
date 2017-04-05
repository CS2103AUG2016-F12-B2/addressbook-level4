//@@author A0114395E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todoapp.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.todoapp.logic.commands.EditCommand;
import seedu.todoapp.logic.commands.RedoCommand;
import seedu.todoapp.logic.commands.UndoCommand;
import seedu.todoapp.testutil.TaskBuilder;
import seedu.todoapp.testutil.TestTask;
import seedu.todoapp.testutil.TestUtil;

public class RedoCommandTest extends ToDoAppGuiTest {

    @Test
    public void redo_add_success() {
        // Test ADD
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;

        assertAddSuccess(taskToAdd, currentList);
        assertUndoCommandSuccess(td.getTypicalTasks());
        assertRedoCommandSuccess(TestUtil.addTasksToList(currentList, taskToAdd));
    }

    @Test
    public void redo_edit_success() throws Exception {
        TestTask[] currentList = td.getTypicalTasks();

        String detailsToEdit = "Bobby t/husband";
        int toDoAppIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Bobby").withStart("Thu Mar 30 12:43:24 2017")
                .withDeadline("Thu Mar 31 12:43:24 2017").withPriority(1).withTags("husband").withNotes("").build();

        assertEditSuccess(toDoAppIndex, toDoAppIndex, detailsToEdit, editedTask);
        assertUndoCommandSuccess(td.getTypicalTasks());
        currentList[toDoAppIndex - 1] = editedTask;
        assertRedoCommandSuccess(currentList);
    }

    @Test
    public void redo_delete_success() {
        // Test DELETE
        // Try delete first in list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        assertDeleteSuccess(targetIndex, currentList);
        assertUndoCommandSuccess(td.getTypicalTasks());
        assertRedoCommandSuccess(TestUtil.removeTaskFromList(currentList, targetIndex));
    }

    @Test
    public void redo_clear_success() {
        // Test CLEAR
        //verify a non-empty list can be cleared
        assertClearCommandSuccess();
        assertUndoCommandSuccess(td.getTypicalTasks());
        assertRedoCommandSuccess(new TestTask[0]);
    }

    @Test
    public void redo_nothing_failure() {
        // Test UNDO-ing nothing
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAIL);
    }

    @Test
    public void redo_moreThanComamnds_failure() {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;

        assertAddSuccess(taskToAdd, currentList);
        assertUndoCommandSuccess(td.getTypicalTasks());
        assertRedoCommandSuccess(TestUtil.addTasksToList(currentList, taskToAdd));

        // Should have no more commands to redo
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAIL);
    }

    /*
     * ASSERTS of typical commands (add,edit,delete...) etc are the same as from the other test files
     * Undo is the same as UndoCommandTest.java
     */

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    /*
     * Runs the clear command, and check that the list is empty
     */
    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("ToDoApp has been cleared!");
    }

    /**
     * Checks whether the edited person has the correct updated details.
     *
     * @param filteredTaskListIndex index of person to edit in filtered list
     * @param toDoAppIndex index of person to edit in the address book.
     *      Must refer to the same person as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the person with as input to the edit command
     * @param editedTask the expected person after editing the person's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int toDoAppIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous persons plus the person with updated details
        TestTask[] expectedTasksList = td.getTypicalTasks();
        expectedTasksList[toDoAppIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    /*
     * Runs the undo command, and check that the list is back to the previous state
     */
    private void assertUndoCommandSuccess(TestTask[] expectedList) {
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(UndoCommand.MESSAGE_SUCCESS);
    }

    /*
     * Runs the redo command, and check that the list is back to the previous state
     */
    private void assertRedoCommandSuccess(TestTask[] expectedList) {
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertResultMessage(RedoCommand.MESSAGE_SUCCESS);
    }
}
