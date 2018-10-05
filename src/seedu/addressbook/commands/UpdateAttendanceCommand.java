package seedu.addressbook.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList;


/**
 *  Clocks in the date and time where the student is present.
 */
public class UpdateAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "attendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Updates the attendance of a student. \n"
            + "Parameters: INDEXOFPERSON att/ATTENDANCE \n\t"
            + "Example: " + COMMAND_WORD + " "
            + "1 att/1 or 0"; // '1' will be read at true, '0' will be read as false

    public static final String MESSAGE_SUCCESS = "Attendance updated : %1$s";
    public static final String MESSAGE_DUPLICATE_EXAM = "Attendance has already been taken";
    private String isPresent;

    // TODO shift date tp Attendance object and make date adjustable
    // TODO If no date specified, default to current date
    private String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());



    // Constructor
    public UpdateAttendanceCommand(String targetIndex, String isPresent) {
        super(Integer.parseInt(targetIndex));
        this.isPresent = isPresent;
    }

    // TODO Ensure target index is an integer

    @Override
    public CommandResult execute() {
        try {
            Person person = addressBook.findPerson(getTargetPerson());
            person.updateAttendanceMethod(currentDate, isPresent);
            return new CommandResult(String.format(MESSAGE_SUCCESS, getTargetIndex()));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (UniquePersonList.PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
