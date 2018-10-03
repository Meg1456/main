package seedu.addressbook.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.addressbook.common.Messages.MESSAGE_INSUFFICIENT_PRIVILEGE;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.RaisePrivilegeCommand;
import seedu.addressbook.commands.SayCommand;
import seedu.addressbook.data.AddressBook;
import seedu.addressbook.data.ExamBook;
import seedu.addressbook.parser.Parser;
import seedu.addressbook.privilege.Privilege;
import seedu.addressbook.privilege.user.AdminUser;
import seedu.addressbook.privilege.user.BasicUser;
import seedu.addressbook.storage.StorageFile;
import seedu.addressbook.stubs.StorageStub;

public class PrivilegeTest {
    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();
    private AddressBook addressBook = new AddressBook();
    private Privilege privilege = new Privilege();
    @Before
    public void setUp() throws Exception {
        StorageStub stubFile;
        StorageFile saveFile;
        ExamBook examBook = new ExamBook();
        saveFile = new StorageFile(saveFolder.newFile("testSaveFile.txt").getPath());
        stubFile = new StorageStub(saveFolder.newFile("testStubFile.txt").getPath());
        saveFile.save(addressBook);

        Logic logic = new Logic(stubFile, addressBook, examBook, privilege);
        CommandAssertions.setData(saveFile, addressBook, logic);
    }


    @Test
    public void executeClearInsufficientPrivilege() throws Exception {
        String expectedMessage = String.format(MESSAGE_INSUFFICIENT_PRIVILEGE, "Admin", "Basic");
        CommandAssertions.assertCommandBehavior("clear",
                expectedMessage,
                AddressBook.empty(),
                false,
                Collections.emptyList(),
                true);

        privilege.raiseToTutor();
        expectedMessage = String.format(MESSAGE_INSUFFICIENT_PRIVILEGE, "Admin", "Tutor");
        CommandAssertions.assertCommandBehavior("clear",
                expectedMessage,
                AddressBook.empty(),
                false,
                Collections.emptyList(),
                true);
    }


    @Test
    public void executeSay() throws Exception {
        CommandAssertions.assertCommandBehavior("say",
                String.format(SayCommand.MESSAGE_FORMAT, privilege.getLevelAsString()));

        privilege.raiseToTutor();
        CommandAssertions.assertCommandBehavior("say",
                String.format(SayCommand.MESSAGE_FORMAT, privilege.getLevelAsString()));

        privilege.raiseToAdmin();
        CommandAssertions.assertCommandBehavior("say",
                String.format(SayCommand.MESSAGE_FORMAT, privilege.getLevelAsString()));
    }
    @Test
    public void assertDefaultPassword() {
        final String defaultPassword = "default_pw";
        assertEquals(addressBook.getMasterPassword(), defaultPassword);
    }


    @Test
    public void executeRaisePrivilegeInvalidArg() throws Exception {
        final String[] inputs = {"raise", "raise ", "raise arg1 arg2" , "raise arg1 arg2 arg3"};
        final String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RaisePrivilegeCommand.MESSAGE_USAGE);

        for (String input: inputs) {
            CommandAssertions.assertCommandBehavior(input, expectedMessage);
        }
        assertEquals(privilege.getUser(), new BasicUser());
    }

    @Test
    public void executeRaisePrivilegeWrongPassword() throws Exception {
        String expectedMessage = RaisePrivilegeCommand.MESSAGE_WRONG_PASSWORD;
        CommandAssertions.assertCommandBehavior("raise wrong_password", expectedMessage);
        assertEquals(privilege.getUser(), new BasicUser());
    }

    @Test
    public void executeRaisePrivilegeSuccessDefaultPassword() throws Exception {
        String defaultPassword = AddressBook.DEFAULT_MASTER_PASSWORD;
        CommandAssertions.assertCommandBehavior("raise " + defaultPassword,
                String.format(RaisePrivilegeCommand.MESSAGE_SUCCESS, new AdminUser().getPrivilegeLevel()));
        assertEquals(privilege.getUser(), new AdminUser());
    }

    @Test
    public void executeRaisePrivilegeSuccessChangedPassword() throws Exception {
        addressBook.setMasterPassword("new_Password");
        CommandAssertions.assertCommandBehavior("raise new_Password",
                String.format(RaisePrivilegeCommand.MESSAGE_SUCCESS, new AdminUser().getPrivilegeLevel()));
        assertEquals(privilege.getUser(), new AdminUser());
    }

    @Test
    public void executeAdminCommandsInsufficientPrivilege() throws Exception {
        final String[] inputs = {"clear", "viewall 1", "change default_pw new_pw" };
        assertCommandsInsufficientPrivilege(inputs);
        privilege.raiseToTutor();
        assertCommandsInsufficientPrivilege(inputs);
    }

    @Test
    public void executeTutorCommandsInsufficientPrivilege() throws Exception {
        final String[] inputs = {"add Valid Name p/12345 e/valid@e.mail a/valid, address ",
            "delete 1"};
        assertCommandsInsufficientPrivilege(inputs);
    }

    /** Asserts that the given command strings cannot be executed using the current privilege level*/
    private void assertCommandsInsufficientPrivilege(String[] inputs) throws Exception {
        Parser parser = new Parser();
        for (String input: inputs) {
            Command command = parser.parseCommand(input);
            CommandAssertions.assertCommandBehavior(input, String.format(MESSAGE_INSUFFICIENT_PRIVILEGE,
                    privilege.getRequiredPrivilegeAsString(command), privilege.getLevelAsString()));
        }
    }

}
