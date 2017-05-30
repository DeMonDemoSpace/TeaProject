package tea.demon.com.teaproject.presenter;

import android.content.Context;

import java.util.Map;

import tea.demon.com.teaproject.data.Api;
import tea.demon.com.teaproject.model.ICallBack;
import tea.demon.com.teaproject.model.Model;
import tea.demon.com.teaproject.view.IView;

/**
 * Created by D&LL on 2017/5/25.
 */

public class RegisterPresenter implements IPresenter {
    private Model model;
    private IView view;
    private Context context;

    public RegisterPresenter(IView view, Context context) {
        this.model = Model.getModel();
        this.view = view;
        this.context = context;
    }


    @Override
    public void initDo(Object object) {
        model.post(context, Api.REGISTER, (Map<String, String>) object, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, null);
            }
        });
    }
}
