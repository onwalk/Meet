package org.jl.ablibrary.core.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import org.jl.ablibrary.R;

import java.util.List;

/**
 * 所有Activity的基类
 *
 * User: XiaoQinag.ma
 * Date: 2015-06-04 17:44
 * FIXME
 */
public class AbBaseActivity extends AppCompatActivity {

    public static Activity active;
    public final String TAG = getClass().getSimpleName();
    public static boolean D = true;
    /**
     * 网络连接是否正常 true为正常
     */
    public boolean ISCONNECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            if (D)
                Log.v(TAG, TAG + "start");
            super.onCreate(savedInstanceState);
            if (!ISCONNECTED) {
                Toast.makeText(getApplicationContext(), "当前没有可用的网络连接",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "初始化失败", e);
            ToastUtil.show("初始化失败");
        }

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(this.getClass().toString(), "onRestoreInstanceState");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(this.getClass().toString(), "onRestart");
    }

    @Override
    protected void onStart() {

        try {
            super.onStart();
            Log.e(this.getClass().toString(), "onStart");
            if (Constants.needChangeBackgroundView != null) {// 恢复点击后的view背景
                Constants.needChangeBackgroundView
                        .setBackgroundResource(Constants.backgroundInt);
            }
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "onStartError", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        active = this;
        Log.e(this.getClass().toString(), "onResume");
//		setSupportProgressBarIndeterminate(false);
//		setSupportProgressBarIndeterminateVisibility(false);
//		setSupportProgressBarVisibility(false);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        String ping;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ping = "当前屏幕为横屏";
        } else {
            ping = "当前屏幕为竖屏";
        }
        Log.e(this.getClass().toString(), "onConfigurationChanged：" + ping);

    }



    // @SuppressLint("HandlerLeak")
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityManager activityManager = (ActivityManager) getSystemService(android.content.Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> lists = activityManager.getRunningTasks(1);// 获取当前activity
            int num = lists.get(0).numActivities;// 当前activity包含多少activity
            Log.e(this.getClass().toString(), "size===" + num + "");

            if (num <= 1) {// 只有一个activity，提示再按一次退出
                // if ((System.currentTimeMillis() - mExitTime) > 2000) {

                ActivityUtil.showSelectDialog(this, "确定退出"
                                + getResources().getString(R.string.app_name) + "吗？",
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // BaseSherlockActionBar.this.finish();
                                ExitApp();
                            }
                        }, new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                ActivityUtil.closeSelectAlertDialog();
                            }
                        });
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出app结束应用
     */
    protected void ExitApp() {

    }
}
