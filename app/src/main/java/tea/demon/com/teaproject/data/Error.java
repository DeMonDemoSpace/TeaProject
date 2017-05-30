package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/22.
 */

public class Error {
    private String message;
    private int status_code;

    public Error(String message, int status_code) {
        this.message = message;
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus_code() {
        return status_code;
    }
}
