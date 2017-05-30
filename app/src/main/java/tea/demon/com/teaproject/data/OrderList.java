package tea.demon.com.teaproject.data;

import java.io.Serializable;

/**
 * Created by D&LL on 2017/5/30.
 */

public class OrderList implements Serializable{
    private int order_id;
    private String order_no, created_at, pay;

    public OrderList(int order_id, String order_no, String created_at, String pay) {
        this.order_id = order_id;
        this.order_no = order_no;
        this.created_at = created_at;
        this.pay = pay;
    }

    public int getOrder_id() {
        return order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getPay() {
        return pay;
    }
}
