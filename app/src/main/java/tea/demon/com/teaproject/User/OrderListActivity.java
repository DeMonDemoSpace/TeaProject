package tea.demon.com.teaproject.User;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.Adapter.OrderListAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.OrderList;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class OrderListActivity extends AppCompatActivity implements IView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private OrderListAdapter adapter;
    private List<OrderList> list;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_lsit);
        TitleUtil.setToolBar(this, getString(R.string.orderManage));
        ButterKnife.bind(this);
        initView();
        initHandler();
    }


    private void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        Map<String, String> map = new HashMap<>();
        map.put(Constant.STATUS, -1 + "");
        UserPresenter presenter = new UserPresenter(this, this);
        if (Constant.IDENTITY == 0){
            presenter.getOrderList(map);
        }else {
            presenter.getOrderListAdmin(map);
        }

    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    adapter = new OrderListAdapter(OrderListActivity.this, list);
                    recycleView.setAdapter(adapter);
                }
            }
        };
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 200) {
            list = (List<OrderList>) object;
            handler.sendEmptyMessage(0x001);
        }
    }
}
