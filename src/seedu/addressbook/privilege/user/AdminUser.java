package seedu.addressbook.privilege.user;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.commands.ChangePasswordCommand;
import seedu.addressbook.commands.ClearCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.ViewAllCommand;

/**
 * Represents a user with Admin access
 * */
public class AdminUser extends TutorUser {
    private static List<Command> newAllowedCommand = Arrays.asList(
            new ClearCommand(),
            new ViewAllCommand(),
            new ChangePasswordCommand()
    );

    public AdminUser() {
        super();
        allowedCommands.addAll(newAllowedCommand);
        this.currentLevel = PrivilegeLevel.Admin;
        sortCommands();
    }

    /**
     * Overrides the check with true to allow compatibility with "new" commands that were
     * unaccounted for (ie commands made by teammates)
     * */
    @Override
    public boolean isAllowedCommand(Command command) {
        return true;
    }
}
