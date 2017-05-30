package tea.demon.com.teaproject.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import tea.demon.com.teaproject.data.Error;

/**
 * Created by D&LL on 2017/5/25.
 */

public class ToastUtil {
    /**
     * 显示一个Toast
     *
     * @param context
     * @param msg
     */
    public static void ShowToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 异步线程中显示错误提示的Toast
     *
     * @param context
     * @param msg
     */
    public static void ThreadErrorToast(final Context context, final String msg) {
        final Error error = JsonUtil.getErrorJson(msg);
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    ToastUtil.ShowToast(context, error.getStatus_code() + "," + error.getMessage());
                }
            });
        }
    }

    /**
     * 异步线程中显示Toast
     *
     * @param activity
     * @param s
     */
    public static void ThreadToast(final Activity activity, final String s) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.ShowToast(activity.getApplicationContext(), s);
            }
        });
    }
}
