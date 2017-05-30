package tea.demon.com.teaproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.User.GoodsActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Store;

/**
 * Created by D&LL on 2017/5/25.
 */

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {
    private List<Store> list;
    private Context context;

    public StoreListAdapter(Context context, List<Store> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_storelist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent();
                intent.putExtra(Constant.STORE_ID, list.get(pos).getStore_id());
                intent.putExtra(Constant.STORE_NAME, list.get(pos).getName());
                intent.setClass(context, GoodsActivity.class);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Store store = list.get(position);
        holder.tvName.setText(store.getName());
        holder.tvPhone.setText("手机:" + store.getPhone());
        holder.tvDes.setText("地址:" + store.getDescription());
        holder.tvAddress.setText("简介:" + store.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_des)
        TextView tvDes;
        View views;

        public ViewHolder(View view) {
            super(view);
            views = view;
            ButterKnife.bind(this, view);
        }
    }
}
