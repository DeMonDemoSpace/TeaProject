package tea.demon.com.teaproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.User.UserActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.presenter.PutPassPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.IView;

public class PutPassWordActivity extends AppCompatActivity implements IView {
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_ensurePass)
    EditText etEnsurePass;
    @BindView(R.id.bt_sure)
    Button btSure;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        TitleUtil.setToolBar(this, getString(R.string.change_password));
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    ToastUtil.ShowToast(PutPassWordActivity.this, "修改成功！");
                    finish();
                    UserActivity.activity.finish();
                    startActivity(new Intent(PutPassWordActivity.this, LoginActivity.class));
                }
            }
        };
    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        String pass = etPassword.getText().toString();
        String enPass = etEnsurePass.getText().toString();
        if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(enPass) || !pass.equals(enPass)) {
            ToastUtil.ShowToast(this, "两次密码不一致！");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put(Constant.NEWPASS, pass);
            PutPassPresenter presenter = new PutPassPresenter(this, this);
            presenter.initDo(map);

        }
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 201) {
            handler.sendEmptyMessage(0x001);
        }
    }
}
