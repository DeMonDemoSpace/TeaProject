package tea.demon.com.teaproject.Agent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.Adapter.CouponListAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Coupon;
import tea.demon.com.teaproject.presenter.AgentPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class CouponActivity extends AppCompatActivity implements IView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private List<Coupon> list;
    private CouponListAdapter adapter;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        TitleUtil.setToolBar(this,getString(R.string.couponManage));
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        AgentPresenter presenter = new AgentPresenter(this, this);
        presenter.getCoupon();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x234) {
                    adapter = new CouponListAdapter(CouponActivity.this, list);
                    recycleView.setAdapter(adapter);
                } else if (msg.what == 0x001) {
                    adapter.notifyDataSetChanged();
                }
            }
        };


    }

    @Override
    public void getData(int code, Object object) {
        list = (List<Coupon>) object;
        handler.sendEmptyMessage(0x234);
    }
}
