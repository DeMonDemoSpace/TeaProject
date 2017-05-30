package tea.demon.com.teaproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import tea.demon.com.teaproject.Admin.TeaInfoActivity;
import tea.demon.com.teaproject.Admin.TeaListActivity;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Tea;
import tea.demon.com.teaproject.presenter.TeaPresenter;
import tea.demon.com.teaproject.util.GlideUtils;
import tea.demon.com.teaproject.view.IView;

/**
 * Created by D&LL on 2017/5/27.
 */

public class TeaListAdapter extends RecyclerView.Adapter<TeaListAdapter.ViewHolder> {

    private Context context;
    private List<Tea> list;

    public TeaListAdapter(Context context, List<Tea> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_tealist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        initOnClick(holder);
        return holder;
    }

    private void initOnClick(final ViewHolder holder) {
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.btDel.setVisibility(View.VISIBLE);
                holder.btMove.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btDel.setVisibility(View.GONE);
                holder.btMove.setVisibility(View.GONE);
                int pos = holder.getAdapterPosition();
                Tea tea = list.get(pos);
                Intent intent = new Intent();
                intent.putExtra(Constant.TEA, tea);
                intent.setClass(context, TeaInfoActivity.class);
                context.startActivity(intent);
            }
        });
        holder.btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btDel.setVisibility(View.GONE);
                holder.btMove.setVisibility(View.GONE);
                final int pos = holder.getAdapterPosition();
                Map<String, String> map = new HashMap<String, String>();
                map.put(Constant.GOODS_ID, list.get(pos).getGoods_id() + "");
                TeaPresenter presenter = new TeaPresenter(context, new IView() {
                    @Override
                    public void getData(int code, Object object) {
                        if (code == 204) {
                            TeaListActivity.list.remove(pos);
                            TeaListActivity.handler.sendEmptyMessage(0x002);
                        }
                    }
                });
                presenter.delTea(map);

            }
        });
        holder.btMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btDel.setVisibility(View.GONE);
                holder.btMove.setVisibility(View.GONE);
                int pos = holder.getAdapterPosition();
                Map<String, String> map = new HashMap<String, String>();
                map.put(Constant.GOODS_ID, list.get(pos).getGoods_id() + "");
                Message msg = new Message();
                msg.what = 0x003;
                msg.obj = map;
                TeaListActivity.list.remove(pos);
                TeaListActivity.handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tea tea = list.get(position);
        GlideUtils.setImage(context, tea.getThumb(), holder.ivThumb);
        holder.tvName.setText(tea.getName());
        holder.tvDes.setText(tea.getDescription());
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
        @BindView(R.id.tv_des)
        TextView tvDes;
        View view;
        @BindView(R.id.bt_move)
        Button btMove;
        @BindView(R.id.bt_del)
        Button btDel;

        public ViewHolder(View v) {
            super(v);
            view = v;
            ButterKnife.bind(this, v);
        }
    }
}
