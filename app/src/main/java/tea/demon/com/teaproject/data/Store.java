package tea.demon.com.teaproject.data;

import java.io.Serializable;

/**
 * Created by D&LL on 2017/5/25.
 */

public class Store implements Serializable{
    private int store_id;
    private String name, address, phone, description;

    public Store(int store_id, String name, String address, String phone, String description) {
        this.store_id = store_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }

    public int getStore_id() {
        return store_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }
}
