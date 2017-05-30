package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/22.
 */

public class Token {
    private String token;
    private int id;
    private static Token instance = new Token();

    public static Token getInstance() {
        return instance;
    }

    public Token() {
        this.id = -1;
        this.token = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return "Bearer " + token;//Bearer+空格+token值
    }

    public void setToken(String token) {
        this.token = token;
    }

}
