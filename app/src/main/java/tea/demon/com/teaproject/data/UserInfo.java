package tea.demon.com.teaproject.data;

import java.io.Serializable;

/**
 * Created by D&LL on 2017/5/29.
 */

public class UserInfo implements Serializable {
    private String name, avatar;
    private int userId;

    public UserInfo(String name, String avatar, int userId) {
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getUserId() {
        return userId;
    }
}
