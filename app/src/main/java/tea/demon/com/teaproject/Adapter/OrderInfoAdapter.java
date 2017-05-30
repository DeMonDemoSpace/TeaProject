package tea.demon.com.teaproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Order;
import tea.demon.com.teaproject.util.GlideUtils;

/**
 * Created by D&LL on 2017/5/25.
 */

public class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoAdapter.ViewHolder> {
    private List<Order> list;
    private Context context;

    public OrderInfoAdapter(Context context, List<Order> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_orderinfolist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Order order = list.get(position);
        GlideUtils.setImage(context, order.getThumb(), holder.ivPic);
        holder.tvName.setText("茶叶:" + order.getName());
        holder.tvPrice.setText("￥:" + order.getPrice());
        holder.tvAmount.setText("数量:" + order.getAmount() + "");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_amount)
        TextView tvAmount;
        View views;

        public ViewHolder(View view) {
            super(view);
            views = view;
            ButterKnife.bind(this, view);
        }
    }
}
