package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;

import java.util.Collections;
import java.util.List;

import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.storage.StorageFile;

/**
 * This class holds the commonly used assertions of commands.
 * */
public class CommandAssertions {
    private static StorageFile saveFile;
    private static AddressBook addressBook;
    private static Logic logic;
    private static ExamBook examBook;

    public static void setData(StorageFile saveFile, AddressBook addressBook, Logic logic) {
        CommandAssertions.saveFile = saveFile;
        CommandAssertions.addressBook = addressBook;
        CommandAssertions.logic = logic;
    }

    public static void setData(StorageFile saveFile, AddressBook addressBook, Logic logic, ExamBook examBook) {
        setData(saveFile, addressBook, logic);
        CommandAssertions.examBook = examBook;
    }

    /**
     * Executes the command and confirms that the result message is correct.
     * Both the 'address book' and the 'last shown list' are expected to be empty.
     * @see #assertCommandBehavior(String, String, AddressBook, boolean, List)
     */
    public static void assertCommandBehavior(String inputCommand, String expectedMessage) throws Exception {
        assertCommandBehavior(inputCommand, expectedMessage, AddressBook.empty(), false, Collections.emptyList());
    }
    /**
     * Executes the command and confirms that the result message is correct and
     * Assumes the command does not write to file
     *      * @see #assertCommandBehavior(String, String, AddressBook, boolean, List, boolean)
     */
    public static void assertCommandBehavior(String inputCommand,
                                      String expectedMessage,
                                      AddressBook expectedAddressBook,
                                      boolean isRelevantPersonsExpected,
                                      List<? extends ReadOnlyPerson> lastShownList) throws Exception {
        assertCommandBehavior(inputCommand,
                expectedMessage,
                expectedAddressBook,
                isRelevantPersonsExpected,
                lastShownList,
                false);
    }
    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal address book data are same as those in the {@code expectedAddressBook} <br>
     *      - the internal 'last shown list' matches the {@code expectedLastList} <br>
     *
     *      if the command will write to file
     *      - the storage file content matches data in {@code expectedAddressBook} <br>
     */
    public static void assertCommandBehavior(String inputCommand,
                                      String expectedMessage,
                                      AddressBook expectedAddressBook,
                                      boolean isRelevantPersonsExpected,
                                      List<? extends ReadOnlyPerson> lastShownList,
                                      boolean writesToFile) throws Exception {
        // If we need to test if the command writes to file correctly
        // Injects the saveFile object to check
        if (writesToFile) {
            logic.setStorage(saveFile);
        }
        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);
        assertEquals(r.getRelevantPersons().isPresent(), isRelevantPersonsExpected);
        if (isRelevantPersonsExpected) {
            assertEquals(lastShownList, r.getRelevantPersons().get());
        }

        //Confirm the state of data is as expected
        assertEquals(expectedAddressBook, addressBook);
        assertEquals(lastShownList, logic.getLastShownList());
        if (writesToFile) {
            assertEquals(addressBook, saveFile.load());
        }
    }


    /**
     * Executes the command and confirms that the result message is correct and
     * also confirms that the following three parts of the Logic object's state are as expected:<br>
     *      - the internal exam book data are same as those in the {@code expectedExamBook} <br>
     *
     *      if the command will write to file
     *      - the storage file content matches data in {@code expectedExamBook} <br>
     */
    public static void assertCommandBehavior(String inputCommand,
                                      String expectedMessage,
                                      ExamBook expectedExamBook,
                                      boolean writesToFile) throws Exception {
        // If we need to test if the command writes to file correctly
        // Injects the saveFile object to check
        if (writesToFile) {
            logic.setStorage(saveFile);
        }
        //Execute the command
        CommandResult r = logic.execute(inputCommand);

        //Confirm the result contains the right data
        assertEquals(expectedMessage, r.feedbackToUser);

        //Confirm the state of data is as expected
        assertEquals(expectedExamBook, examBook);
        if (writesToFile) {
            assertEquals(examBook, saveFile.loadExam());
        }
    }

}
