package seedu.addressbook.privilege;

import java.util.List;
import java.util.Optional;

import seedu.addressbook.commands.Command;
import seedu.addressbook.data.account.Account;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.privilege.user.AdminUser;
import seedu.addressbook.privilege.user.BasicUser;
import seedu.addressbook.privilege.user.TutorUser;
import seedu.addressbook.privilege.user.User;

/** Represents the privilege level of the user */
public class Privilege {
    /** Enums for the different privilege levels*/
    private enum PrivilegeLevels {
        BASIC(new BasicUser()),
        TUTOR(new TutorUser()),
        ADMIN(new AdminUser());

        private final User userType;
        PrivilegeLevels(User userType) {
            this.userType = userType;
        }

        public User getUserType() {
            return userType;
        }
    }

    private User user;
    private Optional<ReadOnlyPerson> myPerson = Optional.empty();

    public Privilege() {
        user = PrivilegeLevels.BASIC.getUserType();
    }

    public Privilege(User user) {
        this.user = user;
    }

    /**
     * Signals that an operation would have changed the properties of the currently logged in user.
     */
    public static class SelfTargetingException extends Exception {
        protected SelfTargetingException() {
            super("Operation would result in errors due effects on logged in user");
        }
    }

    public static Privilege getPrivilegeFromString (String userType) {
        for (PrivilegeLevels p : PrivilegeLevels.values()) {
            if (p.toString().equals(userType.toUpperCase())) {
                return new Privilege(p.getUserType());
            }
        }
        return null;
    }

    public void raiseToAccountLevel(Account account) {
        user = account.getPrivilege().getUser();
    }

    public void raiseToTutor() {
        user = PrivilegeLevels.TUTOR.getUserType();
    }

    public void raiseToAdmin() {
        user = PrivilegeLevels.ADMIN.getUserType();
    }

    public Optional<ReadOnlyPerson> getMyPerson() {
        return myPerson;
    }

    public void setMyPerson(Person myPerson) {
        this.myPerson = Optional.of(myPerson);
    }

    /**
     * Resets the privilege to base (No myPerson assigned, Basic User).
     */
    public void resetPrivilege() {
        clearMyPerson();
        raiseToBasic();
    }

    public boolean isBase() {
        return (user.equals(PrivilegeLevels.BASIC.getUserType()) && !myPerson.isPresent());
    }

    private void clearMyPerson() {
        myPerson = Optional.empty();
    }

    private void raiseToBasic() {
        user = PrivilegeLevels.BASIC.getUserType();
    }

    public String getLevelAsString() {
        return user.getPrivilegeLevelAsString();
    }

    public User getUser() {
        return user;
    }

    public List<Command> getAllowedCommands() {
        return user.getAllowedCommands();
    }

    public boolean isAllowedCommand(Command command) {
        return user.isAllowedCommand(command);
    }

    /**
     * Checks if the target is the currently logged in user
     * @throws SelfTargetingException if the above is true
     */
    public void checkTargetIsSelf(ReadOnlyPerson person) throws SelfTargetingException {
        if (isTargetSelf(person)) {
            throw new SelfTargetingException();
        }
    }

    private boolean isTargetSelf(ReadOnlyPerson person) {
        return myPerson.isPresent() && person.equals(myPerson.get());
    }

    public String getRequiredPrivilegeAsString(Command command) {
        // TODO Fix this potato code
        String requiredPrivilege = "PRIVILEGE NOT FOUND";
        for (PrivilegeLevels p : PrivilegeLevels.values()) {
            if (p.getUserType().isAllowedCommand(command)) {
                requiredPrivilege = p.getUserType().getPrivilegeLevelAsString();
            }
        }
        return requiredPrivilege;
    }
}
