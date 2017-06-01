package tea.demon.com.teaproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.Adapter.TeaClassAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.User.OrderListActivity;
import tea.demon.com.teaproject.activity.LoginActivity;
import tea.demon.com.teaproject.activity.PutPassWordActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.TeaClass;
import tea.demon.com.teaproject.presenter.AdminPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.AddDialogView;
import tea.demon.com.teaproject.view.IView;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private TextView tv_adminNo, tv_adminName;
    private View admin_header;
    public static Handler handler;
    public static List<TeaClass> teaClassList = new ArrayList<>();
    private List<String> adminInfo = new ArrayList<>();
    private TeaClassAdapter teaClassAdapter;
    private TeaClass addTeaClass;
    public static int request = -1;//判断是不是移动请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        TitleUtil.setDrawToolBar(this, getString(R.string.admin), drawer);
        request = getIntent().getIntExtra(Constant.MOVE, -1);
        initView();


    }

    private void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        navView.setNavigationItemSelectedListener(this);
        admin_header = navView.getHeaderView(0);
        tv_adminNo = (TextView) admin_header.findViewById(R.id.tv_adminNo);
        tv_adminName = (TextView) admin_header.findViewById(R.id.tv_adminName);

        //获取分类
        AdminPresenter cpresenter = new AdminPresenter(this, new IView() {
            @Override
            public void getData(int code, Object object) {
                teaClassList = (List<TeaClass>) object;
                handler.sendEmptyMessage(0x678);
            }
        });
        cpresenter.getTeaClass();


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x678) {
                    teaClassAdapter = new TeaClassAdapter(teaClassList, AdminActivity.this);
                    recycleView.setAdapter(teaClassAdapter);
                    initAdminInfo();
                } else if (msg.what == 0x789) {
                    tv_adminNo.setText("编号:" + adminInfo.get(0));
                    tv_adminName.setText(adminInfo.get(1));
                } else if (msg.what == 0x890) {
                    teaClassList.add(addTeaClass);
                    teaClassAdapter.notifyDataSetChanged();
                } else if (msg.what == 0x901) {
                    teaClassAdapter.notifyDataSetChanged();
                } else if (msg.what == 0x012) {
                    teaClassAdapter.notifyDataSetChanged();
                } else if (msg.what == 0x0123) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.MOVE, (int) msg.obj);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    /**
     * 获取管理员信息
     */
    private void initAdminInfo() {
        AdminPresenter apresenter = new AdminPresenter(AdminActivity.this, new IView() {
            @Override
            public void getData(int code, final Object object) {
                adminInfo = (List<String>) object;
                handler.sendEmptyMessage(0x789);
            }
        });
        apresenter.getAdminInfo();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_user) {
            startActivity(new Intent(this, UserListActivity.class));
        } else if (id == R.id.nav_agent) {
            startActivity(new Intent(this, AgentListActivity.class));
        } else if (id == R.id.nav_order) {
            startActivity(new Intent(this, OrderListActivity.class));
        } else if (id == R.id.nav_changepass) {
            startActivity(new Intent(this, PutPassWordActivity.class));
        } else if (id == R.id.nav_logout) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.nav_quit) {
            finish();
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
        View view = View.inflate(this, R.layout.edit_teaclass, null);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_des = (EditText) view.findViewById(R.id.et_description);
        Dialogutil.addDialogBuilder(this, getString(R.string.addclass), view, new AddDialogView() {
            @Override
            public void add() {
                String name = et_name.getText().toString();
                String des = et_des.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(des)) {
                    ToastUtil.ShowToast(AdminActivity.this, getString(R.string.needInfo));
                    return;
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(Constant.NAME, name);
                    map.put(Constant.DESCRIPTION, des);
                    AdminPresenter addPresenter = new AdminPresenter(AdminActivity.this, new IView() {
                        @Override
                        public void getData(int code, Object object) {
                            if (code == 201) {
                                addTeaClass = (TeaClass) object;
                                handler.sendEmptyMessage(0x890);
                            }
                        }
                    });
                    addPresenter.postAddClass(map);
                }
            }
        });
    }

    private long exitTime = 0;

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

