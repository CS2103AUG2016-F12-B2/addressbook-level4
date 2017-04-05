package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.todoapp.logic.commands.EditCommand;
import seedu.todoapp.model.person.Name;
import seedu.todoapp.model.tag.Tag;
import seedu.todoapp.testutil.TaskBuilder;
import seedu.todoapp.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends ToDoAppGuiTest {

    // The list of persons in the person list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Bobby t/husband";
        int toDoAppIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Bobby").withStart("Thu Mar 30 12:43:24 2017")
                .withDeadline("Thu Mar 31 12:43:24 2017").withPriority(1).withTags("husband").withNotes("").build();

        assertEditSuccess(toDoAppIndex, toDoAppIndex, detailsToEdit, editedTask);
    }

    //@@author A0114395E
    @Test
    public void edit_moreDetailsFieldsSpecified_success() throws Exception {
        String detailsToEdit = "Buy a zebra s/Mon Jul 10 12:43:24 2017 d/Wed Jul 12 12:43:24 2017 "
                + "t/animal p/3 n/find a poacher";
        int toDoAppIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Buy a zebra")
                .withStart("Mon Jul 10 12:43:24 2017").withDeadline("Wed Jul 12 12:43:24 2017")
                .withPriority(3).withTags("animal").withNotes("find a poacher").build();

        assertEditSuccess(toDoAppIndex, toDoAppIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "t/sweetie t/bestie";
        int toDoAppIndex = 2;

        TestTask personToEdit = expectedTasksList[toDoAppIndex - 1];
        TestTask editedTask = new TaskBuilder(personToEdit).withTags("sweetie", "bestie").build();

        assertEditSuccess(toDoAppIndex, toDoAppIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_clearTags_success() throws Exception {
        String detailsToEdit = "t/";
        int toDoAppIndex = 2;

        TestTask personToEdit = expectedTasksList[toDoAppIndex - 1];
        TestTask editedTask = new TaskBuilder(personToEdit).withTags().build();

        assertEditSuccess(toDoAppIndex, toDoAppIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find n/ Elle");

        String detailsToEdit = "Belle";
        int filteredTaskListIndex = 1;
        int toDoAppIndex = 5;

        TestTask personToEdit = expectedTasksList[toDoAppIndex - 1];
        TestTask editedTask = new TaskBuilder(personToEdit).withName("Belle").build();

        assertEditSuccess(filteredTaskListIndex, toDoAppIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit Bobby");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 Bobby");
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/*&");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit 3 Alice Pauline s/today d/tomorrow "
                                + "p/1 t/friends");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    //@@author A0114395E
    @Test
    public void edit_deadlineBeforeStart_failure() throws Exception {
        commandBox.runCommand("edit 3 Buy a zebra s/Wed Jul 12 12:43:24 2017 d/Mon Jul 10 12:43:24 2017 "
                + "t/animal p/3 n/find a poacher");

        assertResultMessage(EditCommand.MESSAGE_INVALID_START_END);
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
        expectedTasksList[toDoAppIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
