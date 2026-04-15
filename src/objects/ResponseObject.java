package src.objects;

public class ResponseObject {
    public boolean success;
    public String message;

    public ResponseObject(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
