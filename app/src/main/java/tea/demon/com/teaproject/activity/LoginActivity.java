package tea.demon.com.teaproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.Admin.AdminActivity;
import tea.demon.com.teaproject.Agent.AgentActivity;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.User.UserActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Login;
import tea.demon.com.teaproject.data.Token;
import tea.demon.com.teaproject.presenter.LoginPresenter;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.IView;


public class LoginActivity extends Activity implements IView {

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bn_login)
    Button bnLogin;
    @BindView(R.id.bn_register)
    Button bnRegister;

    private Login login;
    private Token token = Token.getInstance();
    private Handler handler;
    private Map<String, String> map = new HashMap<>();
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        etAccount.setText("admin");
        etPassword.setText("123456");
    }


    @Override
    public void getData(int code, final Object object) {
        login = (Login) object;
        token.setToken(login.getToken());
        token.setId(login.getUserId());
        handler.sendEmptyMessage(0x123);

    }


    @OnClick({R.id.bn_login, R.id.bn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bn_login:
                toLogin();

                break;
            case R.id.bn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void toLogin() {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            ToastUtil.ShowToast(this, "账号或者密码不能为空！");
            return;
        } else {
            map.put(Constant.USERNAME, account);
            map.put(Constant.PASSWORD, password);
            LoginPresenter presenter = new LoginPresenter(this, this);
            presenter.initDo(map);
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0x123) {
                        start();
                    }
                }
            };

        }
    }

    private void start() {
        System.out.println(token.getToken());
        int identity = login.getIdentity();
        Constant.IDENTITY = identity;
        if (identity == 0) {//用户
            startActivity(new Intent(this, UserActivity.class));
        } else if (identity == 1) {//代理商
            startActivity(new Intent(this, AgentActivity.class));
        } else if (identity == 2) {//管理员
            startActivity(new Intent(this, AdminActivity.class));
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

