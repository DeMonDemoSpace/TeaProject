package tea.demon.com.teaproject.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import tea.demon.com.teaproject.data.Api;
import tea.demon.com.teaproject.data.Approve;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Coupon;
import tea.demon.com.teaproject.data.Order;
import tea.demon.com.teaproject.data.OrderList;
import tea.demon.com.teaproject.data.Store;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.data.UserAddress;
import tea.demon.com.teaproject.data.UserInfo;
import tea.demon.com.teaproject.model.ICallBack;
import tea.demon.com.teaproject.model.Model;
import tea.demon.com.teaproject.util.JsonUtil;
import tea.demon.com.teaproject.view.IView;

/**
 * 用户获取数据
 * Created by D&LL on 2017/5/29.
 */

public class UserPresenter {
    private Context context;
    private Model model;
    private IView view;

    public UserPresenter(Context context, IView view) {
        this.context = context;
        this.model = Model.getModel();
        this.view = view;
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        model.get(context, Api.USER_GETINFO, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getObjectData(s);
                UserInfo userInfo = new Gson().fromJson(object, UserInfo.class);
                userInfo.setAvatar(Api.http + userInfo.getAvatar());
                view.getData(code, userInfo);
            }
        });
    }

    /**
     * 修改用户信息
     *
     * @param map
     */
    public void putUserInfo(Map<String, String> map) {
        model.put(context, Api.USER_MODIFYINFO, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 获取地址
     */
    public void getAddress() {
        model.get(context, Api.USER_GETADDRESS, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String array = JsonUtil.getArrayData(s);
                List<UserAddress> list = new Gson().fromJson(array, new TypeToken<List<UserAddress>>() {
                }.getType());
                view.getData(code, list);
            }
        });

    }

    /**
     * 删除地址
     *
     * @param map
     */
    public void delAddress(Map<String, String> map) {
        model.delete(context, Api.USER_DELETEADDRESS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 新建地址
     *
     * @param map
     */
    public void createAddress(Map<String, String> map) {
        model.post(context, Api.USER_CREATEADDRESS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getObjectData(s);
                JSONObject jo = null;
                try {
                    jo = new JSONObject(object);
                    int id = jo.getInt(Constant.ADDRESS_ID);
                    view.getData(code, id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 修改地址
     *
     * @param map
     */
    public void putAddress(Map<String, String> map) {
        model.put(context, Api.USER_MODIFYADDRESS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 获取认证信息
     */
    public void getUpgrade() {
        model.get(context, Api.USER_GETUPGRADE, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getObjectData(s);
                if (code == 200) {
                    Approve approve = new Gson().fromJson(object, Approve.class);
                    view.getData(code, approve);
                } else {
                    Approve approve = new Approve("", "", "", -1);
                    view.getData(code, approve);
                }
            }
        });
    }

    /**
     * 申请认证
     *
     * @param map
     */
    public void postUpgrade(Map<String, String> map) {
        model.post(context, Api.USER_UPGRADE, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 获取商店列表
     */
    public void getStoreList() {
        model.get(context, Api.USER_GETSTORELIST, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String array = JsonUtil.getArrayData(s);
                List<Store> list = new Gson().fromJson(array, new TypeToken<List<Store>>() {
                }.getType());
                view.getData(code, list);
            }
        });
    }

    /**
     * 获取商店的商品
     *
     * @param map
     */
    public void getGoodsByStore(Map<String, String> map) {
        model.get(context, Api.USER_GETGOODLISTBYSRORE, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String array = JsonUtil.getArrayData(s);
                List<Tea> list = new Gson().fromJson(array, new TypeToken<List<Tea>>() {
                }.getType());
                for (Tea tea : list) {
                    tea.setThumb(Api.http + tea.getThumb());
                }
                view.getData(code, list);
            }
        });
    }

    /**
     * 获取折扣
     *
     * @param map
     */
    public void getGoodCoupon(Map<String, String> map) {
        model.get(context, Api.USER_GETCOUPONBYGOODS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String array = JsonUtil.getArrayData(s);
                List<Coupon> list = new Gson().fromJson(array, new TypeToken<List<Coupon>>() {
                }.getType());
                view.getData(code, list);
            }
        });
    }

    /**
     * 创建订单
     *
     * @param s
     */
    public void postBuyInfo(String s) {
        model.postJson(context, Api.USER_CREATEORDER, s, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 获取订单列表
     *
     * @param map
     */
    public void getOrderList(Map<String, String> map) {
        model.get(context, Api.USER_GETORDERLIST, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String array = JsonUtil.getArrayData(s);
                List<OrderList> list = new Gson().fromJson(array, new TypeToken<List<OrderList>>() {
                }.getType());
                view.getData(code, list);
            }
        });
    }

    /**
     * 获取订单详情
     *
     * @param map
     */
    public void getOrderInfo(Map<String, String> map) {
        model.get(context, Api.USER_GETORDERDETAIL, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String array = JsonUtil.getArrayDetail(s);
                List<Order> list = new Gson().fromJson(array, new TypeToken<List<Order>>() {
                }.getType());
                for (Order order : list) {
                    order.setThumb(Api.http + order.getThumb());
                }
                view.getData(code, list);
            }
        });
    }

    /**
     * 获取订单列表
     *
     * @param map
     */
    public void getOrderListAdmin(Map<String, String> map) {
        model.get(context, Api.ADMINS_GETORDERLIST, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String array = JsonUtil.getArrayData(s);
                List<OrderList> list = new Gson().fromJson(array, new TypeToken<List<OrderList>>() {
                }.getType());
                view.getData(code, list);
            }
        });
    }

    /**
     * 获取订单详情
     *
     * @param map
     */
    public void getOrderInfoAdmin(Map<String, String> map) {
        model.get(context, Api.ADMINS_GETORDERDETAIL, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String array = JsonUtil.getArrayDetail(s);
                List<Order> list = new Gson().fromJson(array, new TypeToken<List<Order>>() {
                }.getType());
                for (Order order : list) {
                    order.setThumb(Api.http + order.getThumb());
                }
                view.getData(code, list);
            }
        });
    }
}
