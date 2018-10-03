package seedu.addressbook.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.addressbook.TestDataHelper;
import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CreateExamCommand;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.FindCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.IncorrectCommand;
import seedu.addressbook.commands.ListCommand;
import seedu.addressbook.commands.RaisePrivilegeCommand;
import seedu.addressbook.commands.ViewAllCommand;
import seedu.addressbook.commands.ViewCommand;
import seedu.addressbook.data.exception.IllegalValueException;

import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;

import seedu.addressbook.data.tag.Tag;


public class ParserTest {

    private Parser parser;

    @Before
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void emptyInput_returnsIncorrect() {
        final String[] emptyInputs = { "", "  ", "\n  \n" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, emptyInputs);
    }

    @Test
    public void unknownCommandWord_returnsHelp() {
        final String input = "unknowncommandword arguments arguments";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    /**
     * Test 0-argument commands
     */
    @Test
    public void helpCommand_parsedCorrectly() {
        final String input = "help";
        parseAndAssertCommandType(input, HelpCommand.class);
    }

    @Test
    public void clearCommand_parsedCorrectly() {
        final String input = "clear";
        parseAndAssertCommandType(input, ClearCommand.class);
    }

    @Test
    public void listCommand_parsedCorrectly() {
        final String input = "list";
        parseAndAssertCommandType(input, ListCommand.class);
    }

    @Test
    public void exitCommand_parsedCorrectly() {
        final String input = "exit";
        parseAndAssertCommandType(input, ExitCommand.class);
    }

    /**
     * Test ingle index argument commands
     */
    @Test
    public void deleteCommand_noArgs() {
        final String[] inputs = { "delete", "delete " };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "delete notAnumber ", "delete 8*wh12", "delete 1 2 3 4 5" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void deleteCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 1;
        final String input = "delete " + testIndex;
        final DeleteCommand result = parseAndAssertCommandType(input, DeleteCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewCommand_noArgs() {
        final String[] inputs = { "view", "view " };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "view notAnumber ", "view 8*wh12", "view 1 2 3 4 5" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 2;
        final String input = "view " + testIndex;
        final ViewCommand result = parseAndAssertCommandType(input, ViewCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    @Test
    public void viewAllCommand_noArgs() {
        final String[] inputs = { "viewall", "viewall " };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_argsIsNotSingleNumber() {
        final String[] inputs = { "viewall notAnumber ", "viewall 8*wh12", "viewall 1 2 3 4 5" };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void viewAllCommand_numericArg_indexParsedCorrectly() {
        final int testIndex = 3;
        final String input = "viewall " + testIndex;
        final ViewAllCommand result = parseAndAssertCommandType(input, ViewAllCommand.class);
        assertEquals(result.getTargetIndex(), testIndex);
    }

    /**
     * Test find persons by keyword in name command
     */

    @Test
    public void findCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {
            "find",
            "find "
        };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void findCommand_validArgs_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        final String input = "find " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    @Test
    public void findCommand_duplicateKeys_parsedCorrectly() {
        final String[] keywords = { "key1", "key2", "key3" };
        final Set<String> keySet = new HashSet<>(Arrays.asList(keywords));

        // duplicate every keyword
        final String input = "find " + String.join(" ", keySet) + " " + String.join(" ", keySet);
        final FindCommand result =
                parseAndAssertCommandType(input, FindCommand.class);
        assertEquals(keySet, result.getKeywords());
    }

    /**
     * Test add person command
     */
    @Test
    public void addCommand_invalidArgs() {
        final String[] inputs = {
            "add",
            "add ",
            "add wrong args format",
            // no phone prefix
            String.format("add %s %s e/%s a/%s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE),
            // no email prefix
            String.format("add %s p/%s %s a/%s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE),
            // no address prefix
            String.format("add %s p/%s e/%s %s", Name.EXAMPLE, Phone.EXAMPLE, Email.EXAMPLE, Address.EXAMPLE)
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void addCommand_invalidPersonDataInArgs() {
        final String invalidName = "[]\\[;]";
        final String validName = Name.EXAMPLE;
        final String invalidPhoneArg = "p/not__numbers";
        final String validPhoneArg = "p/" + Phone.EXAMPLE;
        final String invalidEmailArg = "e/notAnEmail123";
        final String validEmailArg = "e/" + Email.EXAMPLE;
        final String invalidTagArg = "t/invalid_-[.tag";

        // address can be any string, so no invalid address
        final String addCommandFormatString = "add $s $s $s a/" + Address.EXAMPLE;

        // test each incorrect person data field argument individually
        final String[] inputs = {
                // invalid name
                String.format(addCommandFormatString, invalidName, validPhoneArg, validEmailArg),
                // invalid phone
                String.format(addCommandFormatString, validName, invalidPhoneArg, validEmailArg),
                // invalid email
                String.format(addCommandFormatString, validName, validPhoneArg, invalidEmailArg),
                // invalid tag
                String.format(addCommandFormatString, validName, validPhoneArg, validEmailArg) + " " + invalidTagArg
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    @Test
    public void addCommand_validPersonData_parsedCorrectly() {
        try {
            final Person testPerson = generateTestPerson();
            final String input = convertPersonToAddCommandString(testPerson);
            final AddCommand result = parseAndAssertCommandType(input, AddCommand.class);
            assertEquals(result.getPerson(), testPerson);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addCommand_duplicateTags_merged() throws IllegalValueException {
        final Person testPerson = generateTestPerson();
        String input = convertPersonToAddCommandString(testPerson);
        for (Tag tag : testPerson.getTags()) {
            // create duplicates by doubling each tag
            input += " t/" + tag.tagName;
        }

        final AddCommand result = parseAndAssertCommandType(input, AddCommand.class);
        assertEquals(result.getPerson(), testPerson);
    }

    @Test
    public void raisePrivilegeCommand_invalidArgs() {
        // no keywords
        final String[] inputs = {
            "raise",
            "raise "
        };
        final String resultMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RaisePrivilegeCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    /**
     * Test create exam command
     */
    @Test
    public void createExamCommand_invalidArgs() {
        final String[] inputs = { "createexam", "createexam ", "createexam wrong args format",
                // no exam name prefix
                String.format("createexam %s %s d/%s st/%s et/%s dt/%s", Exam.SUBJECT_NAME_EXAMPLE,
                        Exam.EXAM_NAME_EXAMPLE, Exam.EXAM_DATE_EXAMPLE, Exam.EXAM_START_TIME_EXAMPLE,
                        Exam.EXAM_END_TIME_EXAMPLE, Exam.EXAM_END_TIME_EXAMPLE),
                // no date prefix
                String.format("createexam %s n/%s %s st/%s et/%s dt/%s", Exam.SUBJECT_NAME_EXAMPLE,
                        Exam.EXAM_NAME_EXAMPLE, Exam.EXAM_DATE_EXAMPLE, Exam.EXAM_START_TIME_EXAMPLE,
                        Exam.EXAM_END_TIME_EXAMPLE, Exam.EXAM_END_TIME_EXAMPLE),
                // no start time prefix
                String.format("createexam %s n/%s d/%s %s et/%s dt/%s", Exam.SUBJECT_NAME_EXAMPLE,
                        Exam.EXAM_NAME_EXAMPLE, Exam.EXAM_DATE_EXAMPLE, Exam.EXAM_START_TIME_EXAMPLE,
                        Exam.EXAM_END_TIME_EXAMPLE, Exam.EXAM_END_TIME_EXAMPLE),
                // no end time prefix
                String.format("createexam %s n/%s d/%s st/%s %s dt/%s", Exam.SUBJECT_NAME_EXAMPLE,
                        Exam.EXAM_NAME_EXAMPLE, Exam.EXAM_DATE_EXAMPLE, Exam.EXAM_START_TIME_EXAMPLE,
                        Exam.EXAM_END_TIME_EXAMPLE, Exam.EXAM_END_TIME_EXAMPLE),
                // no details prefix
                String.format("createexam %s n/%s d/%s st/%s et/%s %s", Exam.SUBJECT_NAME_EXAMPLE,
                        Exam.EXAM_NAME_EXAMPLE, Exam.EXAM_DATE_EXAMPLE, Exam.EXAM_START_TIME_EXAMPLE,
                        Exam.EXAM_END_TIME_EXAMPLE, Exam.EXAM_END_TIME_EXAMPLE),
        };
        final String resultMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateExamCommand.MESSAGE_USAGE);
        parseAndAssertIncorrectWithMessage(resultMessage, inputs);
    }

    @Test
    public void createExamCommand_invalidExamDataInArgs() {
        final String invalidDateArg = "d/not__numbers";
        final String validDateArg = "d/" + Exam.EXAM_DATE_EXAMPLE;
        final String invalidStartTimeArg = "st/not__numbers";
        final String validStartTimeArg = "st/" + Exam.EXAM_START_TIME_EXAMPLE;
        final String invalidEndTimeArg = "et/not__numbers";
        final String validEndTimeArg = "et/" + Exam.EXAM_END_TIME_EXAMPLE;
        final String invalidEndTimeIntervalArg = "et/0800";


        // subject name and details can be any string, so no invalid address
        final String createExamCommandFormatString = "createexam " + Exam.SUBJECT_NAME_EXAMPLE
                + " n/" + Exam.EXAM_NAME_EXAMPLE
                + " %s %s %s dt/" + Exam.EXAM_DETAILS_EXAMPLE;

        // test each incorrect person data field argument individually
        final String[] inputs = {
                // invalid date
                String.format(createExamCommandFormatString, invalidDateArg, validStartTimeArg, validEndTimeArg),
                // invalid start time
                String.format(createExamCommandFormatString, validDateArg, invalidStartTimeArg, validEndTimeArg),
                // invalid end time
                String.format(createExamCommandFormatString, validDateArg, validStartTimeArg, invalidEndTimeArg),
                // invalid time interval
                String.format(createExamCommandFormatString, validDateArg, validStartTimeArg,
                        invalidEndTimeIntervalArg),
        };
        for (String input : inputs) {
            parseAndAssertCommandType(input, IncorrectCommand.class);
        }
    }

    @Test
    public void createExamCommand_validExamData_parsedCorrectly() {
        try {
            final Exam testExam = generateTestExam();
            final String input = convertExamToCreateExamCommandString(testExam);
            final CreateExamCommand result = parseAndAssertCommandType(input, CreateExamCommand.class);
            assertEquals(result.getExam(), testExam);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }

    /** **/
    private static Person generateTestPerson() throws IllegalValueException {
        try {
            return new Person(
                new Name(Name.EXAMPLE),
                new Phone(Phone.EXAMPLE, true),
                new Email(Email.EXAMPLE, false),
                new Address(Address.EXAMPLE, true),
                new HashSet<>(Arrays.asList(new Tag("tag1"), new Tag("tag2"), new Tag("tag3")))
            );
        } catch (IllegalValueException ive) {
            throw new IllegalValueException("test person data should be valid by definition");
        }
    }
    /** **/
    private static String convertPersonToAddCommandString(ReadOnlyPerson person) {
        TestDataHelper helper = new TestDataHelper();
        String phoneField = helper.getPrefix(person.getPhone()) + person.getPhone();
        String emailField = helper.getPrefix(person.getEmail()) + person.getEmail();
        String addressField = helper.getPrefix(person.getAddress()) + person.getAddress();

        String addCommand = "add " + person.getName().fullName + phoneField + emailField + addressField;
        for (Tag tag : person.getTags()) {
            addCommand += " t/" + tag.tagName;
        }
        return addCommand;
    }
    /** **/
    private static Exam generateTestExam() throws IllegalValueException {
        try {
            return new Exam(Exam.SUBJECT_NAME_EXAMPLE,
                    Exam.EXAM_NAME_EXAMPLE,
                    Exam.EXAM_DATE_EXAMPLE,
                    Exam.EXAM_START_TIME_EXAMPLE,
                    Exam.EXAM_END_TIME_EXAMPLE,
                    Exam.EXAM_DETAILS_EXAMPLE,
                    false
            );
        } catch (IllegalValueException ive) {
            throw new IllegalValueException("test exam data should be valid by definition");
        }
    }
    /** **/
    private static String convertExamToCreateExamCommandString(Exam exam) {
        TestDataHelper helper = new TestDataHelper();
        String examNameField = helper.getPrefix(exam.getExamName(), exam.isPrivate()) + exam.getExamName();
        String dateField = helper.getDatePrefix(exam.getExamDate()) + helper.removeSpecialChar(exam.getExamDate());
        String startTimeField = helper.getStartTimePrefix(exam.getExamStartTime())
                + helper.removeSpecialChar(exam.getExamStartTime());
        String endTimeField = helper.getEndTimePrefix(exam.getExamEndTime())
                + helper.removeSpecialChar(exam.getExamEndTime());
        String detailsField = helper.getDetailsPrefix(exam.getExamDetails()) + exam.getExamDetails();

        String createExamCommand = "createexam " + exam.getSubjectName()
                + examNameField + dateField + startTimeField
                + endTimeField + detailsField;
        System.out.println(createExamCommand);
        return createExamCommand;
    }
    /**
     * Utility methods
     */

    /**
     * Asserts that parsing the given inputs will return IncorrectCommand with the given feedback message.
     */
    private void parseAndAssertIncorrectWithMessage(String feedbackMessage, String... inputs) {
        for (String input : inputs) {
            final IncorrectCommand result = parseAndAssertCommandType(input, IncorrectCommand.class);

            assertEquals(result.feedbackToUser, feedbackMessage);
        }
    }

    /**
     * Utility method for parsing input and asserting the class/type of the returned command object.
     *
     * @param input to be parsed
     * @param expectedCommandClass expected class of returned command
     * @return the parsed command object
     */
    private <T extends Command> T parseAndAssertCommandType(String input, Class<T> expectedCommandClass) {
        final Command result = parser.parseCommand(input);
        assertTrue(result.getClass().isAssignableFrom(expectedCommandClass));
        return (T) result;
    }


}
