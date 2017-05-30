package tea.demon.com.teaproject.Admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Agent;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.presenter.AdminPresenter;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.IView;

public class ChangeCheckActivity extends AppCompatActivity implements IView {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_licence)
    ImageView ivLicence;
    @BindView(R.id.bn_pass)
    Button bnPass;
    @BindView(R.id.bn_nopass)
    Button bnNopass;
    private Agent agent;
    private Map<String, String> map = new HashMap<>();
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_check);
        ButterKnife.bind(this);
        TitleUtil.setToolBar(this, getString(R.string.checkAgent));
        agent = (Agent) getIntent().getSerializableExtra(Constant.AGENT_ID);
        pos = getIntent().getIntExtra(Constant.POS, -1);
        map.put(Constant.AGENT_ID, agent.getAgent_id() + "");
        initView();
    }

    private void initView() {
        etName.setText(agent.getName());
        etPhone.setText(agent.getPhone());

        GlideUtils.setImage(this, agent.getLicence(), ivLicence);
    }

    @OnClick({R.id.bn_pass, R.id.bn_nopass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bn_pass:
                map.put(Constant.CHECK_STATUS, 1 + "");
                initDo(map);
                finish();
                break;
            case R.id.bn_nopass:
                map.put(Constant.CHECK_STATUS, 0 + "");
                initDo(map);
                finish();
                break;
        }
    }

    private void initDo(Map<String, String> map) {
        AdminPresenter presenter = new AdminPresenter(this, this);
        presenter.postSetStatus(map);
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 201) {
            ToastUtil.ThreadToast(this, "审核成功！");
            AgentListActivity.list.remove(pos);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AgentListActivity.adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
