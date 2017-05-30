package tea.demon.com.teaproject.User;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.Adapter.BuyCarAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.BuyGood;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.UserAddress;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.util.JsonUtil;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.util.ToastUtil;
import tea.demon.com.teaproject.view.AddDialogView;
import tea.demon.com.teaproject.view.IView;

public class ShoppingCarActivity extends AppCompatActivity implements IView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    public static List<BuyGood> list = new ArrayList<>();
    public static Handler handler;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.buy_fab)
    FloatingActionButton buyFab;
    @BindView(R.id.address_fab)
    FloatingActionButton addressFab;
    private BuyCarAdapter adapter;
    public static double sum = 0;
    private int address_id = -1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_car);
        TitleUtil.setToolBar(this, getString(R.string.shoppingcar));
        ButterKnife.bind(this);
        initView();
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    adapter.notifyDataSetChanged();
                }
            }
        };
    }

    private void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BuyCarAdapter(this, list);
        recycleView.setAdapter(adapter);
    }

    @OnClick(R.id.buy_fab)
    public void onViewClicked() {
    }

    @OnClick({R.id.buy_fab, R.id.address_fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buy_fab:
                if (address_id == -1) {
                    ToastUtil.ShowToast(this, "请选择收货地址！");
                } else if (list.size() == 0) {
                    ToastUtil.ShowToast(this, "请选择商品！");
                } else {
                    String s = JsonUtil.buyInfoJson(list, address_id);
                    initDialog(s);

                }

                break;
            case R.id.address_fab:
                Intent intent = new Intent(this, AddressActivity.class);
                startActivityForResult(intent, Constant.PICK_ADDRESS);
                break;
        }

    }

    private void initDialog(final String s) {
        View view = View.inflate(this, R.layout.edit_buycar, null);
        final EditText et_sum = (EditText) view.findViewById(R.id.et_sum);
        et_sum.setText(sum + "");
        Dialogutil.addDialogBuilder(this, getString(R.string.pay), view, new AddDialogView() {
            @Override
            public void add() {
                UserPresenter presenter = new UserPresenter(ShoppingCarActivity.this, ShoppingCarActivity.this);
                presenter.postBuyInfo(s);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.PICK_ADDRESS:
                    UserAddress address = (UserAddress) data.getSerializableExtra(Constant.ADDRESS);
                    address_id = address.getAddress_id();
                    etAddress.setText(address.getName() + "," + address.getPhone() + "," + address.getContent());
            }
        }
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 201) {
            list.clear();
            handler.sendEmptyMessage(0x001);
        }
    }
}
