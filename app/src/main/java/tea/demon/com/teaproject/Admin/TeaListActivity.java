package tea.demon.com.teaproject.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.Adapter.TeaListAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.presenter.TeaPresenter;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.IView;

public class TeaListActivity extends AppCompatActivity implements IView {
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private int classId;
    public static List<Tea> list;
    public static Handler handler;
    private TeaListAdapter adapter;
    private Map<String, String> movemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_list);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra(Constant.TITLE);
        TitleUtil.setToolBar(this, title);
        classId = getIntent().getIntExtra(Constant.POS, -1);

        initView();
        initData();
        initHandler();
    }


    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x000) {
                    adapter = new TeaListAdapter(TeaListActivity.this, list);
                    recycleView.setAdapter(adapter);
                } else if (msg.what == 0x002) {
                    adapter.notifyDataSetChanged();
                } else if (msg.what == 0x003) {
                    movemap = (Map<String, String>) msg.obj;
                    Intent intent = new Intent();
                    intent.putExtra(Constant.MOVE, Constant.REQUEST_MOVE);
                    intent.setClass(TeaListActivity.this, AdminActivity.class);
                    startActivityForResult(intent, Constant.REQUEST_MOVE);
                } else if (msg.what == 0x004) {
                    adapter.notifyDataSetChanged();
                } else if (msg.what == 0x005) {
                    list.clear();//编辑茶叶信息后，清空茶叶列表，重新获取数据
                    final Map<String, String> map = new HashMap<>();
                    map.put(Constant.CLASS_ID, classId + "");
                    TeaPresenter presenter = new TeaPresenter(TeaListActivity.this, new IView() {
                        @Override
                        public void getData(int code, Object object) {
                            if (code == 200) {
                                list = (List<Tea>) object;
                                System.out.println(list.size());
                                handler.sendEmptyMessage(0x006);
                            }
                        }
                    });
                    presenter.getTeaByClass(map);
                } else if (msg.what == 0x006) {
                    //更新编辑茶叶信息后的茶叶列表
                    adapter = new TeaListAdapter(TeaListActivity.this, list);
                    recycleView.setAdapter(adapter);
                }
            }
        };
    }

    private void initView() {
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(sgm);
    }


    private void initData() {
        final Map<String, String> map = new HashMap<>();
        map.put(Constant.CLASS_ID, classId + "");
        TeaPresenter presenter = new TeaPresenter(this, this);
        presenter.getTeaByClass(map);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        //创建茶叶后附带data返回该界面
        Intent intent = new Intent();
        intent.setClass(this, CreateTeaActivity.class);
        intent.putExtra(Constant.CLASS_ID, classId);
        startActivityForResult(intent, Constant.CREATE_TEA);
    }

    @Override
    public void getData(int code, Object object) {
        if (code == 200) {
            list = (List<Tea>) object;
            handler.sendEmptyMessage(0x000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.CREATE_TEA:
                    Tea tea = (Tea) data.getSerializableExtra(Constant.TEA);
                    list.add(tea);
                    adapter.notifyDataSetChanged();
                    break;
                case Constant.REQUEST_MOVE:
                    final int classId = data.getIntExtra(Constant.MOVE, -1);
                    movemap.put(Constant.CLASS_ID, classId + "");
                    TeaPresenter presenter = new TeaPresenter(this, new IView() {
                        @Override
                        public void getData(int code, Object object) {
                            if (code == 201) {
                                handler.sendEmptyMessage(0x004);
                            }
                        }
                    });
                    presenter.putTeaClass(movemap);
            }
        }
    }
}
