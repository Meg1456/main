package seedu.addressbook.commands;

import static seedu.addressbook.commands.AddExamCommand.MESSAGE_DUPLICATE_EXAM;

import java.util.Map;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.UniqueExamList;

/**
 * Edits an exam identified using its last displayed index in the exam book.
 */
public class EditExamCommand extends Command {

    public static final String COMMAND_WORD = "editexam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Edits the exam identified by the index number used in the last exams listing. "
            + "Each exam field is optional but order must be followed.\n\t"
            + "Parameters: INDEX [p/PRIVATE_STATUS] [e/EXAM_NAME] [s/SUBJECT_NAME] "
            + "[d/EXAM_DATE] [st/EXAM_START_TIME]\n\t\t"
            + "[et/EXAM_END_TIME] [dt/EXAM_DETAILS]\n\t"
            + "PRIVATE_STATUS is denoted by \"y\" or \"n\".\n\t"
            + "Example: " + COMMAND_WORD + " 1 e/Math Mid Terms 2018 d/07-06-2018";

    public static final String MESSAGE_EDIT_EXAM_SUCCESS = "Edited Exam: %1$s\n to%3$s Exam: %2$s";

    private Map<String, String> changedDetails;

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public EditExamCommand() {
    }

    public EditExamCommand(int targetVisibleIndex, Map<String, String> changedDetails) {
        super(targetVisibleIndex);
        this.changedDetails = changedDetails;
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyExam target = getTargetExam();
            Exam toEdit = new Exam(target, changedDetails);
            examBook.editExam(target, toEdit);
            return new CommandResult(String.format(MESSAGE_EDIT_EXAM_SUCCESS, target,
                    toEdit, toEdit.isPrivate() ? " private" : ""));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_EXAM_DISPLAYED_INDEX);
        } catch (UniqueExamList.ExamNotFoundException enfe) {
            return new CommandResult(Messages.MESSAGE_EXAM_NOT_IN_EXAMBOOK);
        } catch (UniqueExamList.DuplicateExamException dee) {
            return new CommandResult(MESSAGE_DUPLICATE_EXAM);
        } catch (IllegalValueException ive) {
            return new CommandResult(ive.getMessage());
        }
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public Category getCategory() {
        return Category.EXAM;
    }

    @Override
    public String getCommandUsageMessage() {
        return MESSAGE_USAGE;
    }
}
