package com.chcit.custom.view.loader;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.chcit.custom.view.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 加载中Dialog
 *
 * @author hzb
 */
public class LoadingDialog extends AlertDialog {

    private static LoadingDialog loadingDialog;
    private static AVLoadingIndicatorView avi;
    private static String loadingType ;
    public static LoadingDialog getInstance() {

        return loadingDialog;
    }
    public static LoadingDialog getInstance(Context context) {

        return getInstance(context,null);
    }
    public static LoadingDialog getInstance(Context context,String type) {
        if(loadingDialog == null){
            loadingDialog = new LoadingDialog(context, R.style.dialog); //设置AlertDialog背景透明
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingType =type;
        }

        return loadingDialog;
    }



    public LoadingDialog(Context context, int themeResId) {
        super(context,themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAvLoading();


    }

    private void createAvLoading() {
        this.setContentView(R.layout.dialog_loading);
        avi = this.findViewById(R.id.avi);
        if(loadingType != null){
            avi.setIndicator(loadingType);
        }

    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void hide() {
        super.hide();
        avi.hide();

    }

    @Override
    public void dismiss() {
        super.dismiss();
        avi.hide();
        loadingDialog = null;
        avi = null;

    }
    public static void destroy(){
        if(loadingDialog != null){
            loadingDialog.dismiss();
            loadingDialog = null;

        }
    }

    public static void stop(){
        if(loadingDialog != null){
            loadingDialog.cancel();
            loadingDialog = null;
        }
    }
}