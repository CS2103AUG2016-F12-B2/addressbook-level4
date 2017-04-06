//@@author A0114395E
package guitests;

import org.junit.Test;

import seedu.todoapp.commons.core.Messages;

public class ExitCommandTest extends ToDoAppGuiTest {

    @Test
    public void exit_success() {
        // Should exit
        commandBox.runCommand("exit");
    }

    @Test
    public void exit_failure() {
        // Should not exit
        commandBox.runCommand("exits");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("zexit");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
}
