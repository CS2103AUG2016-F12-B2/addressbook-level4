package seedu.todoapp.logic.parser;

import static seedu.todoapp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_COMPLETION;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_START;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.todoapp.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.todoapp.commons.core.UnmodifiableObservableList;
import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.logic.commands.Command;
import seedu.todoapp.logic.commands.EditCommand;
import seedu.todoapp.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.todoapp.logic.commands.HelpCommand;
import seedu.todoapp.logic.commands.IncorrectCommand;
import seedu.todoapp.model.person.Completion;
import seedu.todoapp.model.person.Deadline;
import seedu.todoapp.model.person.Name;
import seedu.todoapp.model.person.Notes;
import seedu.todoapp.model.person.Priority;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.person.Start;
import seedu.todoapp.model.person.Venue;
import seedu.todoapp.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    //@@author A0114395E
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START, PREFIX_DEADLINE, PREFIX_PRIORITY,
                                        PREFIX_TAG, PREFIX_NOTES, PREFIX_COMPLETION, PREFIX_VENUE);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setTags(parseTagsForEdit
                    (ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
            // We only set new values if user had input something.
            Optional<Name> nameStr = ParserUtil.parseName(preambleFields.get(1));
            if (nameStr.isPresent() && nameStr.get().fullName.length() > 0) editTaskDescriptor.setName(nameStr);
            //@@author A0124591H
            setStartValueForDescriptor(args.contains(PREFIX_START.prefix), argsTokenizer, editTaskDescriptor);
            setDeadlineValueForDescriptor(args.contains(PREFIX_DEADLINE.prefix), argsTokenizer, editTaskDescriptor);
            setPriorityValueForDescriptor(args.contains(PREFIX_PRIORITY.prefix), argsTokenizer, editTaskDescriptor);
            setNotesValueForDescriptor(args.contains(PREFIX_NOTES.prefix), argsTokenizer, editTaskDescriptor);
            setCompletionValueForDescriptor(args.contains(PREFIX_COMPLETION.prefix), argsTokenizer, editTaskDescriptor);
            //@@author
            setVenueValueForDescriptor(args.contains(PREFIX_VENUE.prefix), argsTokenizer, editTaskDescriptor);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    //@@author A0114395E
    /*
     * Helper method to parse a get the inverse arguments of an Edit command
     * @param String, UnmodifiableObservableList<ReadOnlyTask>
     * @returns String of arguments for the inverse of the input edit command
     */
    public Command parseInverse(String arguments,
            UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
        argsTokenizer.tokenize(arguments);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);
        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (index.isPresent() && index.get() <= lastShownList.size()) {
            ReadOnlyTask taskToEdit = lastShownList.get(index.get() - 1);
            final StringBuilder editBuilder = new StringBuilder();
            editBuilder.append(" ");
            editBuilder.append(index.get().toString());
            editBuilder.append(" ");
            editBuilder.append(ParserUtil.getTaskArgs(taskToEdit, false));
            return parse(editBuilder.toString());
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    //@@author A0114395E
    /**
     * Handler for building editTaskDescriptor for Start
     * @throws IllegalValueException if start date is invalid
     */
    private void setStartValueForDescriptor(boolean containsPrefix,
            ArgumentTokenizer argsTokenizer, EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        // Check start time stamp
        String startStr = argsTokenizer.getValue(PREFIX_START).orElse("");
        if (startStr.length() > 0) {
            editTaskDescriptor.setStart(Optional.of(new Start(startStr)));
        } else if (containsPrefix) {
            editTaskDescriptor.setStart(Optional.of(new Start("-")));
        }
    }

    /**
     * Handler for building editTaskDescriptor for Deadline
     * @throws IllegalValueException if deadline is invalid
     */
    private void setDeadlineValueForDescriptor(boolean containsPrefix,
            ArgumentTokenizer argsTokenizer, EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        // Check deadline
        String deadlineStr = argsTokenizer.getValue(PREFIX_DEADLINE).orElse("");
        if (deadlineStr.length() > 0) {
            editTaskDescriptor.setDeadline(Optional.of(new Deadline(deadlineStr)));
        } else if (containsPrefix) {
            editTaskDescriptor.setDeadline(Optional.of(new Deadline("-")));
        }
    }

    /**
     * Handler for building editTaskDescriptor for Priority
     * @throws IllegalValueException if priority value is invalid
     */
    private void setPriorityValueForDescriptor(boolean containsPrefix,
            ArgumentTokenizer argsTokenizer, EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        // Check priority
        int priorityInt = Integer.parseInt(argsTokenizer.getValue(PREFIX_PRIORITY).orElse("0"));
        if (priorityInt > 0) {
            editTaskDescriptor.setPriority(Optional.of(new Priority(priorityInt)));
        } else if (containsPrefix) {
            editTaskDescriptor.setPriority(Optional.of(new Priority(0)));
        }
    }

    /**
     * Handler for building editTaskDescriptor for Notes
     * @throws IllegalValueException if notes value is invalid
     */
    private void setNotesValueForDescriptor(boolean containsPrefix,
            ArgumentTokenizer argsTokenizer, EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        // Check notes
        String notesStr = argsTokenizer.getValue(PREFIX_NOTES).orElse("");
        if (notesStr.length() > 0) {
            editTaskDescriptor.setNotes(Optional.of(new Notes(notesStr)));
        } else if (containsPrefix) {
            editTaskDescriptor.setNotes(Optional.of(new Notes("-")));
        }
    }

    /**
     * Handler for building editTaskDescriptor for Completion
     * @throws IllegalValueException if Completion value is invalid
     */
    private void setCompletionValueForDescriptor(boolean containsPrefix,
            ArgumentTokenizer argsTokenizer, EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        // Check notes
        Boolean completionBool = Boolean.parseBoolean(argsTokenizer.getValue(PREFIX_COMPLETION).orElse("false"));
        if (completionBool) {
            editTaskDescriptor.setCompletion((Optional.of(new Completion(String.valueOf(completionBool)))));
        } else if (containsPrefix) {
            editTaskDescriptor.setCompletion((Optional.of(new Completion("false"))));
        }
    }

    //@@author A0124153U
    /**
     * Handler for building editTaskDescriptor for completion
     * @throws IllegalValueException
     * @param argsTokenizer
     * @param editTaskDescriptor
     * @throws IllegalValueException
     */
    private void setVenueValueForDescriptor(boolean containsPrefix,
            ArgumentTokenizer argsTokenizer, EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        // Check venue
        String venueStr = argsTokenizer.getValue(PREFIX_VENUE).orElse("");
        if (venueStr.length() > 0) {
            editTaskDescriptor.setVenue(Optional.of(new Venue(venueStr)));
        } else if (containsPrefix) {
            editTaskDescriptor.setVenue(Optional.of(new Venue("-")));
        }
    }
}
