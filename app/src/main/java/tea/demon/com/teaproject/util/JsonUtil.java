package tea.demon.com.teaproject.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import tea.demon.com.teaproject.User.ShoppingCarActivity;
import tea.demon.com.teaproject.data.BuyGood;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Error;

/**
 * Created by D&LL on 2017/5/22.
 */

public class JsonUtil {
    /**
     * 获取返回数据中的data对应的JSONArray
     *
     * @param s
     * @return
     */
    public static String getArrayData(String s) {
        System.out.println(s);
        JSONArray nodata = new JSONArray();
        JSONArray data = null;
        try {
            JSONObject object = new JSONObject(s);
            if (object.has(Constant.DATA)) {
                data = object.getJSONArray(Constant.DATA);
            } else {
                data = nodata;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * 获取返回数据中的detail对应的JSONArray
     *
     * @param s
     * @return
     */
    public static String getArrayDetail(String s) {
        String dataobject = JsonUtil.getObjectData(s);
        System.out.println(s);
        JSONArray nodata = new JSONArray();
        JSONArray data = null;
        try {
            JSONObject object = new JSONObject(dataobject);
            if (object.has(Constant.DETAIL)) {
                data = object.getJSONArray(Constant.DETAIL);
            } else {
                data = nodata;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * 获取返回数据中的data对应的JSONObject
     *
     * @param s
     * @return
     */
    public static String getObjectData(String s) {
        System.out.println(s);
        JSONObject nodata = new JSONObject();
        JSONObject data = null;
        try {
            JSONObject object = new JSONObject(s);
            if (object.has(Constant.DATA)) {
                data = object.getJSONObject(Constant.DATA);
            } else {
                data = nodata;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * 处理请求出错时的Json
     *
     * @param s
     * @return
     */
    public static Error getErrorJson(String s) {
        System.out.println(s);
        Gson gson = new Gson();
        Error error = gson.fromJson(s, Error.class);
        return error;
    }

    /**
     * 将购物信息封装成json
     *
     * @param list
     * @param address_id
     * @return
     */
    public static String buyInfoJson(List<BuyGood> list, int address_id) {
        JSONObject ob = new JSONObject();
        JSONArray array = new JSONArray();
        int money = 0;
        try {
            for (int i = 0; i < list.size(); i++) {
                BuyGood good = list.get(i);
                money += good.getPrice() * good.getAmount();
                JSONObject object = new JSONObject();
                object.put(Constant.GOODS_ID, good.getGoods_id());
                object.put(Constant.COUPON_ID, good.getCoupon_id());
                object.put(Constant.AMOUNT, good.getAmount());
                array.put(object);
            }
            ob.put(Constant.GOODS, array);
            ob.put(Constant.ADDRESS_ID, address_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ShoppingCarActivity.sum = money;
        return ob.toString();
    }
}
