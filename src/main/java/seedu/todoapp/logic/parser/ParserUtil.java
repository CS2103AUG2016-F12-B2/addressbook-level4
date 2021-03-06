package seedu.todoapp.logic.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.todoapp.commons.exceptions.IllegalValueException;
import seedu.todoapp.commons.util.StringUtil;
import seedu.todoapp.model.person.Name;
import seedu.todoapp.model.person.ReadOnlyTask;
import seedu.todoapp.model.tag.Tag;
import seedu.todoapp.model.tag.UniqueTagList;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty,
     * or if the list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new HashSet<>(elements);
    }

    /**
    * Splits a preamble string into ordered fields.
    * @return A list of size {@code numFields} where the ith element is the ith field value if specified in
    *         the input, {@code Optional.empty()} otherwise.
    */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }

    //@@author A0114395E
    /*
     * Helper method to parse a ReadOnlyTask into an command-line statement to be stored.
     * @param ReadOnlyTask, boolean isAddCommand
     * @returns String consisting of how a user would have typed the original command
     */
    public static String getTaskArgs(ReadOnlyTask task, boolean isAddCommand) {
        // Build arguments
        final StringBuilder builder = new StringBuilder();
        builder.append(task.getName());
        buildCommandForStart(builder, task);
        buildCommandForDeadline(builder, task);
        buildCommandForPriority(builder, task);
        buildCommandForNotes(builder, task);
        buildCommandForVenue(builder, task);
        buildCommandForTags(builder, task, isAddCommand);

        return builder.toString();
    }

    /*
     * Helper function to get command for Start
     * @return String
     */
    private static void buildCommandForStart(StringBuilder builder, ReadOnlyTask task) {
        if (task.getStart().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_START.getPrefix());
            builder.append(task.getStart().toString());
        }
    }

    /*
     * Helper function to get command for Deadline
     * @return String
     */
    private static void buildCommandForDeadline(StringBuilder builder, ReadOnlyTask task) {
        if (task.getDeadline().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_DEADLINE.getPrefix());
            builder.append(task.getDeadline().toString());
        }
    }

    /*
     * Helper function to get command for Priority
     * @return String
     */
    private static void buildCommandForPriority(StringBuilder builder, ReadOnlyTask task) {
        if (task.getPriority().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_PRIORITY.getPrefix());
            builder.append(task.getPriority().toString());
        }
    }

    /*
     * Helper function to get command for Notes
     * @return String
     */
    private static void buildCommandForNotes(StringBuilder builder, ReadOnlyTask task) {
        if (task.getNotes().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_NOTES.getPrefix());
            builder.append(task.getNotes().toString());
        }
    }

    /*
     * Helper function to get command for Venue
     * @return String
     */
    private static void buildCommandForVenue(StringBuilder builder, ReadOnlyTask task) {
        if (task.getVenue().toString().length() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_VENUE.getPrefix());
            builder.append(task.getVenue().toString());
        }
    }

    /*
     * Helper function to get command for Tags
     * @return String
     */
    private static void buildCommandForTags(StringBuilder builder, ReadOnlyTask task, boolean isAddCommand) {
        if (task.getTags().asObservableList().size() > 0) {
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_TAG.getPrefix());
            final StringBuilder tagBuilder = new StringBuilder();
            task.getTags().forEach(tagBuilder::append);
            // Remove square brackets for tags
            builder.append(tagBuilder.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        } else if (!isAddCommand) {
            // Return empty tag if it's not add command (i.e edit)
            builder.append(" ");
            builder.append(CliSyntax.PREFIX_TAG.getPrefix());
        }
    }
}
