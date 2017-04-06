package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.todoapp.commons.core.Messages;
import seedu.todoapp.testutil.TestTask;

public class FindCommandTest extends ToDoAppGuiTest {

    @Test
    public void find_nonEmptyList_byName() {
        assertFindResult("find n/ Mark"); // no results
        assertFindResult("find n/ Meier", td.benson, td.daniel); // multiple
                                                                 // results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find n/ Meier", td.daniel);
    }

    //@@author: A0124591H
    @Test
    public void find_nonEmptyList_byPriority() {
        assertFindResult("find p/ 3"); // no results
        assertFindResult("find p/ one"); // no results
        assertFindResult("find p/ 1", td.fiona, td.george); // multiple results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find p/ 1", td.george);
    }

    @Test
    public void find_nonEmptyList_byCompletion() {
        commandBox.runCommand("list");
        assertFindResult("find c/ true"); // no results

        // find after deleting one result
        commandBox.runCommand("mark 1");
        assertFindResult("find c/ true", td.alice);
    }

    @Test
    public void find_nonEmptyList_byStart() {
        assertFindResult("find s/ Apr 1"); // no results
        assertFindResult("find s/ Wed Mar 29 12:43:24 2017", td.elle); // one
                                                                       // result
        assertFindResult("find s/ today", td.hoon, td.ida); // multiple results

        // find after deleting one result
        commandBox.runCommand("delete 2");
        assertFindResult("find s/ today", td.hoon);
    }

    @Test
    public void find_nonEmptyList_byDeadline() {
        assertFindResult("find d/ Dec 21"); // no results
        assertFindResult("find d/ Sat Apr 1 15:43:24 2017", td.fiona); // one
                                                                       // result
        assertFindResult("find d/ tmr", td.hoon); // multiple results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find d/ tmr");
    }

    //@@author

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find n/ Jean"); // no results
        assertFindResult("find p/ 1"); // no results

    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
