package seedu.addressbook.commands;

import java.util.List;

/**
 * Shows help instructions.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Shows program usage instructions.\n\t"
            + "Example: " + COMMAND_WORD;

    /**
     * Creates the help message feedback to the user based on what commands they can access
     * */
    public static String makeHelpMessage() {
        String message = "";
        List<Command> allowedCommands = privilege.getAllowedCommands();
        boolean isFirstString = true;
        for (Command command: allowedCommands) {
            if (!isFirstString) {
                message += '\n';
            }
            isFirstString = false;
            message += command.getCommandUsageMessage();
        }

        return message;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(makeHelpMessage());
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
