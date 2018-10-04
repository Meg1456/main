package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Fees;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;

/**
 * Adds fees to a respective person
 */
public class AddFeesCommand extends Command {

    public static final String COMMAND_WORD = "addfees";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds fees to an existing person "
            + "in the address book. Fees are automatically marked as private.\n\t"
            + "Parameters: INDEX FEES \n\t"
            + "Example: " + COMMAND_WORD + " 1 3264.90";

    public static final String MESSAGE_SUCCESS = "Fees updated: %1$s";

    private Fees fees;

    /**
     * Use a constructor to update the fees values in AddressBook.
     */
    public AddFeesCommand(int index, String fees) throws IllegalValueException {
        super(index);
        this.fees = new Fees(fees);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            Person person = addressBook.findPerson(target);
            person.setFees(fees);
            return new CommandResult(String.format(MESSAGE_SUCCESS, target));
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }

}
