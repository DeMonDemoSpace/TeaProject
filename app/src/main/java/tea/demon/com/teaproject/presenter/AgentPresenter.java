package tea.demon.com.teaproject.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import tea.demon.com.teaproject.data.Api;
import tea.demon.com.teaproject.data.Coupon;
import tea.demon.com.teaproject.data.Store;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.model.ICallBack;
import tea.demon.com.teaproject.model.Model;
import tea.demon.com.teaproject.util.JsonUtil;
import tea.demon.com.teaproject.view.IView;

/**
 * Created by D&LL on 2017/5/30.
 */

public class AgentPresenter {
    private Model model;
    private Context context;
    private IView view;

    public AgentPresenter(Context context, IView view) {
        this.model = Model.getModel();
        this.context = context;
        this.view = view;
    }

    /**
     * 获取商铺
     */
    public void getStore() {
        model.get(context, Api.AGENT_GETSTORE, new ICallBack() {
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
     * 修改商铺信息
     *
     * @param map
     */
    public void putStoreInfo(Map<String, String> map) {
        model.put(context, Api.AGENT_MOFITYSTORE, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 获取代理商品
     */
    public void getGoodList() {
        model.get(context, Api.AGENT_GETGOODLIST, new ICallBack() {
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
     * 创建商店
     */
    public void postStore(Map<String, String> map) {
        model.post(context, Api.AGENT_CREATESTORE, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 创建优惠券
     *
     * @param map
     */
    public void postCoupon(Map<String, String> map) {
        model.post(context, Api.AGENT_COUPONCREATE, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 获取优惠券
     */
    public void getCoupon() {
        model.get(context, Api.AGENT_COUPONGET, new ICallBack() {
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
     * 删除优惠券
     *
     * @param map
     */
    public void delCoupon(Map<String, String> map) {
        model.delete(context, Api.AGENT_COUPONDELETE, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }
}
