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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tea.demon.com.teaproject.Adapter.AddressAdapter;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.UserAddress;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.util.TitleUtil;
import tea.demon.com.teaproject.view.AddDialogView;
import tea.demon.com.teaproject.view.IView;

public class AddressActivity extends AppCompatActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    public static Handler handler;
    public static List<UserAddress> list;
    private AddressAdapter adapter;
    private int createId;//新建地址的Id
    private UserAddress putaddress;//新建地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        TitleUtil.setToolBar(this, getString(R.string.goodaddress));
        ButterKnife.bind(this);
        initView();
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x001) {
                    adapter = new AddressAdapter(AddressActivity.this, list);
                    recycleView.setAdapter(adapter);
                } else if (msg.what == 0x002) {
                    list.add(putaddress);
                    adapter.notifyDataSetChanged();
                } else if (msg.what == 0x003) {
                    adapter.notifyDataSetChanged();
                } else if (msg.what == 0x004) {
                    int pos = (int) msg.obj;
                    Intent intent = new Intent();
                    intent.putExtra(Constant.ADDRESS, list.get(pos));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private void initView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        UserPresenter presenter = new UserPresenter(this, new IView() {
            @Override
            public void getData(int code, Object object) {
                if (code == 200) {
                    list = (List<UserAddress>) object;
                    handler.sendEmptyMessage(0x001);
                }
            }
        });
        presenter.getAddress();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        View addview = View.inflate(this, R.layout.edit_address, null);
        final EditText et_name = (EditText) addview.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) addview.findViewById(R.id.et_phone);
        final EditText et_address = (EditText) addview.findViewById(R.id.et_address);
        Dialogutil.addDialogBuilder(this, getString(R.string.addAddress), addview, new AddDialogView() {
            @Override
            public void add() {
                final String name = et_name.getText().toString();
                final String phone = et_phone.getText().toString();
                final String address = et_address.getText().toString();
                Map<String, String> map = new HashMap<String, String>();
                map.put(Constant.NAME, name);
                map.put(Constant.PHONE, phone);
                map.put(Constant.CONTENT, address);
                UserPresenter presenter = new UserPresenter(AddressActivity.this, new IView() {
                    @Override
                    public void getData(int code, Object object) {
                        if (code == 201) {
                            createId = (int) object;
                            putaddress = new UserAddress(name, phone, address, createId);
                            handler.sendEmptyMessage(0x002);
                        }
                    }
                });
                presenter.createAddress(map);
            }

        });
    }
}
