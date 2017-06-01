package tea.demon.com.teaproject.Admin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Store;
import tea.demon.com.teaproject.presenter.AdminPresenter;
import tea.demon.com.teaproject.presenter.TeaPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.IView;

public class AgentStoreActivity extends AppCompatActivity implements IView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.bn_sure)
    Button bnSure;
    @BindView(R.id.et_phone)
    EditText etPhone;

    private int agentId;
    private List<Store> list = new ArrayList<>();
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_store);
        ButterKnife.bind(this);
        TitleUtil.setToolBar(this, getString(R.string.lookStore));
        agentId = getIntent().getIntExtra(Constant.AGENT_ID, -1);
        initData();
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.AGENT_ID, agentId + "");
        AdminPresenter presenter = new AdminPresenter(this, this);
        presenter.getAgentStore(map);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x567) {
                    initView();
                } else if (msg.what == 0x111) {
                    ToastUtil.ShowToast(AgentStoreActivity.this, "上架成功！");
                    TeaInfoActivity.handler.sendEmptyMessage(0x001);
                    AgentListActivity.activity.finish();
                    finish();
                }
            }
        };
    }

    private void initView() {
        if (list.size() == 0) {
            etName.setText(getString(R.string.noInfo));
            etAddress.setText(getString(R.string.noInfo));
            etPhone.setText(getString(R.string.noInfo));
            etDescription.setText(getString(R.string.noInfo));
            bnSure.setVisibility(View.GONE);
        } else {
            Store store = list.get(0);
            etName.setText(store.getName());
            etAddress.setText(store.getAddress());
            etPhone.setText(store.getPhone());
            etDescription.setText(store.getDescription());
        }
        if (AgentListActivity.request != -1) {
            bnSure.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.bn_sure)
    public void onViewClicked() {
        Map<String, String> map = new HashMap<>();
        map.put(Constant.GOODS_ID, AgentListActivity.request + "");
        map.put(Constant.STORE_ID, list.get(0).getStore_id() + "");
        TeaPresenter teaPresenter = new TeaPresenter(this, new IView() {
            @Override
            public void getData(int code, Object object) {
                if (code == 201) {
                    handler.sendEmptyMessage(0x111);
                }
            }
        });
        teaPresenter.postOnSell(map);
    }

    @Override
    public void getData(int code, Object object) {
        list = (List<Store>) object;
        handler.sendEmptyMessage(0x567);
    }
}
