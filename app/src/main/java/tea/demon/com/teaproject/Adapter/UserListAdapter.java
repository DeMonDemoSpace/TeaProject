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
import tea.demon.com.teaproject.Admin.UserInfoActivity;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.User;

/**
 * Created by D&LL on 2017/5/25.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {


    private List<User> list;
    private Context context;

    public UserListAdapter(Context context, List<User> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_userlist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Intent intent = new Intent();
                intent.putExtra(Constant.USERID, list.get(pos).getId());
                intent.setClass(context, UserInfoActivity.class);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = list.get(position);
        holder.tvUserName.setText(user.getUsername());
        holder.tvName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_userName)
        TextView tvUserName;
        View views;

        public ViewHolder(View view) {
            super(view);
            views = view;
            ButterKnife.bind(this, view);
        }
    }
}
