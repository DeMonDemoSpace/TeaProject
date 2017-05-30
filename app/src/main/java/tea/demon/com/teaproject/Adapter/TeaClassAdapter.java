package tea.demon.com.teaproject.Adapter;

import android.content.Context;
import android.content.Intent;
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
import tea.demon.com.teaproject.Admin.AdminActivity;
import tea.demon.com.teaproject.Admin.TeaListActivity;
import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.TeaClass;
import tea.demon.com.teaproject.presenter.AdminPresenter;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.view.AddDialogView;
import tea.demon.com.teaproject.view.DelDialogView;
import tea.demon.com.teaproject.view.IView;

/**
 * Created by D&LL on 2017/5/26.
 */

public class TeaClassAdapter extends RecyclerView.Adapter<TeaClassAdapter.ViewHolder> {
    private List<TeaClass> list;
    private Context context;

    public TeaClassAdapter(List<TeaClass> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_teaclass, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        initOnClick(holder);//按钮事件
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeaClass teaClass = list.get(position);
        holder.tvName.setText(teaClass.getName());
        holder.tvDescription.setText(teaClass.getDescription());
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
        @BindView(R.id.tv_description)
        TextView tvDescription;
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
                if (AdminActivity.request == -1) {
                    holder.ivDelete.setVisibility(View.GONE);
                    holder.ivEdit.setVisibility(View.GONE);
                    Intent intent = new Intent();
                    intent.putExtra(Constant.POS, list.get(pos).getClass_id());
                    intent.putExtra(Constant.TITLE, list.get(pos).getName());
                    intent.setClass(context, TeaListActivity.class);
                    context.startActivity(intent);
                } else {
                    Message message = new Message();
                    message.obj = list.get(pos).getClass_id();
                    message.what = 0x0123;
                    AdminActivity.handler.sendMessage(message);
                    AdminActivity.request = -1;
                }
            }
        });
        holder.views.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (AdminActivity.request == -1) {
                    holder.ivDelete.setVisibility(View.VISIBLE);
                    holder.ivEdit.setVisibility(View.VISIBLE);
                }
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
                        map.put(Constant.CLASS_ID, list.get(delpos).getClass_id() + "");
                        AdminPresenter delPresenter = new AdminPresenter(context, new IView() {
                            @Override
                            public void getData(int code, Object object) {
                                if (code == 204) {
                                    AdminActivity.teaClassList.remove(delpos);
                                    AdminActivity.handler.sendEmptyMessage(0x901);
                                }
                            }
                        });
                        delPresenter.delTeaClass(map);

                    }
                });
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int editpos = holder.getAdapterPosition();
                View addview = View.inflate(context, R.layout.edit_teaclass, null);
                final EditText et_name = (EditText) addview.findViewById(R.id.et_name);
                final EditText et_des = (EditText) addview.findViewById(R.id.et_description);
                et_name.setText(list.get(editpos).getName());
                et_des.setText(list.get(editpos).getDescription());
                Dialogutil.addDialogBuilder(context, context.getString(R.string.alterClass), addview, new AddDialogView() {
                    @Override
                    public void add() {
                        final String name = et_name.getText().toString();
                        final String des = et_des.getText().toString();
                        holder.ivDelete.setVisibility(View.GONE);
                        holder.ivEdit.setVisibility(View.GONE);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put(Constant.CLASS_ID, list.get(editpos).getClass_id() + "");
                        map.put(Constant.NAME, name);
                        map.put(Constant.DESCRIPTION, des);
                        AdminPresenter putPresenter = new AdminPresenter(context, new IView() {
                            @Override
                            public void getData(int code, Object object) {
                                if (code == 201) {
                                    TeaClass tea = AdminActivity.teaClassList.get(editpos);
                                    tea.setName(name);
                                    tea.setDescription(des);
                                    AdminActivity.handler.sendEmptyMessage(0x012);
                                }
                            }
                        });
                        putPresenter.putTeaClass(map);
                    }
                });
            }
        });
    }
}
