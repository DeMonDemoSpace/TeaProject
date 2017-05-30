package tea.demon.com.teaproject.util;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tea.demon.com.teaproject.R;


/**
 * Created by D&LL on 2016/11/3.
 */

public class TitleUtil {

    /**
     * 设置toolbar
     *
     * @param activity 当前的Activity
     * @param title    toolbar的标题
     */
    public static void setToolBar(final Activity activity, String title) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(activity.getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    /**
     * 给DrawLayouts设置标题及按钮功能
     *
     * @param activity
     * @param title
     */
    public static void setDrawToolBar(final Activity activity, String title, DrawerLayout drawer) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

}
