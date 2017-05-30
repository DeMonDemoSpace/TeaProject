package tea.demon.com.teaproject.User;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.Adapter.GoodsAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class GoodsActivity extends AppCompatActivity implements IView {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    private int store_Id;
    private List<Tea> list;
    private Handler handler;
    private GoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        TitleUtil.setToolBar(this, getIntent().getStringExtra(Constant.STORE_NAME));
        ButterKnife.bind(this);
        store_Id = getIntent().getIntExtra(Constant.STORE_ID, -1);

        initView();
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    adapter = new GoodsAdapter(GoodsActivity.this, list);
                    recycleView.setAdapter(adapter);
                }
            }
        };
    }

    private void initView() {
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(sgm);
        Map<String, String> map = new HashMap<>();
        map.put(Constant.STORE_ID, store_Id + "");
        UserPresenter presenter = new UserPresenter(this, this);
        presenter.getGoodsByStore(map);
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 200) {
            list = (List<Tea>) object;
            handler.sendEmptyMessage(0x001);
        }
    }
}
