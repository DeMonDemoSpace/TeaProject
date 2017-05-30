package tea.demon.com.teaproject.data;

import java.io.Serializable;

/**
 * Created by D&LL on 2017/5/29.
 */

public class UserAddress implements Serializable{
    private String name,phone,content;
    private int address_id;

    public UserAddress(String name, String phone, String content, int address_id) {
        this.name = name;
        this.phone = phone;
        this.content = content;
        this.address_id = address_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getContent() {
        return content;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
}
