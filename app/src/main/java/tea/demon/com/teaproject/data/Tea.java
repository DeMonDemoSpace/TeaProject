package tea.demon.com.teaproject.data;

import java.io.Serializable;

/**
 * Created by D&LL on 2017/5/27.
 */

public class Tea implements Serializable {
    private int goods_id, stock, sold_amount, store_id;
    private double price;
    private String name, description, thumb;

    //茶叶详情
    public Tea(int goods_id, int store_id, int stock, int sold_amount, double price, String name, String description, String thumb) {
        this.goods_id = goods_id;
        this.store_id = store_id;
        this.stock = stock;
        this.sold_amount = sold_amount;
        this.price = price;
        this.name = name;
        this.description = description;
        this.thumb = thumb;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold_amount() {
        return sold_amount;
    }

    public void setSold_amount(int sold_amount) {
        this.sold_amount = sold_amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
