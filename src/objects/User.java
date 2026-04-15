package src.objects;

import java.util.Comparator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import src.enums.UserRole;

public class User {
    public String name;
    public char[] password;
    public UserRole role;
    public String studentId;
    private Set<LocalDate> records = new HashSet<LocalDate>();

    public User(String studentId, String name, char[] password, UserRole role) {
        this.name = name;
        this.studentId = studentId;
        this.password = password.clone();
        this.role = role;
    }

    public Set<String> getRecords() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return records.stream()
                .sorted(Comparator.reverseOrder())
                .map(date -> date.format(formatter))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void markAttendance() {
        LocalDate now = LocalDate.now();
        records.add(now);
    }

    public void setRecord(String date) {
        records.add(LocalDate.parse(date));
    }

}