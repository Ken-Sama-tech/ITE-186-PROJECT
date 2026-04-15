package src.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import src.enums.UserRole;
import src.objects.User;

public class TestData {
    public static Set<String> dumpTestAttendanceRecord() {
        Set<String> foo = new HashSet<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.of(2025, 1, 1);

        for (int i = 0; i < 50; i++) {
            foo.add(start.plusDays(i).format(formatter));
        }

        return foo;
    }

    public static HashMap<String, User> dumpTestUsers() {
        HashMap<String, User> users = new HashMap<>();

        for (int i = 2; i <= 50; i++) {
            users.put("" + i,
                    new User("" + i, "ken" + i, "l".toCharArray(), UserRole.USER));
        }

        return users;
    }
}
