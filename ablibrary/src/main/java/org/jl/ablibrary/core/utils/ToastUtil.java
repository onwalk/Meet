package org.jl.ablibrary.core.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jl.ablibrary.R;

/**
 * User: XiaoQinag.ma
 * Date: 2015-06-04 17:49
 * FIXME
 */
public class ToastUtil extends Toast {

    private MediaPlayer mPlayer;
    private boolean isSound;
    private static ToastUtil mToastUtil = null;
    private static Context mContext = null;
    private static LinearLayout mToast_layout;
    private static TextView mTv;
    private static View mV;

    public ToastUtil(Context context) {
        this(context, false);
    }

    public ToastUtil(Context context, boolean isSound) {
        super(context);

        this.isSound = isSound;

        // mPlayer = MediaPlayer.create(context, R.raw.newdatatoast);
        // mPlayer.setOnCompletionListener(new
        // MediaPlayer.OnCompletionListener() {
        // @Override
        // public void onCompletion(MediaPlayer mp) {
        // mp.release();
        // }
        // });

    }

    public static ToastUtil getInstance() {
        if (mToastUtil == null) {
            synchronized (ToastUtil.class) {
                if (mToastUtil == null) {
                    mContext = CrashApplication.getInstance();
                    mToastUtil = new ToastUtil(CrashApplication.getInstance());
                    LayoutInflater inflate = (LayoutInflater) CrashApplication
                            .getInstance().getSystemService(
                                    Context.LAYOUT_INFLATER_SERVICE);
                    DisplayMetrics dm = CrashApplication.getInstance()
                            .getResources().getDisplayMetrics();

                    mV = inflate.inflate(R.layout.new_data_toast, null);
                    mTv = (TextView) mV
                            .findViewById(R.id.new_data_toast_message);
                    mV.setMinimumWidth(dm.widthPixels);// 设置控件最小宽度为手机屏幕宽度

                    mToastUtil.setView(mV);
                    mToastUtil.setGravity(Gravity.TOP, 0,
                            (int) (dm.density * 47));
                    return mToastUtil;
                }
            }
        }
        return mToastUtil;
    }

    private static void showMsg(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            mToastUtil = getInstance();
            mTv.setText(msg);
            mToast_layout.getBackground().setAlpha(210);// 0~255透明度值
//			mToast_layout.setAnimation(new ScaleAnimation(0f,1f,0f,1f));
            mToastUtil.show();
        }
    }

    public static void show(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            mToastUtil = getInstance();
            mToast_layout = (LinearLayout) mV.findViewById(R.id.toast_layout);
            mToast_layout.setBackgroundColor(mContext.getResources().getColor(
                    R.color.toast_red));
            showMsg(msg);
        }
    }

    public static void show(Context context, String msg) {
        mContext = context;
        mToastUtil = getInstance();
        mToast_layout = (LinearLayout) mV.findViewById(R.id.toast_layout);
        mToast_layout.setBackgroundColor(mContext.getResources().getColor(
                R.color.toast_red));
        showMsg(msg);
    }

    public static void showOK(Context context, String msg) {
        mContext = context;
        mToastUtil = getInstance();
        mToast_layout = (LinearLayout) mV.findViewById(R.id.toast_layout);
        mToast_layout.setBackgroundColor(mContext.getResources().getColor(
                R.color.toast_green));
        showMsg(msg);
    }

    public static void showOK(String msg) {
        mToastUtil = getInstance();
        mToast_layout = (LinearLayout) mV.findViewById(R.id.toast_layout);
        mToast_layout.setBackgroundColor(mContext.getResources().getColor(
                R.color.toast_green));
        showMsg(msg);
    }

    public static void showLong(String info) {
        if (!StringUtils.isEmpty(info)) {
            mToastUtil = getInstance();
            mToastUtil.setDuration(LENGTH_LONG);
            mToastUtil.show();
        }
    }

    /**
     * 自定义持续时间
     *
     * @Title: showOK
     * @Description: TODO
     * @throws
     */
    public static void showOK(String info, int time) {
        if (!StringUtils.isEmpty(info)) {
            mToastUtil = getInstance();
            if (time > 0) {
                mToastUtil.setDuration(time);
            }
            mToastUtil.show();
        }
    }

    public static void Cancle() {
        if (mToastUtil != null) {
            mToastUtil.cancel();
        }
    }
    @Override
    public void show() {
        if (mToastUtil == null) {
            return;
        }
        super.show();

        // if (isSound) {
        // mPlayer.start();
        // }
    }

    /**
     * 设置是否播放声音
     */
    public void setIsSound(boolean isSound) {
        this.isSound = isSound;
    }
}
