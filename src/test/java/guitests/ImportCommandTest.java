//@@author A0124591H
package guitests;

import org.junit.Test;

import seedu.todoapp.logic.commands.ImportCommand;

public class ImportCommandTest extends ToDoAppGuiTest {

    private static final String TEST_PATH = " new.xml";

    @Test
    public void importPath() {
        // successful import path
        commandBox.runCommand(ImportCommand.COMMAND_WORD + TEST_PATH);
        assertResultMessage(String.format(ImportCommand.MESSAGE_SUCCESS, TEST_PATH));

        // unsuccessful import path
        commandBox.runCommand(ImportCommand.COMMAND_WORD + " ");
        assertResultMessage(ImportCommand.MESSAGE_INVALID_FILE);
    }
}
