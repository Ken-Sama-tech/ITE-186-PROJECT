package src.objects;

public class AuthenticateUser {
    public boolean success;
    public String message;
    public User user;

    public AuthenticateUser(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }
}