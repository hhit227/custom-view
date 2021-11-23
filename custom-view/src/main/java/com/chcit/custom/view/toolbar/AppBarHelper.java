package com.chcit.custom.view.toolbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 变更 App bar 的风格的帮助类
 *
 * @author Sharry <a href="SharryChooChn@Gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2018/8/27 23:41
 */
class AppBarHelper {

    /**
     * Get AppBarHelper instance with this factory method.
     */
    static AppBarHelper with(Context context) {
        return new AppBarHelper(context);
    }

    private static final int DEFAULT_OPTIONS = 0;
    private int mOptions = DEFAULT_OPTIONS;
    private AppCompatActivity mActivity;
    private Window mWindow;

    private AppBarHelper(Context context) {
        if (context instanceof AppCompatActivity) {
            mActivity = (AppCompatActivity) context;
            mWindow = mActivity.getWindow();
        } else {
            throw new IllegalArgumentException("Please ensure context instance of Activity.");
        }
    }

    /**
     * 设置StatusBar的风格
     */
    AppBarHelper setStatusBarStyle(View view,Style style) {
        if (!Utils.isLollipop()) {
            setImmerseLayout(view,mActivity);
            return this;
        }else{
            switch (style) {
                // 设置状态栏为全透明
                case TRANSPARENT: {
                    int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    mOptions = mOptions | option;

                    mWindow.setStatusBarColor(Color.TRANSPARENT);

                    break;
                }
                // 设置状态栏为半透明
                case TRANSLUCENCE: {
                    int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    mOptions = mOptions | option;
                    mWindow.setStatusBarColor(Utils.alphaColor(Color.BLACK, 0.3f));
                    break;
                }
                // 隐藏状态栏
                case HIDE: {
                    int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
                    mOptions = mOptions | option;
                    break;
                }
                // 清除透明状态栏
                case DEFAULT: {
                    mOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    //获取当前Application主题中的状态栏Color
                    TypedValue typedValue = new TypedValue();
                    mActivity.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
                    int color = typedValue.data;
                    mWindow.setStatusBarColor(color);
                }
            }
            return this;
        }

    }

    public static  void setImmerseLayout(View view,Activity activity) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(activity);
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    AppBarHelper setStatusBarColor(int color) {
        if (!Utils.isLollipop()) return this;
        mOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        mWindow.setStatusBarColor(color);
        return this;
    }

    /**
     * 设置NavigationBar的风格
     */

    AppBarHelper setNavigationBarStyle(Style style) {
        if (!Utils.isLollipop()) return this;
        switch (style) {
            // 设置导航栏为全透明
            case TRANSPARENT: {
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                mOptions = mOptions | option;
                mWindow.setNavigationBarColor(Color.TRANSPARENT);
                break;
            }
            // 设置导航栏为半透明
            case TRANSLUCENCE: {
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                mOptions = mOptions | option;
                mWindow.setNavigationBarColor(Utils.alphaColor(Color.BLACK, 0.3f));
                break;
            }
            //隐藏导航栏
            case HIDE: {
                int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                mOptions = mOptions | option;
                break;
            }
            case DEFAULT: {
                mOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                //获取当前Activity主题中的color
                TypedValue typedValue = new TypedValue();
                mActivity.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
                int color = typedValue.data;
                mWindow.setNavigationBarColor(color);
            }
        }
        return this;
    }

    AppBarHelper setNavigationBarColor(int color) {
        if (!Utils.isLollipop()) return this;
        mOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        mWindow.setNavigationBarColor(color);
        return this;
    }

    /**
     * 隐藏所有Bar(全屏模式)
     */
    AppBarHelper setAllBarsHide() {
        if (!Utils.isLollipop()) return this;
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        mOptions = option;
        return this;
    }


    void apply() {
        if (!Utils.isLollipop()) return;
        if (mOptions != DEFAULT_OPTIONS) {
            View decorView = mWindow.getDecorView();
            decorView.setSystemUiVisibility(mOptions);
        }
    }

}
