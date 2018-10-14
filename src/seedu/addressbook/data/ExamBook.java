package seedu.addressbook.data;

import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.ReadOnlyExam;
import seedu.addressbook.data.person.UniqueExamList;
import seedu.addressbook.data.person.UniqueExamList.DuplicateExamException;
import seedu.addressbook.data.person.UniqueExamList.ExamNotFoundException;

/**
 * Represents the entire exams book. Contains the data of the exams book.
 */
public class ExamBook {

    private final UniqueExamList examList;

    /**
     * Creates an empty exam book.
     */
    public ExamBook() {
        examList = new UniqueExamList();
    }

    /**
     * Constructs an exam book with the given data.
     *
     * @param examList external changes to this will not affect this exam book
     */

    public ExamBook(UniqueExamList examList) {
        this.examList = new UniqueExamList(examList);
    }

    public static ExamBook empty() {
        return new ExamBook();
    }

    /**
     * Adds a exam to the exam book.
     *
     * @throws DuplicateExamException if an exam with equivalent data already exists.
     */
    public void addExam(Exam toAdd) throws DuplicateExamException {
        examList.add(toAdd);
    }

    /**
     * Checks if an equivalent exam exists in the exam book.
     */
    public boolean containsExam(ReadOnlyExam key) {
        return examList.contains(key);
    }

    /**
     * Removes the equivalent exam from the exam book.
     *
     * @throws ExamNotFoundException if no such Exam could be found.
     */
    public void removeExam(ReadOnlyExam toRemove) throws ExamNotFoundException {
        examList.remove(toRemove);
    }

    /**
     * Clears all exams from the exam book.
     */
    public void clear() {
        examList.clear();
    }

    /**
     * Defensively copied UniqueExamList of all exam in the exam book at the time of the call.
     */
    public UniqueExamList getAllExam() {
        return new UniqueExamList(examList);
    }

    /**
     * Edits a exam in the ExamBook
     *
     * @throws ExamNotFoundException if no such Exam could be found.
     */
    public void editExam(ReadOnlyExam oldExam, Exam examToChange)
            throws ExamNotFoundException, DuplicateExamException {
        if (examList.contains(oldExam)) {
            examList.add(examToChange);
            examList.remove(oldExam);
        } else {
            throw new ExamNotFoundException();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExamBook // instanceof handles nulls
                && this.examList.equals(((ExamBook) other).examList));
    }

    @Override
    public int hashCode() {
        return examList.hashCode();
    }
}
