package seedu.addressbook.data.person;

import java.util.HashMap;
import java.util.Map;

/**
 *  Represents a map that links dates(kay) to attendance(value)
 */

public class Attendance implements Printable {

    private Map<String, Boolean> attendance_map = new HashMap<>();
    /**
    // Method to add attendance
     */
    public void addAttendance(String currentDate, String isPresent) {
        boolean isPresent_b = false;
        if (isPresent.equals("1")) {
            isPresent_b = true;
        }
        attendance_map.put(currentDate, isPresent_b);
    }
    /**
    // Method to reiterate person's attendance
    */
    public String viewAttendance() {
        String output = "";
        for (Map.Entry entry : attendance_map.entrySet()) {
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
                && this.attendance_map.equals(((Attendance) other).attendance_map)); // state check
    }
}

