package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todoapp.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.todoapp.commons.core.Messages;
import seedu.todoapp.logic.commands.DeleteCommand;
import seedu.todoapp.testutil.TestTask;
import seedu.todoapp.testutil.TestUtil;

public class DeleteCommandTest extends ToDoAppGuiTest {

    //@@author A0114395E
    @Test
    public void delete_success() {
        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);
    }

    @Test
    public void delete_outOfIndex_failure() {
        TestTask[] currentList = td.getTypicalTasks();
        //invalid index
        commandBox.runCommand("delete " + String.valueOf(currentList.length + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void delete_noIndex_failure() {
        // no index
        commandBox.runCommand("delete");
        assertResultMessage(DeleteCommand.MESSAGE_USAGE);
    }

    //author
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

}
