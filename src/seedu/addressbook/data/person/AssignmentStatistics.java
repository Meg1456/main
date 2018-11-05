package seedu.addressbook.data.person;

import java.util.Objects;

import seedu.addressbook.data.exception.IllegalValueException;
/**
 * Represents a statistic in the statistics book.
 */
public class AssignmentStatistics implements Printable {

    public static final String EXAM_NAME_EXAMPLE = "Math Midterms";
    public static final String AVERAGESCORE_EXAMPLE = "21.5";
    public static final String TOTALEXAMTAKERS_EXAMPLE = "84"; //Number of students who attended test
    public static final String MAXMIN_EXAMAPLE = "27 10"; //maximum score and minimum score

    private String examName;
    private double averageScore;
    private int totalExamTakers;
    private double maxScore;
    private double minScore;


    public AssignmentStatistics() {}

    /**
     * Validates given statistics.
     *
     */
    public AssignmentStatistics(String examName, double averageScore, int totalExamTakers, double maxScore,
                                double minScore) {

        this.examName = examName.trim();
        this.averageScore = averageScore;
        this.totalExamTakers = totalExamTakers;
        this.maxScore = maxScore;
        this.minScore = minScore;
    }

    @Override
    public String toString() {
        return examName + " Average Score: " + averageScore + " Total Exam Takers: "
                + totalExamTakers + " Max Score: " + maxScore + " Min Score: " + minScore;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentStatistics // instanceof handles nulls
                && this.examName.equals(((AssignmentStatistics) other).examName)); // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(averageScore, totalExamTakers, maxScore, minScore);
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        return "Exam: " + examName + " " + averageScore + " " + totalExamTakers + " " + maxScore + " " + minScore;
    }


    public String getExamName() {
        return examName;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getTotalExamTakers() {
        return totalExamTakers;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public double getMinScore() {
        return minScore;
    }

}
