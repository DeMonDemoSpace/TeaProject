package tea.demon.com.teaproject.presenter;

import android.content.Context;

import java.util.Map;

import tea.demon.com.teaproject.data.Api;
import tea.demon.com.teaproject.model.ICallBack;
import tea.demon.com.teaproject.model.Model;
import tea.demon.com.teaproject.view.IView;

/**
 * Created by D&LL on 2017/5/29.
 */

public class PutPassPresenter implements IPresenter {
    private Context context;
    private Model model;
    private IView view;

    public PutPassPresenter(Context context, IView view) {
        this.context = context;
        this.model = Model.getModel();
        this.view = view;
    }

    @Override
    public void initDo(Object object) {
        model.put(context, Api.SETPASSWORD, (Map<String, String>) object, new ICallBack() {
            @Override
            public void result(int code, String s) {
                view.getData(code, s);
            }
        });
    }
}
