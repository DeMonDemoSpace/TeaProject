package tea.demon.com.teaproject.Adapter;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.User.AddressActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.UserAddress;
import tea.demon.com.teaproject.presenter.UserPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.view.AddDialogView;
import tea.demon.com.teaproject.view.DelDialogView;
import tea.demon.com.teaproject.view.IView;

/**
 * Created by D&LL on 2017/5/26.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private List<UserAddress> list;
    private Context context;

    public AddressAdapter(Context context, List<UserAddress> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_address, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        initOnClick(holder);//按钮事件
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserAddress address = list.get(position);
        holder.tvName.setText(address.getName());
        holder.tvPhone.setText(address.getPhone());
        holder.tvAddress.setText(address.getContent());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_edit)
        ImageView ivEdit;
        View views;

        ViewHolder(View view) {
            super(view);
            views = view;
            ButterKnife.bind(this, view);
        }
    }

    private void initOnClick(final ViewHolder holder) {
        holder.views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                holder.ivDelete.setVisibility(View.GONE);
                holder.ivEdit.setVisibility(View.GONE);
                Message message = new Message();
                message.what = 0x004;
                message.obj = pos;
                AddressActivity.handler.sendMessage(message);
            }
        });
        holder.views.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.ivDelete.setVisibility(View.VISIBLE);
                holder.ivEdit.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogutil.delDialogBuilder(context, new DelDialogView() {
                    @Override
                    public void delete() {
                        holder.ivDelete.setVisibility(View.GONE);
                        holder.ivEdit.setVisibility(View.GONE);
                        final int delpos = holder.getAdapterPosition();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(Constant.ADDRESS_ID, list.get(delpos).getAddress_id() + "");
                        UserPresenter presenter = new UserPresenter(context, new IView() {
                            @Override
                            public void getData(int code, Object object) {
                                if (code == 204) {
                                    list.remove(delpos);
                                    AddressActivity.handler.sendEmptyMessage(0x003);
                                }
                            }
                        });
                        presenter.delAddress(map);
                    }
                });
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int editpos = holder.getAdapterPosition();
                final UserAddress editaddress = list.get(editpos);
                View addview = View.inflate(context, R.layout.edit_address, null);
                final EditText et_name = (EditText) addview.findViewById(R.id.et_name);
                final EditText et_phone = (EditText) addview.findViewById(R.id.et_phone);
                final EditText et_address = (EditText) addview.findViewById(R.id.et_address);
                et_name.setText(editaddress.getName());
                et_phone.setText(editaddress.getPhone());
                et_address.setText(editaddress.getContent());
                Dialogutil.addDialogBuilder(context, context.getString(R.string.putAddress), addview, new AddDialogView() {
                    @Override
                    public void add() {
                        final String name = et_name.getText().toString();
                        final String phone = et_phone.getText().toString();
                        final String address = et_address.getText().toString();
                        holder.ivDelete.setVisibility(View.GONE);
                        holder.ivEdit.setVisibility(View.GONE);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(Constant.ADDRESS_ID, list.get(editpos).getAddress_id() + "");
                        map.put(Constant.NAME, name);
                        map.put(Constant.PHONE, phone);
                        map.put(Constant.CONTENT, address);
                        UserPresenter presenter = new UserPresenter(context, new IView() {
                            @Override
                            public void getData(int code, Object object) {
                                if (code == 201) {
                                    editaddress.setName(name);
                                    editaddress.setPhone(phone);
                                    editaddress.setContent(address);
                                    AddressActivity.handler.sendEmptyMessage(0x003);
                                }
                            }
                        });
                        presenter.putAddress(map);
                    }
                });
            }
        });
    }
}
