package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList;

/**
 *  Lists down the dates where the person's attendance has been taken.
 */
public class ViewAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "viewAtten";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Views the attendance of a student. \n"
            + "Parameters: n/STUDENTINDEXn\t"
            + "Example: " + COMMAND_WORD + " "
            + "n/Neow Bo Kai";

    public static final String MESSAGE_SUCCESS = "Attendance for student: ";
    public static final String MESSAGE_DUPLICATE_EXAM = "No such student";

    // Constructor
    public ViewAttendanceCommand(String targetIndex) {
        super(Integer.parseInt(targetIndex));
    }

    @Override
    public CommandResult execute() {
        try {
            Person person = addressBook.findPerson(getTargetPerson());
            final String output = person.viewAttendanceMethod();
            return new CommandResult("Attendance of " + getTargetIndex() + ":" + output);

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
