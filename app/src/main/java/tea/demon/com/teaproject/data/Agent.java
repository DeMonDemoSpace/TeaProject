package tea.demon.com.teaproject.data;

import java.io.Serializable;

/**
 * Created by D&LL on 2017/5/25.
 */

public class Agent implements Serializable {
    int agent_id, userId, check_status;
    private String phone, licence, name;

    public Agent(int agent_id, int userId, int check_status, String phone, String licence, String name) {
        this.agent_id = agent_id;
        this.userId = userId;
        this.check_status = check_status;
        this.phone = phone;
        this.licence = licence;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public int getCheck_status() {
        return check_status;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }
}
