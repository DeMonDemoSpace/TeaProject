package tea.demon.com.teaproject.Agent;

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
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.presenter.AgentPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.IView;

public class CreateStoreActivity extends AppCompatActivity implements IView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_des)
    EditText etDes;
    @BindView(R.id.bn_sure)
    Button bnSure;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        ButterKnife.bind(this);
        TitleUtil.setToolBar(this, getString(R.string.newStore));
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    AgentActivity.activity.finish();
                    startActivity(new Intent(CreateStoreActivity.this, AgentActivity.class));
                    finish();
                }
            }
        };
    }

    @OnClick(R.id.bn_sure)
    public void onViewClicked() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String address = etAddress.getText().toString();
        String des = etDes.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(des)) {
            ToastUtil.ShowToast(this, getString(R.string.needInfo));
            return;
        } else {
            Map<String, String> map = new HashMap<>();
            map.put(Constant.NAME, name);
            map.put(Constant.PHONE, phone);
            map.put(Constant.ADDRESS, address);
            map.put(Constant.DESCRIPTION, des);
            AgentPresenter presenter = new AgentPresenter(this, this);
            presenter.postStore(map);
        }
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 201) {
            handler.sendEmptyMessage(0x001);
        }
    }
}
