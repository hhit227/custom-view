package com.chcit.custom.view.util;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.chcit.custom.view.R;
import com.chcit.custom.view.dialog.DialogPlus;
import com.chcit.custom.view.dialog.ViewHolder;


import java.util.Objects;

public class DialogUtil {


    public static void showErrorDialog(Context ctx, String msg, int gravity) {

       DialogPlus dialogPlus = DialogPlus.newDialog(ctx)
                .setContentHolder(new ViewHolder(R.layout.dialog_error))
                //.setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .setGravity(gravity)
                .setInAnimation(R.anim.dialog_in)
                .setOutAnimation(R.anim.dialog_out)
                .setContentBackgroundResource(R.color.transparent)
                .setCancelable(true)
                .create();

        /*AlertDialog dialogPlus = new AlertDialog.Builder(ctx, R.style.dialog)
                .create();*/
       // View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_error, null);
        AppCompatImageView imageView = dialogPlus.findViewById(R.id.iv_close);
        TextView textView = dialogPlus.findViewById(R.id.tv_error_info);
        textView.setText(msg);
/*
        if (Build.VERSION.SDK_INT > 25) {
            dialogPlus.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialogPlus.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialogPlus.show();

        dialogPlus.getWindow().setContentView(view);
        dialogPlus.getWindow().setGravity(gravity);*/
        // 加载自定义布局,可完全覆盖dialog窗口

       /* WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogPlus.getWindow().setAttributes(params);*/


        imageView.setOnClickListener(l -> {
            dialogPlus.dismiss();
        });

        dialogPlus.show();



    }

    public static void showErrorDialog(Context ctx, String msg) {
        showErrorDialog(ctx, msg, Gravity.CENTER);
    }

    public static void showMsg(final Context ctx, String title, String msg, DialogInterface.OnClickListener listener) {
        Builder builder = new Builder(ctx);

        builder.setMessage(msg);
        builder.setTitle(title);

        builder.setPositiveButton("确认", listener);
        builder.create().show();

    }

    public static void showMsgDialog(final Context ctx, String title, String msg, final OnClickEvent clickEvent) {

        Builder builder = new Builder(ctx);

        builder.setMessage(msg);
        builder.setTitle(title);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickEvent.onClick(dialog, which);
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    public static void showMsgDialog(final Context ctx, String msg) {

       AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setMessage(msg)
                .setTitle("提示")
                .setPositiveButton("确认", (dialog, which) -> dialog.dismiss())
                .create();
        if (Build.VERSION.SDK_INT > 25) {
            Objects.requireNonNull(alertDialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            Objects.requireNonNull(alertDialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        alertDialog.show();
    }

    public interface OnClickEvent {
        void onClick(DialogInterface dialog, int which);
    }

	/*private static  WindowManager wm;
	private static  View windowFriendLocLayout;
	private static  WindowManager.LayoutParams lp;
	private static boolean isShow = false;
	private static  TextView textView;
	//弹出框 没有添加经纬度就提示添加经纬度
	public static void showErrorWindow(String msg) {
		Context context = Quick.getApplicationContext();

		if (wm == null) {

			wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			lp = new WindowManager.LayoutParams();
			//窗口类型
			if (Build.VERSION.SDK_INT > 25) {
				lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
			} else {
				lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
			}
			lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
					| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			lp.gravity = Gravity.CENTER;
			// 设置图片格式，效果为背景透明
			lp.format = PixelFormat.RGBA_8888;
			lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			lp.height = WindowManager.LayoutParams.MATCH_PARENT;

			windowFriendLocLayout = LayoutInflater.from(context).inflate(R.layout.dialog_error, null);

			//设置监听
			 textView = windowFriendLocLayout.findViewById(R.id.tv_error_info);

			AppCompatImageView imageView = windowFriendLocLayout.findViewById(R.id.iv_close);
			imageView.setOnClickListener(l->{
				isShow = false;
				wm.removeView(windowFriendLocLayout);
			});

		}
		if (!isShow) {
			textView.setText(msg);
			wm.addView(windowFriendLocLayout, lp);
			isShow = true;
		}
	}
*/
}
