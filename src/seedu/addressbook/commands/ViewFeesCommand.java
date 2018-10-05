package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;


/**
 * Shows the fees owed with respect to the person identified in the last displayed index.
 * It's a private detail which is showed.
 */
public class ViewFeesCommand extends Command {

    public static final String COMMAND_WORD = "viewfees";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Shows the fees of the person identified by the index number used in the last person listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEWFEE_PERSON_SUCCESS = "Name: %1$s";


    public ViewFeesCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public ViewFeesCommand() {
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            addressBook.findPerson(target);
            return new CommandResult(String.format(MESSAGE_VIEWFEE_PERSON_SUCCESS, target.getAsTextShowFee()));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }
    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public Category getCategory() {
        return Category.DETAILS;
    }
    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
