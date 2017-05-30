package tea.demon.com.teaproject.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.BuyGood;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Coupon;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.AddDialogView;
import tea.demon.com.teaproject.view.IView;

public class GoodsInfoActivity extends AppCompatActivity implements IView {

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.et_coupon)
    EditText etCoupon;
    @BindView(R.id.buy_fab)
    FloatingActionButton buyFab;
    @BindView(R.id.car_fab)
    FloatingActionButton carFab;

    private Tea tea;
    private List<Coupon> list;
    private Coupon coupon;
    private String coupon_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        tea = (Tea) getIntent().getSerializableExtra(Constant.TEA);
        TitleUtil.setToolBar(this, tea.getName());
        initView();
    }

    private void initView() {
        GlideUtils.setImage(this, tea.getThumb(), ivPic);
        etPrice.setText(tea.getPrice() + "");
        etDescription.setText(tea.getDescription());
        Map<String, String> map = new HashMap<>();
        map.put(Constant.GOODS_ID, tea.getGoods_id() + "");
        UserPresenter presenter = new UserPresenter(this, this);
        presenter.getGoodCoupon(map);
    }


    @Override
    public void getData(int code, Object object) {
        if (code == 200) {
            list = (List<Coupon>) object;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (list.size() > 0) {
                        coupon = list.get(0);
                        if (coupon.getType() == 1) {
                            double dis = Double.parseDouble(coupon.getDiscount()) * 10;
                            coupon_text = coupon.getName() + ":" + dis + "折";
                            etCoupon.setText(coupon_text);
                        } else {
                            coupon_text = coupon.getName() + ":" + coupon.getGift();
                            etCoupon.setText(coupon_text);
                        }
                    } else {
                        coupon = new Coupon(-1, 0, "", "", "");
                        coupon_text = getString(R.string.nowno);
                        etCoupon.setText(getString(R.string.nowno));
                    }
                }
            });
        }
    }

    @OnClick({R.id.buy_fab, R.id.car_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buy_fab:
                initDialog();
                break;
            case R.id.car_fab:
                startActivity(new Intent(this, ShoppingCarActivity.class));
                break;
        }
    }

    private void initDialog() {
        View view = View.inflate(this, R.layout.edit_buygood, null);
        final EditText et_num = (EditText) view.findViewById(R.id.et_num);
        Dialogutil.addDialogBuilder(this, getString(R.string.addcar), view, new AddDialogView() {
            @Override
            public void add() {
                String num = et_num.getText().toString();
                if (TextUtils.isEmpty(num)) {
                    ToastUtil.ShowToast(GoodsInfoActivity.this, "请输入购买数量！");
                } else {
                    int number = Integer.parseInt(num);
                    BuyGood good = new BuyGood(tea.getName(), tea.getPrice(),
                            coupon_text, tea.getGoods_id(), coupon.getCoupon_id(), number);
                    ShoppingCarActivity.list.add(good);
                    ToastUtil.ShowToast(GoodsInfoActivity.this, "已加入购物车！");
                }
            }
        });
    }
}
