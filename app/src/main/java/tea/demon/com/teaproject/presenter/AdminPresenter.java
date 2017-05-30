package tea.demon.com.teaproject.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tea.demon.com.teaproject.data.Agent;
import tea.demon.com.teaproject.data.Api;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Store;
import tea.demon.com.teaproject.data.TeaClass;
import tea.demon.com.teaproject.data.User;
import tea.demon.com.teaproject.model.ICallBack;
import tea.demon.com.teaproject.model.Model;
import tea.demon.com.teaproject.util.JsonUtil;
import tea.demon.com.teaproject.view.IView;

/**
 * 管理员界面操作数据
 * Created by D&LL on 2017/5/25.
 */

public class AdminPresenter {
    private Model model;
    private IView view;
    private Context context;

    public AdminPresenter(Context context, IView view) {
        this.model = Model.getModel();
        this.view = view;
        this.context = context;
    }

    /**
     * 获取管理员信息
     */
    public void getAdminInfo() {
        model.get(context, Api.ADMINS_GETINFO, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getObjectData(s);
                try {
                    JSONObject jo = new JSONObject(object);
                    List<String> list = new ArrayList<String>();
                    list.add(jo.getInt(Constant.A_ID) + "");
                    list.add(jo.getString(Constant.NAME));
                    view.getData(code, list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 管理员获取用户列表
     */
    public void getUserList() {
        model.get(context, Api.ADMINS_GETUSERLIST, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getArrayData(s);
                Gson gson = new Gson();
                List<User> users = gson.fromJson(object, new TypeToken<List<User>>() {
                }.getType());
                view.getData(code, users);
            }
        });
    }

    /**
     * 管理员获取用户信息
     */
    public void getUserInfo(Map<String, String> map) {
        model.get(context, Api.ADMINS_GETUSERDETAIL, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getObjectData(s);
                try {
                    JSONObject jo = new JSONObject(object);
                    JSONObject General = jo.getJSONObject("General");
                    String username = General.getString(Constant.USERNAME);
                    String email = General.getString(Constant.EMAIL);
                    JSONObject Detail = jo.getJSONObject("Detail");
                    String name = Detail.getString(Constant.NAME);
                    String avatar = Detail.getString(Constant.AVATAR);
                    User user = new User(username, email, name, Api.http + avatar);
                    view.getData(code, user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取代理商列表
     *
     * @param map
     */
    public void getAgentList(Map<String, String> map) {
        model.get(context, Api.ADMINS_MANAGEAGENT_GETLIST, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getArrayData(s);
                Gson gson = new Gson();
                List<Agent> list = gson.fromJson(object, new TypeToken<List<Agent>>() {
                }.getType());

                for (Agent agent : list) {
                    agent.setLicence(Api.http + agent.getLicence());
                }
                view.getData(code, list);
            }
        });

    }

    /**
     * 获取代理商的商铺信息
     *
     * @param map
     */
    public void getAgentStore(Map<String, String> map) {
        model.get(context, Api.ADMINS_AGENT_GETSTORE, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getArrayData(s);
                Gson gson = new Gson();
                List<Store> list = gson.fromJson(object, new TypeToken<List<Store>>() {
                }.getType());
                view.getData(code, list);
            }
        });
    }

    /**
     * 设置审核状态
     *
     * @param map
     */
    public void postSetStatus(Map<String, String> map) {
        model.post(context, Api.ADMINS_SETSTATUS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, s);
            }
        });
    }

    /**
     * 获取茶叶分类
     */
    public void getTeaClass() {
        model.get(context, Api.ADMINS_GETCLASS, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String result = JsonUtil.getArrayData(s);
                Gson gson = new Gson();
                List<TeaClass> list = gson.fromJson(result, new TypeToken<List<TeaClass>>() {
                }.getType());
                view.getData(code, list);
            }
        });
    }

    /**
     * 新建茶叶分类
     *
     * @param map
     */
    public void postAddClass(Map<String, String> map) {
        model.post(context, Api.ADMINS_CREATECLASS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                String object = JsonUtil.getObjectData(s);
                Gson gson = new Gson();
                TeaClass teaClass = gson.fromJson(object, TeaClass.class);
                view.getData(code, teaClass);
            }
        });
    }

    /**
     * 删除分类
     *
     * @param map
     */
    public void delTeaClass(Map<String, String> map) {
        model.delete(context, Api.ADMINS_DELETECLASS, map, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });

    }

    /**
     * 修改分类
     */
    public void putTeaClass(Map<String, String> map) {
        model.put(context,
                Api.ADMINS_MODIFYCLASS, map, new ICallBack() {
                    @Override
                    public void result(int code, String s) {
                        view.getData(code, s);
                    }
                });
    }

}
