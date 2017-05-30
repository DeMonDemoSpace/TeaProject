package tea.demon.com.teaproject.Admin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.Adapter.AgentListAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Agent;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.presenter.AdminPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class AgentListActivity extends AppCompatActivity implements IView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.btn_checking)
    Button btnChecking;
    @BindView(R.id.btn_passed)
    Button btnPassed;
    @BindView(R.id.btn_notpassed)
    Button btnNotpassed;
    public static AgentListAdapter adapter;
    public static List<Agent> list = new ArrayList<>();
    private Handler handler;
    private int status = 1;
    public static int request = -1;
    public static AgentListActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);
        TitleUtil.setToolBar(this, getString(R.string.agentManage));
        ButterKnife.bind(this);
        activity = this;
        Map<String, String> map = new HashMap<>();
        map.put(Constant.CHECK_STATUS, status + "");
        request = getIntent().getIntExtra(Constant.GOODS_ID, -1);
        if (request != -1) {//上架操作，隐藏审核和不通过商铺
            btnChecking.setVisibility(View.GONE);
            btnNotpassed.setVisibility(View.GONE);
        }
        initData(map);

    }

    @OnClick({R.id.btn_checking, R.id.btn_passed, R.id.btn_notpassed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_checking:
                initColor();
                status = 2;
                btnChecking.setTextColor(getResources().getColor(R.color.button_press));
                Map<String, String> map1 = new HashMap<>();
                map1.put(Constant.CHECK_STATUS, status + "");
                initData(map1);
                break;
            case R.id.btn_passed:
                initColor();
                status = 1;
                btnPassed.setTextColor(getResources().getColor(R.color.button_press));
                Map<String, String> map2 = new HashMap<>();
                map2.put(Constant.CHECK_STATUS, status + "");
                initData(map2);
                break;
            case R.id.btn_notpassed:
                initColor();
                status = 0;
                btnNotpassed.setTextColor(getResources().getColor(R.color.button_press));
                Map<String, String> map3 = new HashMap<>();
                map3.put(Constant.CHECK_STATUS, status + "");
                initData(map3);
                break;
        }
    }


    private void initData(Map<String, String> map) {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        AdminPresenter presenter = new AdminPresenter(this, this);
        presenter.getAgentList(map);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x456) {
                    adapter = new AgentListAdapter(AgentListActivity.this, list);
                    recycleView.setAdapter(adapter);
                }
            }
        };


    }

    @Override
    public void getData(int code, Object object) {
        list = (List<Agent>) object;
        handler.sendEmptyMessage(0x456);
    }

    private void initColor() {
        btnChecking.setTextColor(getResources().getColor(R.color.button_normal));
        btnPassed.setTextColor(getResources().getColor(R.color.button_normal));
        btnNotpassed.setTextColor(getResources().getColor(R.color.button_normal));
    }
}
