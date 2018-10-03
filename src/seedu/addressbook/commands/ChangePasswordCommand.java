package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.logic.Logic;

/**
 * Changes the master password to a new one.
 * Checks that the old password given is correct.
 */
public class ChangePasswordCommand extends Command {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Changes master password to a new specified one\n\t "
            + "Parameters: OLD_PASSWORD NEW_PASSWORD\n\t"
            + "Example: " + COMMAND_WORD + " old_password sudo1234";
    public static final String MESSAGE_SUCCESS = "Password changed to %s";
    public static final String MESSAGE_WRONG_PASSWORD = "Wrong password entered";
    public static final String MESSAGE_WRONG_NUMBER_ARGUMENTS =
            "Wrong number of arguments, expected %d, actual %d\n %s";
    private static final int REQUIRED_ARGUMENTS = 2;

    private String newPassword;
    private String oldPassword;

    public ChangePasswordCommand(String[] arguments) throws IllegalValueException {
        if (arguments.length != REQUIRED_ARGUMENTS) {
            throw new IllegalValueException(String.format(MESSAGE_WRONG_NUMBER_ARGUMENTS,
                    REQUIRED_ARGUMENTS, arguments.length, MESSAGE_USAGE));
        }

        this.oldPassword = arguments[0];
        this.newPassword = arguments[1];
    }
    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public ChangePasswordCommand() {
    }

    @Override
    public CommandResult execute() {
        try {
            validatePassword();
            addressBook.setMasterPassword(newPassword);
            return new CommandResult(String.format(MESSAGE_SUCCESS, newPassword));
        } catch (Logic.WrongPasswordEnteredException wpe) {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        }
    }

    private void validatePassword() throws Logic.WrongPasswordEnteredException {
        if (!oldPassword.equals(addressBook.getMasterPassword())) {
            throw new Logic.WrongPasswordEnteredException();
        }
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }

    @Override
    public Category getCategory() {
        return Category.PRIVILEGE;
    }

    @Override
    public boolean isMutating() {
        // TODO maybe make it so that it only saves the password portion of the XML
        return true;
    }
}
