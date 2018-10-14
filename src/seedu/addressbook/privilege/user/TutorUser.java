package seedu.addressbook.privilege.user;

import java.util.Arrays;
import java.util.List;

import seedu.addressbook.commands.AddCommand;
import seedu.addressbook.commands.AddExamCommand;
import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.DeleteCommand;
import seedu.addressbook.commands.DeleteExamCommand;
import seedu.addressbook.commands.EditExamCommand;
import seedu.addressbook.commands.ExamsListCommand;

/**
 * Represents a user with Tutor access
 * */
public class TutorUser extends BasicUser {
    private static List<Command> newAllowedCommand = Arrays.asList(
            new AddCommand(),
            new DeleteCommand(),
            new AddExamCommand(),
            new ExamsListCommand(),
            new DeleteExamCommand(),
            new EditExamCommand()
    );

    public TutorUser() {
        super();
        addAllowedCommands(newAllowedCommand);
        setCurrentLevel(PrivilegeLevel.Tutor);
        sortCommands();
    }
}
