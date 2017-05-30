package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/25.
 */

public class User {
    private int id, identity, score;
    private String username, email, name, avator;

    /**
     * 用户列表
     *
     * @param id
     * @param identity
     * @param username
     * @param email
     */
    public User(int id, int identity, String username, String email, String name) {
        this.id = id;
        this.identity = identity;
        this.username = username;
        this.email = email;
        this.name = name;
    }

    /**
     * 用户详情
     *
     * @param username
     * @param email
     * @param name
     * @param avator
     */
    public User(String username, String email, String name, String avator) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.avator = avator;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getAvator() {
        return avator;
    }

    public int getId() {
        return id;
    }

    public int getIdentity() {
        return identity;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
