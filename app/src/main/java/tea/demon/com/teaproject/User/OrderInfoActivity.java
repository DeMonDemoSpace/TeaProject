package tea.demon.com.teaproject.User;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.Adapter.OrderInfoAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Order;
import tea.demon.com.teaproject.data.OrderList;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class OrderInfoActivity extends AppCompatActivity implements IView {

    @BindView(R.id.et_no)
    EditText etNo;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.et_pay)
    EditText etPay;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private OrderList orderlist;
    private OrderInfoAdapter adapter;
    private List<Order> list;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        TitleUtil.setToolBar(this, getString(R.string.orderInfo));
        ButterKnife.bind(this);
        orderlist = (OrderList) getIntent().getSerializableExtra(Constant.ORDER);
        initView();
        initHandler();
    }

    private void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        etNo.setText(orderlist.getOrder_no());
        etTime.setText(orderlist.getCreated_at());
        etPay.setText(orderlist.getPay());
        Map<String, String> map = new HashMap<>();
        map.put(Constant.ORDER_ID, orderlist.getOrder_id() + "");
        UserPresenter presenter = new UserPresenter(this, this);
        if (Constant.IDENTITY == 0){
            presenter.getOrderInfo(map);
        }else {
            presenter.getOrderInfoAdmin(map);
        }

    }


    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    adapter = new OrderInfoAdapter(OrderInfoActivity.this, list);
                    recycleView.setAdapter(adapter);
                }
            }
        };
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 200) {
            list = (List<Order>) object;
            handler.sendEmptyMessage(0x001);
        }
    }
}
