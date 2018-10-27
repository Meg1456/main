package seedu.addressbook;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.ui.Formatter;

public class FormatterTest {
    private static Formatter formatter = new Formatter();
    /** A platform independent line separator. */
    private static final String NEWLINE = System.lineSeparator();


    @Test
    public void formatStringChain() {
        assertEquals(" A" + NEWLINE + " B" + NEWLINE + " C" + NEWLINE,
                formatter.format("A", "B", "C"));
        assertEquals(" A" + NEWLINE + " B" + NEWLINE + " C" + NEWLINE + " D" + NEWLINE,
                formatter.format("A", "B\nC", "D"));
    }

    @Test
    public void formatPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Person toBePrinted = helper.adam();
        final String expected = " \t1. Adam Brown" + NEWLINE + " " + NEWLINE + " Email: adam@gmail.com" + NEWLINE + " "
                + NEWLINE + " " + NEWLINE + "  Tags: [tag1][tag2]" + NEWLINE
                + " " + NEWLINE;
        List<Person> persons = new ArrayList<>();
        persons.add(toBePrinted);
        assertEquals(expected, formatter.format(persons));
    }

    @Test
    public void formatPersons() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Person> persons = helper.generatePersonList(false);
        final String personFormat = " \t%1$d. Person %1$d" + NEWLINE + " Phone: %1$d" + NEWLINE
                + " Email: %1$d@email" + NEWLINE
                + " Address: House of %1$d" + NEWLINE + " " + NEWLINE
                + "  Tags: [tag%2$d][tag%3$d]" + NEWLINE;
        String expected = String.format(personFormat, 1, 1, 2) + " " + NEWLINE;
        assertEquals(expected, formatter.format(persons));

        //List of 3 people, all fields non-private
        persons = helper.generatePersonList(false, false , false);
        expected = String.format(personFormat, 1, 1, 2) + String.format(personFormat, 2, 2, 3)
                + String.format(personFormat, 3, 4, 3) + " " + NEWLINE;
        assertEquals(expected, formatter.format(persons));

        //List of 3 people, all fields non-private*
        persons = helper.generatePersonList(false, true , false);
        expected = String.format(personFormat, 1, 1, 2)
                + " \t2. Person 2" + NEWLINE + " " + NEWLINE + " " + NEWLINE
                + " " + NEWLINE + " " + NEWLINE + "  Tags: [tag2][tag3]" + NEWLINE
                + String.format(personFormat, 3, 4, 3)
                + " " + NEWLINE;
        assertEquals(expected, formatter.format(persons));
    }

    @Test
    public void formatAllExam() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Exam> exams = helper.generateExamList(false, true);
        final String examFormat = " \t%1$d. %2$sExam: Exam %1$d Subject %1$d 01-02-2018 10:00 12:00 Held in %1$d 0"
                + NEWLINE;
        String expected = String.format(examFormat, 1, "") + String.format(examFormat, 2, "private ") + " " + NEWLINE;
        assertEquals(expected, formatter.formatExam(exams));

    }
}
