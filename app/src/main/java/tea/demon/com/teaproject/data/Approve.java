package tea.demon.com.teaproject.data;

import java.io.Serializable;

/**
 * Created by D&LL on 2017/5/29.
 */

public class Approve implements Serializable{
    private String name,phone,licence;
    private int check_status;

    public Approve(String name, String phone, String licence, int check_status) {
        this.name = name;
        this.phone = phone;
        this.licence = licence;
        this.check_status = check_status;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicence() {
        return licence;
    }

    public int getCheck_status() {
        return check_status;
    }
}
