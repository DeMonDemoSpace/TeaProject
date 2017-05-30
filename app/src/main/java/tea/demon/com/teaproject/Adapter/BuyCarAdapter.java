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
import tea.demon.com.teaproject.User.ShoppingCarActivity;
import tea.demon.com.teaproject.data.BuyGood;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.view.DelDialogView;

/**
 * Created by D&LL on 2017/5/25.
 */

public class BuyCarAdapter extends RecyclerView.Adapter<BuyCarAdapter.ViewHolder> {

    private List<BuyGood> list;
    private Context context;

    public BuyCarAdapter(Context context, List<BuyGood> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_buycarlist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = holder.getAdapterPosition();
                Dialogutil.delDialogBuilder(context, new DelDialogView() {
                    @Override
                    public void delete() {
                        list.remove(pos);
                        ShoppingCarActivity.handler.sendEmptyMessage(0x001);
                    }
                });
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BuyGood good = list.get(position);
        holder.tvName.setText(good.getName());
        holder.tvGoods.setText(good.getPrice() + "*" + good.getAmount());
        holder.tvCoupon.setText(good.getCoupon());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_goods)
        TextView tvGoods;
        @BindView(R.id.tv_coupon)
        TextView tvCoupon;
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        View views;

        public ViewHolder(View view) {
            super(view);
            views = view;
            ButterKnife.bind(this, view);
        }
    }
}
