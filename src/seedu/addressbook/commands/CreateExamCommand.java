package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.UniqueExamList;

/**
 * Creates an exam in the exam book.
 */
public class CreateExamCommand extends Command {
    public static final String COMMAND_WORD = "createexam";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a exam to the exam book. "
            + "Exams can be marked private by prepending 'p' to the prefix of the exam name.\n\t"
            + "Parameters: SUBJECTNAME [p]n/EXAMNAME  d/EXAMDATE st/EXAMSTARTTIME et/EXAMENDTIME dt/EXAMDETAILS\n\t"
            + "Example: " + COMMAND_WORD
            + " Mathematics n/Math Mid-terms d/01122018 st/0900 et/1200 dt/At MPSH";

    public static final String MESSAGE_SUCCESS = "New exam added : %1$s";
    public static final String MESSAGE_DUPLICATE_EXAM = "This exam already exists in the exam book!";

    private final Exam toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public CreateExamCommand(String subjectName,
                             String examName, String examDate, String examStartTime,
                             String examEndTime, String examDetails,
                             boolean isExamPrivate) throws IllegalValueException {
        this.toAdd = new Exam(subjectName, examName, examDate, examStartTime, examEndTime, examDetails, isExamPrivate);
    }

    public Exam getExam() {
        return toAdd;
    }

    @Override
    public CommandResult execute() {
        try {
            examBook.addExam(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueExamList.DuplicateExamException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_EXAM);
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
