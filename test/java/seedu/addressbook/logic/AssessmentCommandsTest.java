package seedu.addressbook.logic;

import static seedu.addressbook.common.Messages.MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_STATISTICS_DISPLAYED_INDEX;
import static seedu.addressbook.logic.CommandAssertions.assertCommandBehavior;
import static seedu.addressbook.logic.CommandAssertions.assertInvalidIndexBehaviorForCommand;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.TestDataHelper;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.assessment.AddAssessmentCommand;
import seedu.addressbook.commands.assessment.DeleteAssessmentCommand;
import seedu.addressbook.commands.assessment.DeleteStatisticsCommand;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.StatisticsBook;
import seedu.addressbook.data.person.Assessment;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.privilege.Privilege;
import seedu.addressbook.privilege.user.AdminUser;
import seedu.addressbook.storage.StorageFile;
import seedu.addressbook.stubs.StorageStub;

/**
 * For testing of Assessment-related Commands
 */
public class AssessmentCommandsTest {

    /**
     *  See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private AddressBook addressBook;
    private StatisticsBook statisticBook;
    private Logic logic;
    //private Logic logic; Temporary left as local variable

    @Before
    public void setUp() throws Exception {
        StorageFile saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath(),
                saveFolder.newFile("testExamFile.txt").getPath(),
                saveFolder.newFile("testStatisticsFile.txt").getPath());
        StorageStub stubFile = new StorageStub(saveFolder.newFile("testStubFile.txt").getPath(),
                saveFolder.newFile("testStubExamFile.txt").getPath(),
                saveFolder.newFile("testStubStatisticsFile.txt").getPath());

        addressBook = new AddressBook();
        ExamBook examBook = new ExamBook();
        statisticBook = new StatisticsBook();
        // Privilege set to admin to allow all commands.
        // Privilege restrictions are tested separately under PrivilegeTest.
        Privilege privilege = new Privilege(new AdminUser());

        saveFile.save(addressBook);
        saveFile.saveStatistics(statisticBook);
        Logic logic = new Logic(stubFile, addressBook, examBook, statisticBook, privilege);
        CommandAssertions.setData(saveFile, addressBook, logic, examBook, statisticBook);
    }

    @Test
    public void executeAddAssessmentSuccessful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Assessment toBeAdded = helper.assess();
        AddressBook expected = new AddressBook();
        expected.addAssessment(toBeAdded);
        List<? extends ReadOnlyPerson> dummyList = expected.getAllPersons().immutableListView();

        // execute command and verify result
        assertCommandBehavior(helper.generateAddAssessment(toBeAdded),
                String.format(AddAssessmentCommand.MESSAGE_SUCCESS, toBeAdded), expected, false,
                dummyList);
    }

    @Test
    public void executeAddAssessmentDuplicateNotAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Assessment toBeAdded = helper.assess();
        AddressBook expected = new AddressBook();
        expected.addAssessment(toBeAdded);
        List<? extends ReadOnlyPerson> dummyList = expected.getAllPersons().immutableListView();

        // setup starting state
        addressBook.addAssessment(toBeAdded); // statistic already in internal statistic book

        // execute command and verify result
        assertCommandBehavior(helper.generateAddAssessment(toBeAdded),
                AddAssessmentCommand.MESSAGE_DUPLICATE_ASSESSMENT, expected, false, dummyList);
    }

    @Test
    public void executeDeleteAssessmentInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssessmentCommand.MESSAGE_USAGE);
        assertCommandBehavior("deleteassess ", expectedMessage);
        assertCommandBehavior("deleteassess arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteAssessmentInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("deleteassess", MESSAGE_INVALID_ASSESSMENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeListAssessmentsShowsAllAssessments() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        AddressBook expected = helper.generateAddressBook(false, true);
        Assessment assessment = new Assessment("Math final");
        expected.addAssessment(assessment);
        addressBook.addAssessment(assessment);
        Assessment assessment2 = new Assessment("CG2271 Midterm");
        expected.addAssessment(assessment2);
        addressBook.addAssessment(assessment2);
        List<? extends Assessment> expectedList = List.of(assessment, assessment2);

        // prepare address book state
        helper.addToAddressBook(addressBook, false, true);

        assertCommandBehavior("listassess",
                Command.getMessageForAssessmentListShownSummary(expectedList),
                "",
                expected,
                false,
                Collections.emptyList(),
                true,
                expectedList,
                false);
    }

    //    @Test
    //    public void executeAddAssignmentStatisticsSuccessful() throws Exception {
    //        // setup expectations
    //        TestDataHelper helper = new TestDataHelper();
    //        AssignmentStatistics toBeAdded = helper.stat();
    //        StatisticsBook expected = new StatisticsBook();
    //        expected.addStatistic(toBeAdded);
    //
    //        // execute command and verify result
    //        Assessment assessment = new Assessment("Spanish Quiz");
    //        addressBook.addAssessment(assessment);
    //        Person person1 = helper.adam();
    //        Grades grade = new Grades(100);
    //        assessment.addGrade(person1, grade);
    //
    //        assertCommandBehavior(helper.generateAddAssignmentStatistics(),
    //                String.format(AddAssignmentStatistics.MESSAGE_SUCCESS, toBeAdded),
    //                expected, false);
    //    }
    //
    //    @Test
    //    public void executeAddAssignmentStatisticsDuplicateNotAllowed() throws Exception {
    //        setup expectations
    //        TestDataHelper helper = new TestDataHelper();
    //        AssignmentStatistics toBeAdded = helper.stat();
    //        StatisticsBook expected = new StatisticsBook();
    //        expected.addStatistic(toBeAdded);
    //
    //        setup starting state
    //        statisticBook.addStatistic(toBeAdded); // statistic already in internal statistic book
    //
    //       execute command and verify result
    //       assertCommandBehavior(helper.generateAddAssignmentStatistics(),
    //       AddAssignmentStatistics.MESSAGE_DUPLICATE_STATISTIC, expected, false);
    // }

    @Test
    public void executeDeleteStatisticsInvalidArgsFormat() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStatisticsCommand.MESSAGE_USAGE);
        assertCommandBehavior("deletestatistics ", expectedMessage);
        assertCommandBehavior("deletestatistics arg not number", expectedMessage);
    }

    @Test
    public void executeDeleteStatisticsInvalidIndex() throws Exception {
        assertInvalidIndexBehaviorForCommand("deletestatistics", MESSAGE_INVALID_STATISTICS_DISPLAYED_INDEX);
    }

}
