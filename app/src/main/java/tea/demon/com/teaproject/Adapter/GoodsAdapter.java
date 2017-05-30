package tea.demon.com.teaproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.Agent.AgentGoodActivity;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.User.GoodsInfoActivity;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.util.GlideUtils;

/**
 * Created by D&LL on 2017/5/27.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {

    private Context context;
    private List<Tea> list;

    public GoodsAdapter(Context context, List<Tea> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_goodlist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        initOnClick(holder);
        return holder;
    }

    private void initOnClick(final ViewHolder holder) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Tea tea = list.get(pos);
                if (Constant.IDENTITY == 0) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.TEA, tea);
                    intent.setClass(context, GoodsInfoActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.TEA, tea);
                    intent.setClass(context, AgentGoodActivity.class);
                    context.startActivity(intent);

                }
            }
        });

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tea tea = list.get(position);
        GlideUtils.setImage(context, tea.getThumb(), holder.ivThumb);
        holder.tvName.setText(tea.getName());
        holder.tvPrice.setText("￥:" + tea.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        View view;

        public ViewHolder(View v) {
            super(v);
            view = v;
            ButterKnife.bind(this, v);
        }
    }
}
