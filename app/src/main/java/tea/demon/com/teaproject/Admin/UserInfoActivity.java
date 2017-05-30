package tea.demon.com.teaproject.Admin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.User;
import tea.demon.com.teaproject.presenter.AdminPresenter;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class UserInfoActivity extends AppCompatActivity implements IView {

    @BindView(R.id.icon)
    CircleImageView icon;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_userId)
    EditText etUserId;
    @BindView(R.id.et_email)
    EditText etEmail;
    private User user;
    private Handler handler;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        TitleUtil.setToolBar(this, getString(R.string.userDetail));

        userId = getIntent().getIntExtra(Constant.USERID, -1);
        initData();

    }

    private void initData() {
        AdminPresenter presenter = new AdminPresenter(this, this);
        Map<String, String> map = new HashMap<>();
        map.put(Constant.USERID, userId + "");
        presenter.getUserInfo(map);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x345) {
                    GlideUtils.setImage(UserInfoActivity.this, user.getAvator(), icon);
                    etUserId.setText(user.getUsername());
                    etEmail.setText(user.getEmail());
                    etName.setText(user.getName());
                }
            }
        };
    }

    @Override
    public void getData(int code, Object object) {
        user = (User) object;
        handler.sendEmptyMessage(0x345);
    }
}
