package tea.demon.com.teaproject.User;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.UserInfo;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.PicUtil;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class EditUserInfoActivity extends AppCompatActivity implements IView {

    @BindView(R.id.iv_pic)
    CircleImageView ivPic;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.bn_sure)
    Button bnSure;
    private UserInfo userInfo;
    private String imgPath = null, base64 = "";
    private Bitmap curBitmap, bitmap;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        TitleUtil.setToolBar(this, getString(R.string.editinfo));
        ButterKnife.bind(this);
        userInfo = (UserInfo) getIntent().getSerializableExtra(Constant.USERINFO);
        initView();
        initHandler();
    }


    private void initView() {
        GlideUtils.setImage(this, userInfo.getAvatar(), ivPic);
        etUsername.setText(userInfo.getName());
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    UserInfo user;
                    if (imgPath == null) {
                        user = new UserInfo(etUsername.getText().toString() + "", userInfo.getAvatar(), userInfo.getUserId());
                    } else {
                        user = new UserInfo(etUsername.getText().toString() + "", imgPath, userInfo.getUserId());
                    }
                    Message message = new Message();
                    message.obj = user;
                    message.what = 0x002;
                    UserActivity.handler.sendMessage(message);
                    finish();
                }
            }
        };

    }

    @OnClick({R.id.iv_pic, R.id.bn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pic:
                //跳转到选择图片界面
                startActivityForResult(new Intent("android.intent.action.PICK",
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Constant.PICK_PIC);
                break;
            case R.id.bn_sure:
                Map<String, String> map = new HashMap<>();
                map.put(Constant.NAME, etUsername.getText().toString() + "");
                map.put(Constant.AVATAR, base64 + "");
                UserPresenter presenter = new UserPresenter(this, this);
                presenter.putUserInfo(map);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.PICK_PIC:
                    if (data != null) {
                        Uri uri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgPath = cursor.getString(columnIndex);
                        System.out.println(imgPath);
                        GlideUtils.setImage(this, imgPath, ivPic);
                        ContentResolver cr = this.getContentResolver();
                        try {
                            curBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                            bitmap = PicUtil.compressBitmap(curBitmap);
                            base64 = PicUtil.getBase64(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

        }
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 201) {
            handler.sendEmptyMessage(0x123);
        }
    }
}
