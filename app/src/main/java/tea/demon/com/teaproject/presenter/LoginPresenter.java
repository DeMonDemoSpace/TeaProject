package tea.demon.com.teaproject.presenter;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

import tea.demon.com.teaproject.data.Api;
import tea.demon.com.teaproject.data.Login;
import tea.demon.com.teaproject.model.ICallBack;
import tea.demon.com.teaproject.model.Model;
import tea.demon.com.teaproject.util.JsonUtil;
import tea.demon.com.teaproject.view.IView;


/**
 * Created by D&LL on 2017/5/22.
 */

public class LoginPresenter implements IPresenter {
    private Model model;
    private IView view;
    private Context context;

    public LoginPresenter(Context context, IView view) {
        this.context = context;
        this.view = view;
        model = Model.getModel();
    }




    @Override
    public void initDo(Object object) {
        model.post(context, Api.LOGIN, (Map<String, String>) object, new ICallBack() {
            @Override
            public void result(int code, String s) {
                Gson gson = new Gson();
                String object = JsonUtil.getObjectData(s);
                Login login = gson.fromJson(object, Login.class);
                view.getData(code,login);
            }
        });
    }
}
