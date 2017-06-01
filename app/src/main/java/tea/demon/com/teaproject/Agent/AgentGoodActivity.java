package tea.demon.com.teaproject.Agent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Coupon;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.presenter.AgentPresenter;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.AddDialogView;
import tea.demon.com.teaproject.view.IView;

public class AgentGoodActivity extends AppCompatActivity implements IView {

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
    @BindView(R.id.coupon_fab)
    FloatingActionButton couponFab;
    int type = 0;
    @BindView(R.id.et_coupon)
    EditText etCoupon;
    private Tea tea;
    private Handler handler;
    private List<Coupon> list;
    private String coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agentgood);
        ButterKnife.bind(this);
        tea = (Tea) getIntent().getSerializableExtra(Constant.TEA);
        TitleUtil.setToolBar(this, tea.getName());
        initView();
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    couponFab.setVisibility(View.GONE);
                    etCoupon.setText(coupon);
                    ToastUtil.ShowToast(AgentGoodActivity.this, "添加优惠券成功！");
                } else if (msg.what == 0x002) {
                    if (list.size() > 0) {
                        couponFab.setVisibility(View.GONE);
                        Coupon coupon = list.get(0);
                        if (coupon.getType() == 1) {
                            double dis = Double.parseDouble(coupon.getDiscount()) * 10;
                            String coupon_text = coupon.getName() + ":" + dis + "折";
                            etCoupon.setText(coupon_text);
                        } else {
                            String coupon_text = coupon.getName() + ":" + coupon.getGift();
                            etCoupon.setText(coupon_text);
                        }
                    } else {
                        etCoupon.setText(getString(R.string.nowno));
                    }
                }
            }
        };
    }

    private void initView() {
        GlideUtils.setImage(this, tea.getThumb(), ivPic);
        etPrice.setText(tea.getPrice() + "");
        etStock.setText(tea.getStock() + "");
        etSoldAmount.setText(tea.getSold_amount() + "");
        etDescription.setText(tea.getDescription());
        Map<String, String> map = new HashMap<>();
        map.put(Constant.GOODS_ID, tea.getGoods_id() + "");
        UserPresenter presenter = new UserPresenter(this, new IView() {
            @Override
            public void getData(int code, Object object) {
                list = (List<Coupon>) object;
                handler.sendEmptyMessage(0x002);
            }
        });
        presenter.getGoodCoupon(map);

    }


    @OnClick(R.id.coupon_fab)
    public void onViewClicked() {
        initDialog();
    }

    private void initDialog() {
        View view = View.inflate(this, R.layout.edit_coupon, null);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_discount = (EditText) view.findViewById(R.id.et_discount);
        final EditText et_gift = (EditText) view.findViewById(R.id.et_gift);
        RadioButton discount = (RadioButton) view.findViewById(R.id.rbtn_discount);
        RadioButton gift = (RadioButton) view.findViewById(R.id.rbtn_gift);
        discount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = 1;
                    et_discount.setVisibility(View.VISIBLE);
                    et_gift.setVisibility(View.GONE);
                }
            }
        });
        gift.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    type = 2;
                    et_discount.setVisibility(View.GONE);
                    et_gift.setVisibility(View.VISIBLE);
                }
            }
        });
        Dialogutil.addDialogBuilder(this, getString(R.string.addcoupon), view, new AddDialogView() {
            @Override
            public void add() {
                String name = et_name.getText().toString();
                String discount = et_discount.getText().toString();
                String gift = et_gift.getText().toString();
                Map<String, String> map = new HashMap<String, String>();
                map.put(Constant.GOODS_ID, tea.getGoods_id() + "");
                if (type == 1) {
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(discount)) {
                        ToastUtil.ShowToast(AgentGoodActivity.this, getString(R.string.needInfo));
                    } else {
                        map.put(Constant.NAME, name);
                        map.put(Constant.DISCOUNT, discount);
                    }
                    double dis = Double.parseDouble(discount) * 10;
                    coupon = name + ":" + dis + "折";
                } else {
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(gift)) {
                        ToastUtil.ShowToast(AgentGoodActivity.this, getString(R.string.needInfo));
                    } else {
                        map.put(Constant.NAME, name);
                        map.put(Constant.GIFT, gift);
                    }
                    coupon = name + ":" + gift;
                }

                map.put(Constant.TYPE, type + "");
                AgentPresenter presenter = new AgentPresenter(AgentGoodActivity.this, AgentGoodActivity.this);
                presenter.postCoupon(map);
            }
        });
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 201) {
            handler.sendEmptyMessage(0x001);
        }

    }
}
