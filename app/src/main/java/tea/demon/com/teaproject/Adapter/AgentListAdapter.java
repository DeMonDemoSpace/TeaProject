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
import tea.demon.com.teaproject.Admin.AgentListActivity;
import tea.demon.com.teaproject.Admin.AgentStoreActivity;
import tea.demon.com.teaproject.Admin.ChangeCheckActivity;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Agent;
import tea.demon.com.teaproject.data.Constant;

/**
 * Created by D&LL on 2017/5/25.
 */

public class AgentListAdapter extends RecyclerView.Adapter<AgentListAdapter.ViewHolder> {


    private List<Agent> list;
    private Context context;

    public AgentListAdapter(Context context, List<Agent> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_agentlist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Agent agent = list.get(pos);
                if (AgentListActivity.request == -1) {//判断是不是上架操作
                    if (agent.getCheck_status() == 2) {
                        Intent intent = new Intent();
                        intent.putExtra(Constant.AGENT_ID, agent);
                        intent.putExtra(Constant.POS, pos);
                        intent.setClass(context, ChangeCheckActivity.class);
                        context.startActivity(intent);
                    } else if (agent.getCheck_status() == 1) {
                        Intent intent = new Intent();
                        intent.putExtra(Constant.AGENT_ID, agent.getAgent_id());
                        intent.setClass(context, AgentStoreActivity.class);
                        context.startActivity(intent);
                    } else {
                        return;
                    }
                } else {
                    //上架操作
                    Intent intent = new Intent();
                    intent.putExtra(Constant.AGENT_ID, agent.getAgent_id());
                    intent.setClass(context, AgentStoreActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Agent agent = list.get(position);
        holder.tvPhone.setText(agent.getPhone());
        holder.tvName.setText(agent.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        View views;

        public ViewHolder(View view) {
            super(view);
            views = view;
            ButterKnife.bind(this, view);
        }
    }
}
