package tea.demon.com.teaproject.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import tea.demon.com.teaproject.data.Api;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.model.ICallBack;
import tea.demon.com.teaproject.model.Model;
import tea.demon.com.teaproject.util.JsonUtil;
import tea.demon.com.teaproject.view.IView;

/**
 * 茶叶操作数据
 * Created by D&LL on 2017/5/26.
 */

public class TeaPresenter {
    private Context context;
    private Model model;
    private IView view;

    public TeaPresenter(Context context, IView view) {
        this.context = context;
        this.model = Model.getModel();
        this.view = view;
    }

    /**
     * 分类获取茶叶
     */
    public void getTeaByClass(Map<String, String> map) {
        model.get(context, Api.ADMINS_GETTEABYCLASS, map, new ICallBack() {
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
     * 创建茶叶信息
     *
     * @param map
     */
    public void postCreateTea(Map<String, String> map) {
        model.post(context, Api.ADMINS_CREATEGOODS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getObjectData(s);
                try {
                    int id = new JSONObject(object).getInt(Constant.GOODS_ID);
                    view.getData(code, id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 删除茶叶
     *
     * @param map
     */
    public void delTea(Map<String, String> map) {
        model.delete(context, Api.ADMINS_DELETEGOODS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });

    }

    /**
     * 修改茶叶分类
     *
     * @param map
     */
    public void putTeaClass(Map<String, String> map) {
        model.put(context, Api.ADMINS_SETTEACLASS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 修改茶叶信息
     *
     * @param map
     */
    public void putTeaInfo(Map<String, String> map) {
        model.put(context, Api.ADMINS_MODIFYGOODS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }

    /**
     * 商铺上架
     *
     * @param map
     */
    public void postOnSell(Map<String, String> map) {
        model.post(context, Api.ADMINS_ONSELL, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }
}
