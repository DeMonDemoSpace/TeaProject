package tea.demon.com.teaproject.User;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import tea.demon.com.teaproject.Adapter.StoreListAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.activity.LoginActivity;
import tea.demon.com.teaproject.activity.PutPassWordActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Store;
import tea.demon.com.teaproject.data.UserInfo;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private View user_header;
    private CircleImageView iv_pic;
    private TextView tv_userName;
    private UserInfo userInfo;
    public static Handler handler;
    public static UserActivity activity;
    private List<Store> list;
    private StoreListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        activity = this;
        ButterKnife.bind(this);
        TitleUtil.setDrawToolBar(this, getString(R.string.user), drawer);

        initView();
        initHandler();
    }


    private void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        navView.setNavigationItemSelectedListener(this);
        user_header = navView.getHeaderView(0);
        iv_pic = (CircleImageView) user_header.findViewById(R.id.iv_pic);
        tv_userName = (TextView) user_header.findViewById(R.id.tv_userName);

        UserPresenter presenter = new UserPresenter(this, new IView() {
            @Override
            public void getData(int code, Object object) {
                if (code == 200) {
                    list = (List<Store>) object;
                    handler.sendEmptyMessage(0x003);
                }
            }
        });
        presenter.getStoreList();
    }

    private void initUserInfo() {
        UserPresenter presenter = new UserPresenter(this, new IView() {
            @Override
            public void getData(int code, Object object) {
                if (code == 200) {
                    userInfo = (UserInfo) object;
                    handler.sendEmptyMessage(0x001);
                }
            }
        });
        presenter.getUserInfo();
        tv_userName.setText("DeMon");
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    GlideUtils.setImage(UserActivity.this, userInfo.getAvatar(), iv_pic);
                    tv_userName.setText(userInfo.getName());
                } else if (msg.what == 0x002) {
                    UserInfo user = (UserInfo) msg.obj;
                    GlideUtils.setImage(UserActivity.this, user.getAvatar(), iv_pic);
                    tv_userName.setText(user.getName());
                } else if (msg.what == 0x003) {
                    adapter = new StoreListAdapter(UserActivity.this, list);
                    recycleView.setAdapter(adapter);
                    initUserInfo();
                }
            }
        };

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_order) {//订单管理
            startActivity(new Intent(UserActivity.this, OrderListActivity.class));
        } else if (id == R.id.nav_editInfo) {//修改信息
            Intent intent = new Intent();
            intent.putExtra(Constant.USERINFO, userInfo);
            intent.setClass(this, EditUserInfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_address) {//地址管理
            startActivity(new Intent(this, AddressActivity.class));
        } else if (id == R.id.nav_approve) {//认证
            startActivity(new Intent(this, ApproveActivity.class));
        } else if (id == R.id.nav_changepass) {//修改密码
            startActivity(new Intent(this, PutPassWordActivity.class));
        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
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

}
