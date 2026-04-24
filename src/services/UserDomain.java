package src.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import src.enums.UserRole;

import src.objects.AuthenticateUser;
import src.objects.ResponseObject;
import src.objects.User;

public class UserDomain {
    private static final HashMap<String, User> users = new HashMap<String, User>();

    public UserDomain() {
        if (users.size() == 0 || users.get("1") == null) {
            users.put("1", new User("1", "admin", "admin".toCharArray(), UserRole.ADMIN));
        }
    }

    public ResponseObject createUser(String studentID, String name, char[] password, UserRole role) {

        if (studentID.isEmpty()) {
            return new ResponseObject(false, "Username cannot be empty");
        }

        if (String.valueOf(password).isEmpty()) {
            return new ResponseObject(false, "Password cannot be empty");
        }

        if (users.containsKey(studentID))
            return new ResponseObject(false, "User already exist");

        users.put(studentID, new User(studentID, name, password, role));

        return new ResponseObject(true, "User created");
    }

    public AuthenticateUser authenticateUser(String studentId, char[] password) {

        if (studentId.isEmpty() || String.valueOf(password).isEmpty())
            return new AuthenticateUser(false, "Required fields cannot be empty", null);

        if (!users.containsKey(studentId)) {
            return new AuthenticateUser(false, "User does not exist", null);
        }

        User user = users.get(studentId);

        if (!String.valueOf(user.password).equals(String.valueOf(password))) {
            return new AuthenticateUser(false, "Incorrect password", null);
        }

        return new AuthenticateUser(true, "Login sucess", users.get(studentId));
    }

    public static List<User> getUsers() {
        List<User> listOfUser = new LinkedList<User>();

        users.forEach((k, v) -> {
            listOfUser.add(v);
        });

        return listOfUser;
    }

    public static ResponseObject deleteUser(String studentId) {

        if (studentId.equals("1"))
            return new ResponseObject(false, "Cannot delete default admin account");

        users.remove(studentId);

        return new ResponseObject(true, "User deleted");
    }

    public static ResponseObject editUser(String studentID, String name, char[] password, UserRole role) {

        if (studentID.isEmpty()) {
            return new ResponseObject(false, "Student ID cannot be empty");
        }

        if (!users.containsKey(studentID)) {
            return new ResponseObject(false, "User does not exist");
        }

        if (name == null || name.isEmpty()) {
            return new ResponseObject(false, "Name cannot be empty");
        }

        if (password == null || String.valueOf(password).isEmpty()) {
            return new ResponseObject(false, "Password cannot be empty");
        }

        users.replace(studentID, new User(studentID, name, password, role));

        return new ResponseObject(true, "User updated successfully");
    }
}
