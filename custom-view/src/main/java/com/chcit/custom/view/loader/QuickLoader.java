package com.chcit.custom.view.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.chcit.custom.view.R;
import com.chcit.custom.view.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.WeakHashMap;

/**
 * Created by liuyang
 */

public class QuickLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;

   // private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    private static AppCompatDialog dialog;
    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    public static void showLoading(Context context, Enum<LoaderStyle> type) {
        showLoading(context, type.name());
    }

    public static void showLoading(Context context, String type) {
        showLoading(context,type,false);
    }

    public static void showLoading(Context context, String type , boolean cancelable) {
            if(dialog == null) {
                dialog = new AppCompatDialog(context, R.style.dialog);
                dialog.setContentView(R.layout.dialog_loading);
                final AVLoadingIndicatorView avLoadingIndicatorView = dialog.findViewById(R.id.avi);
                if (type != null) {
                    avLoadingIndicatorView.setIndicator(type);
                }
            /*
            int deviceWidth = DimenUtil.getScreenWidth(context);
            int deviceHeight = DimenUtil.getScreenHeight(context);
            final Window dialogWindow = dialog.getWindow();
            if (dialogWindow != null) {
                final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = deviceWidth / LOADER_SIZE_SCALE;
                lp.height = deviceHeight / LOADER_SIZE_SCALE;
                lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
                lp.gravity = Gravity.CENTER;
            }*/
                dialog.setCancelable(cancelable);
            }
            dialog.show();


    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }

    public static void stopLoading() {
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }


}
