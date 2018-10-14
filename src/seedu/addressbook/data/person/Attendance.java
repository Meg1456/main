package seedu.addressbook.data.person;

import java.util.HashMap;
import java.util.Map;

/**
 *  Represents a map that links dates(kay) to attendance(value)
 */

public class Attendance implements Printable {

    private Map<String, Boolean> attendanceMap = new HashMap<>();
    /**
    // Method to add attendance
     */
    public void addAttendance(String currentDate, String isPresent) {
        boolean isPresentNow = "1".equals(isPresent);
        attendanceMap.put(currentDate, isPresentNow);
    }
    /**
    // Method to reiterate person's attendance
    */
    public String viewAttendance() {
        String output = "";
        for (Map.Entry entry : attendanceMap.entrySet()) {
            output += entry.getKey() + " -> " + entry.getValue() + "\n";
        }
        return output;
    }

    @Override
    public String getPrintableString(boolean showPrivate) {
        return "{}";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Attendance // instanceof handles nulls
                && this.attendanceMap.equals(((Attendance) other).attendanceMap)); // state check
    }
}

