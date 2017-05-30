package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/30.
 */

public class BuyGood {
    private String name, coupon;
    private int goods_id, coupon_id, amount;
    private double price;

    public BuyGood(String name, double price, String coupon, int goods_id, int coupon_id, int amount) {
        this.name = name;
        this.price = price;
        this.coupon = coupon;
        this.goods_id = goods_id;
        this.coupon_id = coupon_id;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
