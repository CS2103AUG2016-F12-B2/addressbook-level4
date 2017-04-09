//@@author A0124591H
package guitests;

import org.junit.Test;

import seedu.todoapp.logic.commands.SpecifyPathCommand;

public class SpecifyPathCommandTest extends ToDoAppGuiTest {

    private static final String TEST_PATH = " new.xml";

    @Test
    public void specifyPath() {
        // successful specify path
        commandBox.runCommand(SpecifyPathCommand.COMMAND_WORD + TEST_PATH);
        assertResultMessage(String.format(SpecifyPathCommand.MESSAGE_SUCCESS, TEST_PATH));

        // unsuccessful specify path
        commandBox.runCommand("cd ");
        assertResultMessage(SpecifyPathCommand.MESSAGE_IO);
    }
}
