package tea.demon.com.teaproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.TitleUtil;

public class TeaInfoActivity extends AppCompatActivity {

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_stock)
    EditText etStock;
    @BindView(R.id.et_soldAmount)
    EditText etSoldAmount;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.edit_fab)
    FloatingActionButton editFab;
    @BindView(R.id.up_fab)
    FloatingActionButton upFab;
    private Tea tea;
    public static TeaInfoActivity activity;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_info);
        ButterKnife.bind(this);
        activity = this;
        tea = (Tea) getIntent().getSerializableExtra(Constant.TEA);
        TitleUtil.setToolBar(this, tea.getName());
        initView();
        initHandler();
    }

    private void initHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                upFab.setVisibility(View.GONE);
            }
        };
    }

    private void initView() {
        GlideUtils.setImage(this, tea.getThumb(), ivPic);
        etPrice.setText(tea.getPrice() + "");
        etStock.setText(tea.getStock() + "");
        etSoldAmount.setText(tea.getSold_amount() + "");
        etDescription.setText(tea.getDescription());
        if (tea.getStore_id() != 0) {
            upFab.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.up_fab, R.id.edit_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.up_fab:
                Intent upintent = new Intent();
                upintent.putExtra(Constant.GOODS_ID, tea.getGoods_id());
                upintent.setClass(this, AgentListActivity.class);
                startActivity(upintent);
                break;
            case R.id.edit_fab:
                Intent intent = new Intent();
                intent.putExtra(Constant.TEA, tea);
                intent.setClass(this, EditTeaActivity.class);
                startActivity(intent);
                break;
        }
    }
}
