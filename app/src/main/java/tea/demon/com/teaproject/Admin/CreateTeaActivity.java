package tea.demon.com.teaproject.Admin;

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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.presenter.TeaPresenter;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.PicUtil;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.IView;

public class CreateTeaActivity extends AppCompatActivity implements IView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_stock)
    EditText etStock;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.bt_sure)
    Button btSure;
    private int classId, goodId = -1;
    private String imgPath = "";
    private Bitmap picBitmap, curBitmap;
    private Handler handler;
    private Map<String, String> map = new HashMap<>();
    private Tea tea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tea);
        ButterKnife.bind(this);
        TitleUtil.setToolBar(this, getString(R.string.createTea));
        classId = getIntent().getIntExtra(Constant.CLASS_ID, -1);
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    tea.setGoods_id(goodId);
                    Intent intent = new Intent();
                    intent.putExtra(Constant.TEA, tea);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    @OnClick({R.id.iv_add, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                //跳转到选择图片界面
                startActivityForResult(new Intent("android.intent.action.PICK",
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Constant.PICK_PIC);
                break;
            case R.id.bt_sure:
                initUpdate();
                break;
        }
    }

    private void initUpdate() {
        String name = etName.getText().toString();
        String price = etPrice.getText().toString();
        String stock = etStock.getText().toString();
        String des = etDescription.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(stock) || TextUtils.isEmpty(des) || TextUtils.isEmpty(imgPath)) {
            ToastUtil.ShowToast(this, getString(R.string.needInfo));
            return;
        } else {
            picBitmap = PicUtil.compressBitmap(curBitmap);//压缩
            String baseString = PicUtil.getBase64(picBitmap);
            map.put(Constant.NAME, name);
            map.put(Constant.PRICE, price);
            map.put(Constant.STOCK, stock);
            map.put(Constant.DESCRIPTION, des);
            map.put(Constant.THUMB, baseString);
            map.put(Constant.CLASS_ID, classId + "");
            double prices = Double.parseDouble(price);
            int stocks = Integer.parseInt(stock);
            tea = new Tea(goodId, -1, stocks, 0, prices, name, des, imgPath + "");
            TeaPresenter teaPresenter = new TeaPresenter(this, this);
            teaPresenter.postCreateTea(map);
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
            goodId = (int) object;
            handler.sendEmptyMessage(0x001);
        }
    }


}
