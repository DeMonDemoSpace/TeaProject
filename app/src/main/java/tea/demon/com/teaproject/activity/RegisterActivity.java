package tea.demon.com.teaproject.activity;

import android.os.Bundle;
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
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.presenter.RegisterPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.IView;

public class RegisterActivity extends AppCompatActivity implements IView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_ensurePass)
    EditText etEnsurePass;
    @BindView(R.id.et_mail)
    EditText etMail;
    @BindView(R.id.bn_login)
    Button bnLogin;

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        TitleUtil.setToolBar(this, getString(R.string.register));
    }

    @OnClick(R.id.bn_login)
    public void onViewClicked() {
        String phone = etPhone.getText().toString();
        String pass = etPassword.getText().toString();
        String enPass = etEnsurePass.getText().toString();
        String mail = etMail.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(enPass) || TextUtils.isEmpty(mail)) {
            ToastUtil.ShowToast(this, "注册信息不能为空！");
            return;
        } else if (!pass.equals(enPass)) {
            ToastUtil.ShowToast(this, "密码不一致！");
        } else {
            map.put(Constant.USERNAME, phone);
            map.put(Constant.PASSWORD, pass);
            map.put(Constant.EMAIL, mail);
            RegisterPresenter presenter = new RegisterPresenter(this, this);
            presenter.initDo(map);
        }
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 201) {
            ToastUtil.ThreadToast(this, "注册成功！");
            finish();
        }

    }
}



