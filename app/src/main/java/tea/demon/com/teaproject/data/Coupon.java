package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/29.
 */

public class Coupon {
    private int coupon_id, type;
    private String name, discount, gift;

    public Coupon(int coupon_id, int type, String name, String discount, String gift) {
        this.coupon_id = coupon_id;
        this.type = type;
        this.name = name;
        this.discount = discount;
        this.gift = gift;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDiscount() {
        return discount;
    }

    public String getGift() {
        return gift;
    }
}
