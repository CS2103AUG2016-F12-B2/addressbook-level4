//@@ author A0124153U

package seedu.todoapp.logic.commands;

import java.text.ParseException;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.exceptions.CommandException;
import seedu.todoapp.model.person.Task;
import seedu.todoapp.model.person.UniqueTaskList.TaskInvalidTimestampsException;

/**
 * Sort the tasks in ToDoApp base on priority
 *
 */

public class SortCommand extends Command {
    
    public static final String COMMAND_WORD = "sort";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort all tasks by Priority"
            + "Parameters: KEYWORD(priority)"
            + "Example: " + COMMAND_WORD + " priority";
    
    private static String keyword;
    
    public SortCommand(String keyword) {
        this.keyword = keyword;
    }


    @Override
    public CommandResult execute()
            throws CommandException, IllegalValueException, ParseException, TaskInvalidTimestampsException {
        model.updateSortedTaskList(keyword);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
