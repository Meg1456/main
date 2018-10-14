package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.UniqueExamList.ExamNotFoundException;

/**
 * Deletes a exam identified using its last displayed index from the exam book.
 */
public class DeleteExamCommand extends Command {

    public static final String COMMAND_WORD = "deleteexam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Deletes the exam identified by the index number used in the last exams listing.\n\t"
            + "Parameters: INDEX\n\t"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EXAM_SUCCESS = "Deleted Exam: %1$s";

    /**
     * Constructor used for Privileges
     * Command constructed has no functionality
     * */
    public DeleteExamCommand() {
    }

    public DeleteExamCommand(int targetVisibleIndex) {
        super(targetVisibleIndex);
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyExam target = getTargetExam();
            examBook.removeExam(target);
            return new CommandResult(String.format(MESSAGE_DELETE_EXAM_SUCCESS, target));
        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_EXAM_DISPLAYED_INDEX);
        } catch (ExamNotFoundException enfe) {
            return new CommandResult(Messages.MESSAGE_EXAM_NOT_IN_EXAMBOOK);
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
