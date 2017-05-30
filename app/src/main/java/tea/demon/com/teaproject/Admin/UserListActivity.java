package tea.demon.com.teaproject.Admin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.Adapter.UserListAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.User;
import tea.demon.com.teaproject.presenter.AdminPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class UserListActivity extends AppCompatActivity implements IView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private UserListAdapter adapter;
    private List<User> list;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        TitleUtil.setToolBar(this, getString(R.string.userManage));
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        AdminPresenter presenter = new AdminPresenter(this, this);
        presenter.getUserList();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x234) {
                    adapter = new UserListAdapter(UserListActivity.this, list);
                    recycleView.setAdapter(adapter);
                }
            }
        };


    }

    @Override
    public void getData(int code, Object object) {
        list = (List<User>) object;
        handler.sendEmptyMessage(0x234);
    }
}
