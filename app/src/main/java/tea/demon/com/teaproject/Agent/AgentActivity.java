package tea.demon.com.teaproject.Agent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.Adapter.GoodsAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.activity.LoginActivity;
import tea.demon.com.teaproject.activity.PutPassWordActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Store;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.presenter.AgentPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class AgentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private View agent_header;
    private List<Store> list;
    public static Handler handler;
    private TextView tv_agentName, tv_phone;
    private List<Tea> teaList;
    private GoodsAdapter adapter;
    private int store_Id;
    public static AgentActivity activity;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        ButterKnife.bind(this);
        activity = this;
        TitleUtil.setDrawToolBar(this, getString(R.string.agent), drawer);
        initView();
        initHandler();
    }


    private void initView() {
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(sgm);
        navView.setNavigationItemSelectedListener(this);
        agent_header = navView.getHeaderView(0);
        tv_agentName = (TextView) agent_header.findViewById(R.id.tv_agentName);
        tv_phone = (TextView) agent_header.findViewById(R.id.tv_phone);
        AgentPresenter presenter = new AgentPresenter(this, new IView() {
            @Override
            public void getData(int code, Object object) {
                if (code == 200) {
                    list = (List<Store>) object;
                    handler.sendEmptyMessage(0x001);
                }
            }
        });
        presenter.getStore();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    if (list.size() == 0) {
                        Dialogutil.psDialogBuilder(AgentActivity.this, "尚未创建商店，请点击右下角创建！");
                        fab.setVisibility(View.VISIBLE);
                        tv_phone.setText(getString(R.string.noInfo));
                        tv_agentName.setText(getString(R.string.noInfo));
                    } else {
                        store = list.get(0);
                        tv_phone.setText(store.getPhone());
                        tv_agentName.setText(store.getName());
                        store_Id = store.getStore_id();
                        AgentPresenter presenter = new AgentPresenter(AgentActivity.this, new IView() {
                            @Override
                            public void getData(int code, Object object) {
                                if (code == 200) {
                                    teaList = (List<Tea>) object;
                                    handler.sendEmptyMessage(0x002);
                                }
                            }
                        });
                        presenter.getGoodList();
                    }
                } else if (msg.what == 0x002) {
                    adapter = new GoodsAdapter(AgentActivity.this, teaList);
                    recycleView.setAdapter(adapter);
                }
            }
        };
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_store) {//商铺管理
            Intent intent = new Intent(this, EditStoreActivity.class);
            intent.putExtra(Constant.STORE, store);
            startActivity(intent);
        } else if (id == R.id.nav_changepass) {//修改密码
            startActivity(new Intent(this, PutPassWordActivity.class));
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (id == R.id.nav_quit) {
            finish();
        } else if (id == R.id.nav_coupon) {
            startActivity(new Intent(this, CouponActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(this, CreateStoreActivity.class));
    }
}
