package tea.demon.com.teaproject.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tea.demon.com.teaproject.R;


/**
 * Created by Bear on 2016/3/11.
 */
public class GlideUtils {

    /**
     * 根据图片url将图片显示在img控件上
     * @param context 所属的Context
     * @param url 图片url
     * @param img ImageView控件
     */
    public static void setImage(Context context, String url, ImageView img) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .fitCenter()
                .placeholder(R.mipmap.loading)
                .into(img);
    }

    /**
     * 根据图片url将图片以70*70比例显示在img控件上
     * @param context 所属的Context
     * @param url 图片url
     * @param img ImageView控件
     */
    public static void setOverrideImage(Context context, String url, ImageView img) {
        Glide.with(context)
                .load(url)
                .override(70, 70)
                .centerCrop()
                .placeholder(R.mipmap.loading)
                .into(img);
    }
}
