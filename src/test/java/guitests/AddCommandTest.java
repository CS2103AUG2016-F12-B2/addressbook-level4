package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.todoapp.commons.core.Messages;
import seedu.todoapp.logic.commands.AddCommand;
import seedu.todoapp.testutil.TaskBuilder;
import seedu.todoapp.testutil.TestTask;
import seedu.todoapp.testutil.TestUtil;

public class AddCommandTest extends ToDoAppGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author A0114395E
    @Test
    public void add_noName_failure() throws Exception {
        commandBox.runCommand("add s/");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void add_barebones_success() throws Exception {
        final String bareboneTaskName = "Buy tofu";
        //add an only-name task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask barebonesTask = new TaskBuilder().withName(bareboneTaskName).build();

        commandBox.runCommand("add ".concat(bareboneTaskName));
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, barebonesTask);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

    @Test
    public void add_deadlineBeforeStart_failure() throws Exception {
        commandBox.runCommand("add Buy a zebra s/Wed Jul 12 12:43:24 2017 d/Mon Jul 10 12:43:24 2017 "
                + "t/animal p/3 n/find a poacher");

        assertResultMessage(AddCommand.MESSAGE_INVALID_START_END);
    }

    @Test
    public void add_recurringWeekly_success() throws Exception {
        commandBox.runCommand("add Buy a zebra s/Thu Apr 6 12:43:24 2017 d/Fri Apr 7 12:43:24 2017 "
                + "t/weekly p/3 n/find a poacher");

        TestTask taskToAdd = new TaskBuilder().withName("Buy a zebra")
                .withStart("Thu Apr 13 12:43:24 2017").withDeadline("Fri Apr 14 12:43:24 2017").withTags("weekly")
                .withPriority(3).withNotes("find a poacher").withVenue("-").withCompletion("false").build();
        commandBox.runCommand("list");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Buy a zebra");
        assertMatching(taskToAdd, addedCard);
    }

    @Test
    public void add_recurringMonthly_success() throws Exception {
        commandBox.runCommand("add Buy a zebra s/Wed Jan 11 12:43:24 2017 d/Thu Jan 12 12:43:24 2017 "
                + "t/monthly p/3 n/find a poacher");

        TestTask taskToAdd = new TaskBuilder().withName("Buy a zebra")
                .withStart("Thu May 11 12:43:24 2017").withDeadline("Fri May 12 12:43:24 2017").withTags("monthly")
                .withPriority(3).withNotes("find a poacher").withVenue("-").withCompletion("false").build();
        commandBox.runCommand("list");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Buy a zebra");
        assertMatching(taskToAdd, addedCard);
    }

    @Test
    public void add_recurringYearly_success() throws Exception {
        commandBox.runCommand("add Buy a zebra s/Wed Jan 11 12:43:24 2017 d/Thu Jan 12 12:43:24 2017 "
                + "t/yearly p/3 n/find a poacher");

        TestTask taskToAdd = new TaskBuilder().withName("Buy a zebra")
                .withStart("Thu Jan 11 12:43:24 2018").withDeadline("Fri Jan 12 12:43:24 2018").withTags("yearly")
                .withPriority(3).withNotes("find a poacher").withVenue("-").withCompletion("false").build();
        commandBox.runCommand("list");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Buy a zebra");
        assertMatching(taskToAdd, addedCard);
    }

    @Test
    public void add_recurringYearlyDeadline_success() throws Exception {
        commandBox.runCommand("add Buy a zebra d/Thu Jan 12 12:43:24 2017 "
                + "t/yearly p/3 n/find a poacher");

        TestTask taskToAdd = new TaskBuilder().withName("Buy a zebra")
                .withStart("-").withDeadline("Fri Jan 12 12:43:24 2018").withTags("yearly")
                .withPriority(3).withNotes("find a poacher").withVenue("-").withCompletion("false").build();
        commandBox.runCommand("list");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Buy a zebra");
        assertMatching(taskToAdd, addedCard);
    }

    @Test
    public void add_recurringAndCompletedYearlyDeadline_doesNotUpdate() throws Exception {
        commandBox.runCommand("add Buy a zebra d/Thu Jan 12 12:43:24 2017 "
                + "t/yearly p/3 n/find a poacher c/true");
        // Should not update recurring deadline if it's completed 
        TestTask taskToAdd = new TaskBuilder().withName("Buy a zebra")
                .withStart("-").withDeadline("Thu Jan 12 12:43:24 2017").withTags("yearly")
                .withPriority(3).withNotes("find a poacher").withVenue("-").withCompletion("true").build();
        commandBox.runCommand("list");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Buy a zebra");
        assertMatching(taskToAdd, addedCard);
    }

    @Test
    public void add_recurringYearlyStart_success() throws Exception {
        commandBox.runCommand("add Buy a zebra s/Thu Jan 12 12:43:24 2017 "
                + "t/yearly p/3 n/find a poacher");

        TestTask taskToAdd = new TaskBuilder().withName("Buy a zebra")
                .withStart("Fri Jan 12 12:43:24 2018").withDeadline("-").withTags("yearly")
                .withPriority(3).withNotes("find a poacher").withVenue("-").withCompletion("false").build();
        commandBox.runCommand("list");
        TaskCardHandle addedCard = taskListPanel.navigateToTask("Buy a zebra");
        assertMatching(taskToAdd, addedCard);
    }
    //@@author

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
