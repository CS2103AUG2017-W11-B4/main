package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.LIST_OF_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPTY_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_STRING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Class that is responsible for generating hints based on user input
 * Contains one public method generateHint which returns an appropriate hint based on input
 */
public class HintParser {

    private static String commandWord;
    private static String arguments;
    private static String userInput;

    /**
     * Parses {@code String input} and returns an appropriate hint
     */
    public static String generateHint(String input) {
        //the ordering matters as prefix hints are generated inorder
        assert LIST_OF_PREFIXES.equals(Arrays.asList(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_REMARK, PREFIX_EMPTY));

        String[] command;

        try {
            command = ParserUtil.parseCommandAndArguments(input);
        } catch (ParseException e) {
            return " type help for guide";
        }

        userInput = input;
        commandWord = command[0];
        arguments = command[1];
        String hintContent = generateHintContent();

        return hintContent;
    }

    /**
     * returns an appropriate hint based on commandWord and arguments
     * userInput and arguments are referenced to decide whether whitespace should be added to
     * the front of the hint
     */
    private static String generateHintContent() {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return generateAddHint();
        case AliasCommand.COMMAND_WORD:
            return " creates an alias";
        case EditCommand.COMMAND_WORD:
            return generateEditHint();
        case FindCommand.COMMAND_WORD:
            return generateFindHint();
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
            return generateDeleteAndSelectHint();
        case ClearCommand.COMMAND_WORD:
            return " clears address book";
        case ListCommand.COMMAND_WORD:
            return " lists all people";
        case HistoryCommand.COMMAND_WORD:
            return " show command history";
        case ExitCommand.COMMAND_WORD:
            return " exits the app";
        case HelpCommand.COMMAND_WORD:
            return " shows user guide";
        case UndoCommand.COMMAND_WORD:
            return " undo command";
        case RedoCommand.COMMAND_WORD:
            return " redo command";
        default:
            return " type help for guide";
        }
    }

    /**
     * parses the end of arguments to check if user is currently typing a prefix that is not in ignoredPrefixes
     * returns hint if user is typing a prefix
     * returns empty Optional if user is not typing a prefix
     */
    private static Optional<String> generatePrefixHintBasedOnEndArgs(Prefix... ignoredPrefixes) {

        Set<Prefix> ignoredPrefixSet = Arrays.asList(ignoredPrefixes).stream().collect(Collectors.toSet());

        for (Prefix p : LIST_OF_PREFIXES) {
            if (ignoredPrefixSet.contains(p)) {
                continue;
            }
            String prefixLetter = " " + (p.getPrefix().toCharArray()[0]); // " n"
            String identifier = "" + (p.getPrefix().toCharArray()[1]); // "/"
            String parameter = prefixIntoParameter(p);

            if (arguments.endsWith(p.getPrefix())) {
                return Optional.of(parameter);
            } else if (arguments.endsWith(prefixLetter)) {
                return Optional.of(identifier + parameter);
            }
        }
        return Optional.empty();
    }

    /**
     * Currently this method is always called after generatePrefixHintBasedOnEndArgs
     * It parses arguments to check for parameters that have not been filled up
     * {@code ignoredPrefixes} are omitted during this check
     * returns hint for parameter that is not present
     * returns returns {@code defaultHint} if all parameters are present
     */
    private static String offerHint(String defaultHint, Prefix... ignoredPrefixes) {

        Set<Prefix> ignoredPrefixesSet = Arrays.asList(ignoredPrefixes).stream().collect(Collectors.toSet());

        //remove ignored prefixes without losing order
        List<Prefix> prefixList = new ArrayList<>();
        for (Prefix p : LIST_OF_PREFIXES) {
            if (!ignoredPrefixesSet.contains(p)) {
                prefixList.add(p);
            }
        }

        String whitespace = userInput.endsWith(" ") ? "" : " ";
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(arguments, prefixList.toArray(new Prefix[0]));

        for (Prefix p : prefixList) {

            Optional<String> parameterOptional = argumentMultimap.getValue(p);
            if (!parameterOptional.isPresent()) {
                return whitespace + p.getPrefix() + prefixIntoParameter(p);
            }
        }
        return whitespace + defaultHint;
    }

    /**
     * returns a parameter based on {@code prefix}
     */
    private static String prefixIntoParameter(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_NAME_STRING:
            return "NAME";
        case PREFIX_PHONE_STRING:
            return "PHONE";
        case PREFIX_ADDRESS_STRING:
            return "ADDRESS";
        case PREFIX_EMAIL_STRING:
            return "EMAIL";
        case PREFIX_TAG_STRING:
            return "TAG";
        case PREFIX_REMARK_STRING:
            return "REMARK";
        case PREFIX_EMPTY_STRING:
            return "KEYWORD";
        default:
            return "KEYWORD";
        }
    }

    /**
     * parses arguments to check if index is present
     * checks on userInput to handle whitespace
     * returns "index" if index is not present
     * else returns an empty Optional
     */
    private static Optional<String> generateIndexHint() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        try {
            ParserUtil.parseIndex(arguments);
            return Optional.empty();
        } catch (IllegalValueException ive) {
            if (arguments.matches(".*\\s\\d+\\s.*")) {
                return Optional.empty();
            }
            return Optional.of(whitespace + "index");
        }
    }

    /**
     * returns a hint specific to the add command
     */
    private static String generateAddHint() {

        Optional<String> endHintOptional = generatePrefixHintBasedOnEndArgs(PREFIX_EMPTY, PREFIX_REMARK);
        if (endHintOptional.isPresent()) {
            return endHintOptional.get();
        }
        return offerHint("", PREFIX_EMPTY, PREFIX_REMARK);
    }

    /**
     * returns a hint specific to the edit command
     */
    private static String generateEditHint() {
        Optional<String> indexHintOptional = generateIndexHint();
        if (indexHintOptional.isPresent()) {
            return indexHintOptional.get();
        }

        Optional<String> endHintOptional = generatePrefixHintBasedOnEndArgs(PREFIX_EMPTY, PREFIX_REMARK);
        if (endHintOptional.isPresent()) {
            return endHintOptional.get();
        }

        return offerHint("prefix/KEYWORD", PREFIX_EMPTY, PREFIX_REMARK);
    }

    /**
     * returns a hint specific to the find command
     */
    private static String generateFindHint() {
        Optional<String> endHintOptional = generatePrefixHintBasedOnEndArgs(PREFIX_EMPTY);

        if (endHintOptional.isPresent()) {
            return endHintOptional.get();
        }
        return offerHint("prefix/KEYWORD", PREFIX_EMPTY);
    }

    /**
     * returns a hint specific to the select and delete command
     */
    private static String generateDeleteAndSelectHint() {
        Optional<String> indexHintOptional = generateIndexHint();
        if (indexHintOptional.isPresent()) {
            return indexHintOptional.get();
        }
        return "";
    }

}