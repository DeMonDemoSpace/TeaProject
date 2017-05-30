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
import tea.demon.com.teaproject.User.OrderInfoActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.OrderList;

/**
 * Created by D&LL on 2017/5/25.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private List<OrderList> list;
    private Context context;

    public OrderListAdapter(Context context, List<OrderList> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_orderlist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent();
                intent.putExtra(Constant.ORDER,list.get(pos));
                intent.setClass(context, OrderInfoActivity.class);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderList order = list.get(position);
        holder.tvNo.setText("订单编号:" + order.getOrder_no());
        holder.tvTime.setText("时间:" + order.getCreated_at());
        holder.tvPay.setText("￥:" + order.getPay());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_no)
        TextView tvNo;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_pay)
        TextView tvPay;
        View views;

        public ViewHolder(View view) {
            super(view);
            views = view;
            ButterKnife.bind(this, view);
        }
    }
}
