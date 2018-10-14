package seedu.addressbook.commands;

import seedu.addressbook.logic.Logic;

/**
 * Raises the current privilege to Admin
 * Checks if the password supplied is correct
 */
public class RaisePrivilegeCommand extends Command {

    public static final String COMMAND_WORD = "raise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Raises your privilege level to Admin, requires master password.\n\t"
            + "Parameters: PASSWORD\n\t"
            + "Example: " + COMMAND_WORD + " default_password";

    public static final String MESSAGE_WRONG_PASSWORD = "Wrong password entered";
    public static final String MESSAGE_SUCCESS = "Privilege changed to %1$s";

    private String password;

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public RaisePrivilegeCommand() {
    }

    public RaisePrivilegeCommand (String password) {
        this.password = password;
    }

    private void validatePassword() throws Logic.WrongPasswordEnteredException {
        if (!password.equals(addressBook.getMasterPassword())) {
            throw new Logic.WrongPasswordEnteredException();
        }
    }

    @Override
    public CommandResult execute() {
        try {
            validatePassword();
            privilege.raiseToAdmin();
            return new CommandResult(String.format(MESSAGE_SUCCESS, privilege.getLevelAsString()));
        } catch (Logic.WrongPasswordEnteredException wpe) {
            return new CommandResult(MESSAGE_WRONG_PASSWORD);
        }
    }

    @Override
    public Category getCategory() {
        return Category.PRIVILEGE;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }

}
