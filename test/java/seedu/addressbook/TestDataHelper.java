package seedu.addressbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.junit.Test;
import seedu.addressbook.data.AddressBook;


import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Exam;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.AssignmentStatistics;

import seedu.addressbook.data.tag.Tag;

/**
 * A utility class to generate test data.
 */
public class TestDataHelper {
    /** Test person for testing**/
    public Person adam() throws Exception {
        Name name = new Name("Adam Brown");
        Phone privatePhone = new Phone("111111", true);
        Email email = new Email("adam@gmail.com", false);
        Address privateAddress = new Address("111, alpha street", true);
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Set<Tag> tags = new HashSet<>(Arrays.asList(tag1, tag2));
        return new Person(name, privatePhone, email, privateAddress, tags);
    }

    /** Test exam for testing**/
    public Exam math() throws Exception {
        String subjectName = "Mathematics";
        String examName = "Math Mid-Terms 2018";
        String date = "06062018";
        String startTime = "0900";
        String endTime = "1200";
        String details = "Held in MPSH";
        Boolean isPrivate = false;
        return new Exam(subjectName, examName, date, startTime, endTime, details, isPrivate);
    }

    /**
     * Generates a valid person using the given seed.
     * Running this function with the same parameter values guarantees the returned person will have the same state.
     * Each unique seed will generate a unique Person object.
     *
     * @param seed used to generate the person data field values
     * @param isAllFieldsPrivate determines if private-able fields (phone, email, address) will be private
     */
    public Person generatePerson(int seed, boolean isAllFieldsPrivate) throws Exception {
        return new Person(
                new Name("Person " + seed),
                new Phone("" + Math.abs(seed), isAllFieldsPrivate),
                new Email(seed + "@email", isAllFieldsPrivate),
                new Address("House of " + seed, isAllFieldsPrivate),
                new HashSet<>(Arrays.asList(new Tag("tag" + Math.abs(seed)), new Tag("tag" + Math.abs(seed + 1))))
        );
    }

    /**Generated the prefix for the field **/
    public String getPrefix (Phone phone) {
        return (phone.isPrivate() ? " pp/" : " p/");
    }

    public String getPrefix (Email email) {
        return (email.isPrivate() ? " pe/" : " e/");
    }

    public String getPrefix (Address address) {
        return (address.isPrivate() ? " pa/" : " a/");
    }

    public String getPrefix (String name, Boolean isPrivate) {
        return (isPrivate ? " pn/" : " n/");
    }

    public String getDatePrefix (String value) {
        return (" d/");
    }

    public String getStartTimePrefix (String value) {
        return (" st/");
    }

    public String getEndTimePrefix (String value) {
        return (" et/");
    }

    public String getDetailsPrefix (String value) {
        return (" dt/");
    }

    /** Generates the correct add command based on the person given */
    public String generateAddCommand(Person p) {
        StringJoiner cmd = new StringJoiner(" ");
        String phoneField = getPrefix(p.getPhone()) + p.getPhone();
        String emailField = getPrefix(p.getEmail()) + p.getEmail();
        String addressField = getPrefix(p.getAddress()) + p.getAddress();

        cmd.add("add");
        cmd.add(p.getName().toString());
        cmd.add(phoneField);
        cmd.add(emailField);
        cmd.add(addressField);

        Set<Tag> tags = p.getTags();
        for (Tag t: tags) {
            cmd.add("t/" + t.tagName);
        }
        return cmd.toString();
    }

    /** Generates the correct createexam command based on the exam given */
    public String generateCreateExamCommand(Exam e) {
        StringJoiner cmd = new StringJoiner(" ");
        String subjectField = e.getSubjectName();
        String examNameField = getPrefix(e.getExamName(), e.isPrivate()) + e.getExamName();
        String dateField = getDatePrefix(e.getExamDate()) + removeSpecialChar(e.getExamDate());
        String startTimeField = getStartTimePrefix(e.getExamStartTime())
                + removeSpecialChar(e.getExamStartTime());
        String endTimeField = getEndTimePrefix(e.getExamEndTime()) + removeSpecialChar(e.getExamEndTime());
        String detailsField = getDetailsPrefix(e.getExamDetails()) + e.getExamDetails();


        cmd.add("createexam");
        cmd.add(subjectField);
        cmd.add(examNameField);
        cmd.add(dateField);
        cmd.add(startTimeField);
        cmd.add(endTimeField);
        cmd.add(detailsField);
        return cmd.toString();
    }

    /**
     * Removes special characters in a string for exam values
     */
    public String removeSpecialChar(String value) {
        String newValue = value.replaceAll("[^a-zA-Z0-9!@\\.,]", "");
        return newValue;
    }


    /**
     * Generates an AddressBook with auto-generated persons.
     * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
     *                          private.
     */
    public AddressBook generateAddressBook(Boolean... isPrivateStatuses) throws Exception {
        AddressBook addressBook = new AddressBook();
        addToAddressBook(addressBook, isPrivateStatuses);
        return addressBook;
    }

    /**
     * Generates an AddressBook based on the list of Persons given.
     */
    public AddressBook generateAddressBook(List<Person> persons) throws Exception {
        AddressBook addressBook = new AddressBook();
        addToAddressBook(addressBook, persons);
        return addressBook;
    }

    /**
     * Adds auto-generated Person objects to the given AddressBook
     * @param addressBook The AddressBook to which the Persons will be added
     * @param isPrivateStatuses flags to indicate if all contact details of generated persons should be set to
     *                          private.
     */
    public void addToAddressBook(AddressBook addressBook, Boolean... isPrivateStatuses) throws Exception {
        addToAddressBook(addressBook, generatePersonList(isPrivateStatuses));
    }

    /**
     * Adds the given list of Persons to the given AddressBook
     */
    public void addToAddressBook(AddressBook addressBook, List<Person> personsToAdd) throws Exception {
        for (Person p: personsToAdd) {
            addressBook.addPerson(p);
        }
    }

    /**
     * Creates a list of Persons based on the give Person objects.
     */
    public List<Person> generatePersonList(Person... persons) throws Exception {
        List<Person> personList = new ArrayList<>();
        for (Person p: persons) {
            personList.add(p);
        }
        return personList;
    }

    /**
     * Generates a list of Persons based on the flags.
     * @param isPrivateStatuses flags to indicate if all contact details of respective persons should be set to
     *                          private.
     */
    public List<Person> generatePersonList(Boolean... isPrivateStatuses) throws Exception {
        List<Person> persons = new ArrayList<>();
        int i = 1;
        for (Boolean p: isPrivateStatuses) {
            persons.add(generatePerson(i++, p));
        }
        return persons;
    }

    /**
     * Generates a Person object with given name. Other fields will have some dummy values.
     */
    public Person generatePersonWithName(String name) throws Exception {
        return new Person(
                new Name(name),
                new Phone("1", false),
                new Email("1@email", false),
                new Address("House of 1", false),
                Collections.singleton(new Tag("tag"))
        );
    }


}
