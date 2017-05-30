package tea.demon.com.teaproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.Agent.CouponActivity;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Coupon;
import tea.demon.com.teaproject.presenter.AgentPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.view.DelDialogView;
import tea.demon.com.teaproject.view.IView;

/**
 * Created by D&LL on 2017/5/25.
 */

public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.ViewHolder> {

    private List<Coupon> list;
    private Context context;

    public CouponListAdapter(Context context, List<Coupon> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_couponlist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogutil.delDialogBuilder(context, new DelDialogView() {
                    @Override
                    public void delete() {
                        final int delpos = holder.getAdapterPosition();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(Constant.COUPON_ID, list.get(delpos).getCoupon_id() + "");
                        AgentPresenter presenter = new AgentPresenter(context, new IView() {
                            @Override
                            public void getData(int code, Object object) {
                                list.remove(delpos);
                                CouponActivity.handler.sendEmptyMessage(0x001);
                            }
                        });
                        presenter.delCoupon(map);
                    }
                });
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Coupon coupon = list.get(position);
        holder.tvName.setText(coupon.getName());
        if (coupon.getType() == 1) {
            holder.tvCoupon.setText(coupon.getDiscount());
        } else {
            holder.tvCoupon.setText(coupon.getGift());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_coupon)
        TextView tvCoupon;
        @BindView(R.id.iv_del)
        ImageView ivDel;

        View views;

        public ViewHolder(View view) {
            super(view);
            views = view;
            ButterKnife.bind(this, view);
        }
    }
}
