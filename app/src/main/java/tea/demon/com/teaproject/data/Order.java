package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/30.
 */

public class Order {
    private String thumb, name, price;
    private int amount, coupon_id, goods_id;

    public Order(String thumb, String name, String price, int amount, int coupon_id, int goods_id) {
        this.thumb = thumb;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.coupon_id = coupon_id;
        this.goods_id = goods_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }
}
