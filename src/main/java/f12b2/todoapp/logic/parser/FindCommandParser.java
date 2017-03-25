package f12b2.todoapp.logic.parser;

import static f12b2.todoapp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static f12b2.todoapp.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

import f12b2.todoapp.logic.commands.Command;
import f12b2.todoapp.logic.commands.FindCommand;
import f12b2.todoapp.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        // final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywords);
    }

}
