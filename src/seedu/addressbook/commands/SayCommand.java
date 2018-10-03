package seedu.addressbook.commands;

/**
 * Clears the address book.
 */
public class SayCommand extends Command {

    public static final String COMMAND_WORD = "say";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Says your current privilege level.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_FORMAT = "Your privilege is %1$s";

    @Override
    public CommandResult execute() {
        //return new CommandResult(String.format(MESSAGE_FORMAT, addressBook.getMasterPassword()));
        return new CommandResult(String.format(MESSAGE_FORMAT, privilege.getLevelAsString()));
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
