package seedu.todoapp.logic.parser;

import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todoapp.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.todoapp.commons.core.UnmodifiableObservableList;
import seedu.todoapp.logic.commands.AddCommand;
import seedu.todoapp.logic.commands.ClearCommand;
import seedu.todoapp.logic.commands.Command;
import seedu.todoapp.logic.commands.DeleteCommand;
import seedu.todoapp.logic.commands.EditCommand;
import seedu.todoapp.logic.commands.ExitCommand;
import seedu.todoapp.logic.commands.FindCommand;
import seedu.todoapp.logic.commands.HelpCommand;
import seedu.todoapp.logic.commands.IncorrectCommand;
import seedu.todoapp.logic.commands.ListCommand;
import seedu.todoapp.logic.commands.MarkCommand;
import seedu.todoapp.logic.commands.RedoCommand;
import seedu.todoapp.logic.commands.RestoreModelCommand;
import seedu.todoapp.logic.commands.SelectCommand;
import seedu.todoapp.logic.commands.SpecifyPathCommand;
import seedu.todoapp.logic.commands.UndoCommand;
import seedu.todoapp.logic.commands.UnmarkCommand;
import seedu.todoapp.model.Model;
import seedu.todoapp.model.StateManager;
import seedu.todoapp.model.person.ReadOnlyTask;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    // @@author A0114395E
    private static Parser instance = null;

    // Exists only to defeat instantiation.
    protected Parser() {
    }

    // Returns the singleton instance
    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }
    // @@author

    /**
     * Parses user input into command for execution.
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, -1);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        // @@author A0124591H
        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);
        // @@author

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        // @@author A0124591H
        case SpecifyPathCommand.COMMAND_WORD:
            return new SpecifyPathCommand(arguments);
        // @@author

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    // @@author A0114395E
    /**
     * Parses user input into its inverse command for undo command. Only
     * supports inverse of Add, Delete, Edit.
     * @param userInput full user input string
     * @return the inverse of the command based on the user input
     */
    public Command parseInverseCommand(String userInput, Model model) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        Optional<Integer> index = null;

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new DeleteCommand(lastShownList.size() + 1);

        case ClearCommand.COMMAND_WORD:
            // Saving the current data
            StateManager.getInstance().onClearCommand(model.getToDoApp());
            return new RestoreModelCommand();

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parseInverse(arguments, lastShownList);

        case DeleteCommand.COMMAND_WORD:
            index = ParserUtil.parseIndex(arguments);
            if (!index.isPresent() || index.get() > lastShownList.size()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            } else {
                // Get data of command to be deleted
                ReadOnlyTask taskToDelete = lastShownList.get(index.get() - 1);
                String taskArgs = ParserUtil.getTaskArgs(taskToDelete);
                // For add commands, we do not want the empty t/
                taskArgs = taskArgs.replaceAll(CliSyntax.PREFIX_TAG.getPrefix(), "");
                return new AddCommandParser().parse(ParserUtil.getTaskArgs(taskToDelete), index.get() - 1);
            }

        case MarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        default:
            return null;
        }
    }
}
