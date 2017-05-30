package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/22.
 */

public class Login {
    private int userId, identity;
    private String token;

    public Login(int userId, int identity, String token) {
        this.userId = userId;
        this.identity = identity;
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public int getIdentity() {
        return identity;
    }

    public String getToken() {
        return token;
    }

}
