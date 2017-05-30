package tea.demon.com.teaproject.User;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Api;
import tea.demon.com.teaproject.data.Approve;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.IView;

public class ApproveActivity extends AppCompatActivity implements IView {

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_status)
    EditText etStatus;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private Approve approve;
    public static Handler handler;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);
        ButterKnife.bind(this);
        TitleUtil.setToolBar(this, getString(R.string.approveManage));
        initView();
        initHandler();
    }


    private void initView() {
        etStatus.setText(getString(R.string.noapprove));
        UserPresenter presenter = new UserPresenter(this, this);
        presenter.getUpgrade();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    GlideUtils.setImage(ApproveActivity.this, Api.http + approve.getLicence(), ivPic);
                    etName.setText(approve.getName());
                    etPhone.setText(approve.getPhone());
                    status = approve.getCheck_status();
                    if (status == 2) {
                        etStatus.setText(getString(R.string.checking));
                    } else if (status == 0) {
                        etStatus.setText(getString(R.string.failapprove));
                    }
                }
            }
        };
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        if (status == 2) {
            ToastUtil.ShowToast(this, "已认证，请等候审核结果！");
        } else {
            startActivityForResult(new Intent(this, EditApproveActivity.class), Constant.APPROVE_DO);
        }
    }

    @Override
    public void getData(int code, Object object) {
        approve = (Approve) object;
        handler.sendEmptyMessage(0x001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.APPROVE_DO:
                    Approve approve = (Approve) data.getSerializableExtra(Constant.APPROVE);
                    etName.setText(approve.getName());
                    etPhone.setText(approve.getPhone());
                    GlideUtils.setImage(ApproveActivity.this, approve.getLicence(), ivPic);
                    etStatus.setText(getString(R.string.checking));
                    status = approve.getCheck_status();
                    break;
            }
        }
    }
}
