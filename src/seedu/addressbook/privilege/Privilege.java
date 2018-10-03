package seedu.addressbook.privilege;

import java.util.List;

import seedu.addressbook.commands.Command;
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
    public Privilege() {
        user = PrivilegeLevels.BASIC.getUserType();
    }

    public Privilege(User user) {
        this.user = user;
    }

    public void raiseToTutor() {
        user = PrivilegeLevels.TUTOR.getUserType();
    }

    public void raiseToAdmin() {
        user = PrivilegeLevels.ADMIN.getUserType();
    }

    public String getLevelAsString() {
        return user.getPrivilegeLevel();
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
    public String getRequiredPrivilegeAsString(Command command) {
        // TODO Fix this potato code
        String requiredPrivilege = "PRIVILEGE NOT FOUND";
        for (PrivilegeLevels p : PrivilegeLevels.values()) {
            if (p.getUserType().isAllowedCommand(command)) {
                requiredPrivilege = p.getUserType().getPrivilegeLevel();
            }
        }

        return requiredPrivilege;
    }
}
