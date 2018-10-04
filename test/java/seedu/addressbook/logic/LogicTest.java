package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.addressbook.logic.CommandAssertions.assertCommandBehavior;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.TestDataHelper;
import seedu.addressbook.commands.AddAssignmentStatistics;
import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.AddFeesCommand;
import seedu.addressbook.commands.ChangePasswordCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CreateExamCommand;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.ExitCommand;
import seedu.addressbook.commands.FindCommand;
import seedu.addressbook.commands.HelpCommand;
import seedu.addressbook.commands.ViewAllCommand;
import seedu.addressbook.commands.ViewCommand;
import seedu.addressbook.commands.ViewFeesCommand;

import seedu.addressbook.common.Messages;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.AssignmentStatistics;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Exam;
//import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;

import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;

import seedu.addressbook.privilege.Privilege;
import seedu.addressbook.privilege.user.AdminUser;
import seedu.addressbook.storage.StorageFile;

import seedu.addressbook.stubs.StorageStub;

public class LogicTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private StorageFile saveFile;
    private AddressBook addressBook;
    private Privilege privilege;
    private ExamBook examBook;
    private StatisticsBook statisticBook;
    private Logic logic;

    @Before
    public void setUp() throws Exception {
        StorageStub stubFile;
        saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath(),
                saveFolder.newFile("testExamFile.txt").getPath(),
                saveFolder.newFile("testStatisticsFile.txt").getPath());
        stubFile = new StorageStub(saveFolder.newFile("testStubFile.txt").getPath(),
                saveFolder.newFile("testStubExamFile.txt").getPath(),
                saveFolder.newFile("testStubStatisticsFile.txt").getPath());

        addressBook = new AddressBook();
        examBook = new ExamBook();
        statisticBook = new StatisticsBook();

        // Privilege set to admin to allow all commands.
        // Privilege restrictions are tested separately under PrivilegeTest.
        privilege = new Privilege(new AdminUser());

        logic = new Logic(stubFile, addressBook, examBook, statisticBook, privilege);
        CommandAssertions.setData(saveFile, addressBook, logic, examBook, statisticBook);
        saveFile.saveExam(examBook);
        saveFile.saveStatistics(statisticBook);
        saveFile.save(addressBook);
    }

    @Test
    public void constructor() {
        //Constructor is called in the setup() method which executes before every test, no need to call it here again.

        //Confirm the last shown list is empty
        assertEquals(Collections.emptyList(), logic.getLastShownList());
    }

    @Test
    public void defaultConstructor() throws Exception {
        // Verifies if addressbook.txt is loadable
        logic = new Logic();
        //Confirm the last shown list is empty
        assertEquals(Collections.emptyList(), logic.getLastShownList());
    }

    @Test
    public void execute_invalid() throws Exception {
        String invalidCommand = "       ";
        assertCommandBehavior(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }


    @Test
    public void execute_unknownCommandWord() throws Exception {
        String unknownCommand = "uicfhmowqewca";
        assertCommandBehavior(unknownCommand, HelpCommand.makeHelpMessage());
    }

    @Test
    public void executeHelp() throws Exception {
        assertCommandBehavior("help", HelpCommand.makeHelpMessage());

        privilege.raiseToTutor();
        assertCommandBehavior("help", HelpCommand.makeHelpMessage());

        privilege.raiseToAdmin();
        assertCommandBehavior("help", HelpCommand.makeHelpMessage());
    }

    @Test
    public void executeExit() throws Exception {
        assertCommandBehavior("exit", ExitCommand.MESSAGE_EXIT_ACKNOWEDGEMENT);
    }


    @Test
    public void executeClearSuccess() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        // TODO: refactor this elsewhere
        logic.setStorage(saveFile);
        // generates the 3 test people and execute the add command
        for (int i = 1; i <= 3; ++i) {
            final Person testPerson = helper.generatePerson(i, true);
            addressBook.addPerson(testPerson);
            logic.execute(helper.generateAddCommand(testPerson));
        }
        assertCommandBehavior("clear",
                ClearCommand.MESSAGE_SUCCESS,
                AddressBook.empty(),
                false,
                Collections.emptyList(),
                true);
    }

    @Test
    public void executeAddInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        assertCommandBehavior(
                "add wrong args wrong args", expectedMessage);
        assertCommandBehavior(
                "add Valid Name 12345 e/valid@email.butNoPhonePrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 valid@email.butNoPrefix a/valid, address", expectedMessage);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@email.butNoAddressPrefix valid, address", expectedMessage);
    }

    @Test
    public void executeAddInvalidPersonData() throws Exception {
        assertCommandBehavior(
                "add []\\[;] p/12345 e/valid@e.mail a/valid, address", Name.MESSAGE_NAME_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/not_numbers e/valid@e.mail a/valid, address", Phone.MESSAGE_PHONE_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/notAnEmail a/valid, address", Email.MESSAGE_EMAIL_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/12345 e/valid@e.mail a/#$%#@#What Am I?", Address.MESSAGE_ADDRESS_CONSTRAINTS);
        assertCommandBehavior(
                "add Valid Name p/1234 e/valid@e.mail a/valid, address t/invalid_-[.tag", Tag.MESSAGE_TAG_CONSTRAINTS);

    }

    @Test
    public void executeAddSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        AddressBook expected = new AddressBook();
        expected.addPerson(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded),
                expected,
                false,
                Collections.emptyList(),
                true);

    }

    @Test
    public void executeAddFeesCommandInvalidData() throws Exception {
        assertCommandBehavior(
                "addfees 2 1.111", Fees.MESSAGE_FEES_CONSTRAINTS);
    }

    @Test
    public void executeAddFeesSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expected = helper.generateAddressBook(threePersons);
        expected.findPerson(p2).setFees(helper.fees());


        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        assertCommandBehavior(helper.generateAddFeesCommand(),
                String.format(AddFeesCommand.MESSAGE_SUCCESS, p2),
                expected,
                false,
                threePersons);
    }

    @Test
    public void executeAddDuplicateNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Person toBeAdded = helper.adam();
        AddressBook expected = new AddressBook();
        expected.addPerson(toBeAdded);

        // setup starting state
        addressBook.addPerson(toBeAdded); // person already in internal address book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateAddCommand(toBeAdded),
                AddCommand.MESSAGE_DUPLICATE_PERSON,
                expected,
                false,
                Collections.emptyList());

    }

    @Test
    public void executeCreateExamSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Exam toBeAdded = helper.math();
        ExamBook expected = new ExamBook();
        expected.addExam(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(
                helper.generateCreateExamCommand(toBeAdded),
                String.format(CreateExamCommand.MESSAGE_SUCCESS, toBeAdded),
                expected, true);
    }

    @Test
    public void executeCreateDuplicateExamNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Exam toBeAdded = helper.math();
        ExamBook expected = new ExamBook();
        expected.addExam(toBeAdded);

        // setup starting state
        examBook.addExam(toBeAdded); // person already in internal exam book

        // execute command and verify result
        assertCommandBehavior(
                helper.generateCreateExamCommand(toBeAdded),
                CreateExamCommand.MESSAGE_DUPLICATE_EXAM,
                expected,
                true);
    }

    @Test
    public void executeAddAssignmentStatisticsSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        AssignmentStatistics toBeAdded = helper.stat();
        StatisticsBook expected = new StatisticsBook();
        expected.addStatistic(toBeAdded);

        // execute command and verify result
        assertCommandBehavior(helper.generateAddAssignmentStatistics(toBeAdded),
                String.format(AddAssignmentStatistics.MESSAGE_SUCCESS, toBeAdded),
                expected, false);

    }

    @Test
    public void executeAddAssignmentStatisticsDuplicateNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        AssignmentStatistics toBeAdded = helper.stat();
        StatisticsBook expected = new StatisticsBook();
        expected.addStatistic(toBeAdded);

        // setup starting state
        statisticBook.addStatistic(toBeAdded); // statistic already in internal statistic book

        // execute command and verify result
        assertCommandBehavior(helper.generateAddAssignmentStatistics(toBeAdded),
                AddAssignmentStatistics.MESSAGE_DUPLICATE_STATISTIC, expected, false);
    }

    @Test
    public void executeListShowsAllPersons() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expected = helper.generateAddressBook(false, true);
        List<? extends ReadOnlyPerson> expectedList = expected.getAllPersons().immutableListView();

        // prepare address book state
        helper.addToAddressBook(addressBook, false, true);

        assertCommandBehavior("list",
                Command.getMessageForPersonListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeViewInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);
        assertCommandBehavior("view ", expectedMessage);
        assertCommandBehavior("view arg not number", expectedMessage);
    }

    @Test
    public void executeViewInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("view");
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the last shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list based on visible index.
     */
    private void assertInvalidIndexBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Person> lastShownList = helper.generatePersonList(false, true);

        logic.setLastShownList(lastShownList);

        assertCommandBehavior(commandWord + " -1", expectedMessage,
                AddressBook.empty(), false, lastShownList);

        assertCommandBehavior(commandWord + " 0", expectedMessage,
                AddressBook.empty(), false, lastShownList);

        assertCommandBehavior(commandWord + " 3", expectedMessage,
                AddressBook.empty(), false, lastShownList);

    }

    @Test
    public void executeViewOnlyShowsNonPrivate() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expected = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);

        assertCommandBehavior("view 1",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p1.getAsTextHidePrivate()),
                expected,
                false,
                lastShownList);

        assertCommandBehavior("view 2",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p2.getAsTextHidePrivate()),
                expected,
                false,
                lastShownList);
    }

    @Test
    public void executeTryToViewMissingPersonErrorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);

        AddressBook expected = new AddressBook();
        expected.addPerson(p2);

        addressBook.addPerson(p2);
        logic.setLastShownList(lastShownList);

        assertCommandBehavior("view 1",
                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expected,
                false,
                lastShownList);
    }

    @Test
    public void executeViewAllInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAllCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewall ", expectedMessage);
        assertCommandBehavior("viewall arg not number", expectedMessage);
    }

    @Test
    public void executeViewAllInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("viewall");
    }

    @Test
    public void executeViewAllAlsoShowsPrivate() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, true);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);
        AddressBook expected = helper.generateAddressBook(lastShownList);
        helper.addToAddressBook(addressBook, lastShownList);

        logic.setLastShownList(lastShownList);

        assertCommandBehavior("viewall 1",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p1.getAsTextShowAll()),
                expected,
                false,
                lastShownList);

        assertCommandBehavior("viewall 2",
                String.format(ViewCommand.MESSAGE_VIEW_PERSON_DETAILS, p2.getAsTextShowAll()),
                expected,
                false,
                lastShownList);
    }

    @Test
    public void executeTryToViewAllPersonMissingInAddressBookErrorMessage() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, false);
        List<Person> lastShownList = helper.generatePersonList(p1, p2);

        AddressBook expected = new AddressBook();
        expected.addPerson(p1);

        addressBook.addPerson(p1);
        logic.setLastShownList(lastShownList);

        assertCommandBehavior("viewall 2",
                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expected,
                false,
                lastShownList);
    }

    @Test
    public void executeViewFeesCommandSuccessful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expected = helper.generateAddressBook(threePersons);
        expected.findPerson(p2).setFees(helper.fees());

        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("viewfees 2",
                String.format(ViewFeesCommand.MESSAGE_VIEWFEE_PERSON_SUCCESS, p2.getAsTextShowFee()),
                expected,
                false,
                threePersons,
                false);
    }

    @Test
    public void executeViewFeesCommandInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("viewfees");
    }

    @Test
    public void executeViewFeesInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewFeesCommand.MESSAGE_USAGE);
        assertCommandBehavior("viewfees ", expectedMessage);
        assertCommandBehavior("viewfees arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertCommandBehavior("delete ", expectedMessage);
        assertCommandBehavior("delete arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("delete");
    }

    @Test
    public void executeDeleteRemovesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expected = helper.generateAddressBook(threePersons);
        expected.removePerson(p2);


        helper.addToAddressBook(addressBook, threePersons);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("delete 2",
                String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, p2),
                expected,
                false,
                threePersons,
                true);
    }

    @Test
    public void executeDeleteMissingInAddressBook() throws Exception {

        TestDataHelper helper = new TestDataHelper();
        Person p1 = helper.generatePerson(1, false);
        Person p2 = helper.generatePerson(2, true);
        Person p3 = helper.generatePerson(3, true);

        List<Person> threePersons = helper.generatePersonList(p1, p2, p3);

        AddressBook expected = helper.generateAddressBook(threePersons);
        expected.removePerson(p2);

        helper.addToAddressBook(addressBook, threePersons);
        addressBook.removePerson(p2);
        logic.setLastShownList(threePersons);

        assertCommandBehavior("delete 2",
                Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK,
                expected,
                false,
                threePersons);
    }

    @Test
    public void executeFindInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandBehavior("find ", expectedMessage);
    }

    @Test
    public void executeFindOnlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p1 = helper.generatePersonWithName("KE Y");
        Person p2 = helper.generatePersonWithName("KEYKEYKEY sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expected = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeFindIsCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla KEY bla bceofeia");
        Person p1 = helper.generatePersonWithName("key key");
        Person p2 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expected = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY",
                Command.getMessageForPersonListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeFindMatchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person pTarget1 = helper.generatePersonWithName("bla bla KEY bla");
        Person pTarget2 = helper.generatePersonWithName("bla rAnDoM bla bceofeia");
        Person p1 = helper.generatePersonWithName("key key");
        Person p2 = helper.generatePersonWithName("KEy sduauo");

        List<Person> fourPersons = helper.generatePersonList(p1, pTarget1, p2, pTarget2);
        AddressBook expected = helper.generateAddressBook(fourPersons);
        List<Person> expectedList = helper.generatePersonList(pTarget1, pTarget2);
        helper.addToAddressBook(addressBook, fourPersons);

        assertCommandBehavior("find KEY rAnDoM",
                Command.getMessageForPersonListShownSummary(expectedList),
                expected,
                true,
                expectedList);
    }

    @Test
    public void executeChangePasswordInvalidArguments() throws Exception {
        final String initialPassword = addressBook.getMasterPassword();
        final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangePasswordCommand.MESSAGE_USAGE);
        assertCommandBehavior("change", expectedMessage);
        assertCommandBehavior("change ", expectedMessage);
        assertEquals(addressBook.getMasterPassword(), initialPassword);
    }


    @Test
    public void executeChangePasswordInvalidArgumentNumber() throws Exception {
        final String initialPassword = addressBook.getMasterPassword();
        final String expectedMessage = ChangePasswordCommand.MESSAGE_WRONG_NUMBER_ARGUMENTS;
        final int requiredArguments = 2;
        int actualArguments = 1;
        assertCommandBehavior("change default_pw", String.format(expectedMessage,
                requiredArguments, actualArguments, ChangePasswordCommand.MESSAGE_USAGE));

        actualArguments = 3;
        assertCommandBehavior("change default_pw new_pw extra_arg",
                String.format(expectedMessage, requiredArguments, actualArguments,
                        ChangePasswordCommand.MESSAGE_USAGE));
        assertEquals(addressBook.getMasterPassword(), initialPassword);
    }

    @Test
    public void executeChangePasswordWrongPassword() throws Exception {
        final String initialPassword = addressBook.getMasterPassword();
        String expectedMessage = ChangePasswordCommand.MESSAGE_WRONG_PASSWORD;
        assertCommandBehavior("change wrong_password new_password", expectedMessage);
        assertCommandBehavior("change default_password1 new_password", expectedMessage);
        assertCommandBehavior("change Default_password new_password", expectedMessage);
        assertEquals(addressBook.getMasterPassword(), initialPassword);
    }

    @Test
    public void executeChangePasswordSuccess() throws Exception {
        final String expectedMessage = ChangePasswordCommand.MESSAGE_SUCCESS;
        final String commandFormat = "change %s %s";
        String oldPassword = addressBook.getMasterPassword();
        String newPassword = "new_password";
        String commandInput = String.format(commandFormat, oldPassword, newPassword);
        assertCommandBehavior(commandInput,
                String.format(expectedMessage, newPassword));
        assertEquals(addressBook.getMasterPassword(), newPassword);

        oldPassword = addressBook.getMasterPassword();
        newPassword = "another_new_password";
        commandInput = String.format(commandFormat, oldPassword, newPassword);
        assertCommandBehavior(commandInput,
                String.format(expectedMessage, newPassword));
        assertEquals(addressBook.getMasterPassword(), newPassword);
    }
}
