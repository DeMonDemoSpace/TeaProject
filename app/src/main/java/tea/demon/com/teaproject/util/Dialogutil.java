package tea.demon.com.teaproject.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import tea.demon.com.teaproject.R;
import tea.demon.com.teaproject.view.AddDialogView;
import tea.demon.com.teaproject.view.DelDialogView;

/**
 * Created by D&LL on 2017/5/22.
 */

public class Dialogutil {

    /**
     * 数据加载进度框
     *
     * @param context
     * @return
     */
    public static ProgressDialog getProgressDialog(final Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        //dialog.setTitle("温馨提示");
        dialog.setMessage("加载中...");
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * 提示内容的dialog
     *
     * @param context
     * @param msg
     */
    public static void psDialogBuilder(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("提示").setMessage(msg);
        builder.setPositiveButton(R.string.positive, null);
        builder.create().show();
    }

    /**
     * 删除提示dialog的生成和positiveButton的回调
     *
     * @param context
     * @param dialogView
     */
    public static void delDialogBuilder(final Context context, final DelDialogView dialogView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("提示").setMessage("是否确定要删除？");
        builder.setNegativeButton(R.string.negative, null);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogView != null) {
                    dialogView.delete();
                }
            }
        });
        builder.create().show();
    }


    /**
     * 根据title和自定义的view生成dialog
     * 并实现positiveButton的回调
     *
     * @param context       所属的Context
     * @param title         标题
     * @param view          自定义的view
     * @param addDialogView positiveButton的回调
     */
    public static void addDialogBuilder(Context context, String title, View view, final AddDialogView addDialogView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        if (view != null) {
            builder.setView(view);
        }
        builder.setNegativeButton(R.string.negative, null);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (addDialogView != null) {
                    addDialogView.add();
                }
            }
        });
        builder.create().show();

    }
}
