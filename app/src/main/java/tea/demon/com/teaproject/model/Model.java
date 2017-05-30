package tea.demon.com.teaproject.model;

import android.app.ProgressDialog;
import android.content.Context;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tea.demon.com.teaproject.data.Constant;
import tea.demon.com.teaproject.data.Token;
import tea.demon.com.teaproject.util.Dialogutil;
import tea.demon.com.teaproject.util.ToastUtil;

/**
 * Created by D&LL on 2017/5/22.
 */

public class Model {
    private static Model model = new Model();

    public static Model getModel() {
        return model;
    }

    private OkHttpClient client = new OkHttpClient();
    private Token token = Token.getInstance();
    private ProgressDialog dialog;

    /**
     * get请求
     *
     * @param context
     * @param url
     * @return
     * @throws Exception
     */
    public void get(final Context context, String url, final ICallBack callback) {
        dialog = Dialogutil.getProgressDialog(context);
        dialog.show();
        final Request request = new Request.Builder().url(url).header(Constant.AUTHORIZATION, token.getToken()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.toString());
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (response.isSuccessful()) {
                    callback.result(response.code(), result);
                } else {
                    ToastUtil.ThreadErrorToast(context, result);
                }
                response.close();
                dialog.dismiss();
            }
        });
    }

    /**
     * get带参数请求
     *
     * @param context
     * @param url
     * @return
     * @throws Exception
     */
    public void get(final Context context, String url, Map<String, String> map, final ICallBack callback) {
        dialog = Dialogutil.getProgressDialog(context);
        dialog.show();
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (map != null) {
            //增强for循环遍历
            for (Map.Entry<String, String> entry : map.entrySet()) {
                sb.append("?");
                sb.append(entry.getKey() + "=" + entry.getValue());//合成url
            }
        }

        final Request request = new Request.Builder().url(sb.toString()).header(Constant.AUTHORIZATION, token.getToken()).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (response.isSuccessful()) {
                    callback.result(response.code(), result);
                } else {
                    ToastUtil.ThreadErrorToast(context, result);
                }
                response.close();
                dialog.dismiss();
            }
        });
    }

    /**
     * post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public void post(final Context context, String url, Map<String, String> map, final ICallBack callback) {
        dialog = Dialogutil.getProgressDialog(context);
        dialog.show();
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            //增强for循环遍历
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().post(formBody).url(url).header(Constant.AUTHORIZATION, token.getToken()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.toString());
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (response.isSuccessful()) {
                    callback.result(response.code(), result);
                } else {
                    ToastUtil.ThreadErrorToast(context, result);
                }
                response.close();
                dialog.dismiss();
            }
        });

    }

    /**
     * post提交json
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public void postJson(final Context context, String url, String json, final ICallBack callback) {
        dialog = Dialogutil.getProgressDialog(context);
        dialog.show();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"), json);
        Request request = new Request.Builder().post(requestBody).url(url).header(Constant.AUTHORIZATION, token.getToken()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.toString());
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (response.isSuccessful()) {
                    callback.result(response.code(), result);
                } else {
                    ToastUtil.ThreadErrorToast(context, result);
                }
                response.close();
                dialog.dismiss();
            }
        });

    }

    /**
     * delete请求
     *
     * @param context
     * @param url
     * @param map
     * @param callback
     */
    public void delete(final Context context, String url, Map<String, String> map, final ICallBack callback) {
        dialog = Dialogutil.getProgressDialog(context);
        dialog.show();
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            //增强for循环遍历
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().delete(formBody).url(url).header(Constant.AUTHORIZATION, token.getToken()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.toString());
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (response.isSuccessful()) {
                    callback.result(response.code(), result);
                } else {
                    ToastUtil.ThreadErrorToast(context, result);
                }
                response.close();
                dialog.dismiss();
            }
        });
    }

    /**
     * put请求
     *
     * @param context
     * @param url
     * @param map
     * @param callback
     */
    public void put(final Context context, String url, Map<String, String> map, final ICallBack callback) {
        dialog = Dialogutil.getProgressDialog(context);
        dialog.show();
        FormBody.Builder builder = new FormBody.Builder();
        if (map != null) {
            //增强for循环遍历
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().put(formBody).url(url).header(Constant.AUTHORIZATION, token.getToken()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.toString());
                dialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (response.isSuccessful()) {
                    callback.result(response.code(), result);
                } else {
                    ToastUtil.ThreadErrorToast(context, result);
                }
                response.close();
                dialog.dismiss();
            }
        });
    }
}
