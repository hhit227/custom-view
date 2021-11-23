package com.chcit.custom.view.toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chcit.custom.view.R;


public class TitleBarView extends RelativeLayout {

	private static final String TAG = "TitleBarView";
	private Context mContext;
	private Button btnLeft;
	private Button btnRight;
	private Button btn_titleLeft;
	private Button btn_titleRight;
	private TextView tv_center;
	private LinearLayout common_constact;
	private View titleBarView;
	public TitleBarView(Context context){
		super(context);
		mContext=context;
		initView();
	}
	
	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		initView();
	}
	
	private void initView(){
		titleBarView = LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);
		btnLeft=(Button) findViewById(R.id.title_btn_left);
		btnRight=(Button) findViewById(R.id.title_btn_right);
		btn_titleLeft=(Button) findViewById(R.id.constact_group);
		btn_titleRight=(Button) findViewById(R.id.constact_all);
		tv_center=(TextView) findViewById(R.id.title_txt);
		common_constact=(LinearLayout) findViewById(R.id.common_constact);
		
	}
	
	public void setCommonTitle(int LeftVisibility,int centerVisibility,int center1Visibilter,int rightVisibility){
		btnLeft.setVisibility(LeftVisibility);
		btnRight.setVisibility(rightVisibility);
		tv_center.setVisibility(centerVisibility);
		common_constact.setVisibility(center1Visibilter);
		
	}
	
	public void setBtnLeft(int icon,int txtRes){
	    setDrawable(icon, 20,null,null,null,btnLeft);
		btnLeft.setText(txtRes);

	}

	@NonNull
	public void setDrawable(int icon, int i, Drawable top, Drawable right , Drawable bottom, Button button) {
		Drawable img = mContext.getResources().getDrawable(icon);
		int height = SystemMethod.dip2px(mContext, i);
		int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
        button.setCompoundDrawables(img, top, right, bottom);

	}

	public void setBtnLeft(int txtRes){
		btnLeft.setText(txtRes);
	}

	public TextView getTiltle(){
		return tv_center;
	}
	
	public void setBtnRight(int icon){
        Drawable img = mContext.getResources().getDrawable(icon);
        int height = SystemMethod.dip2px(mContext, 30);
        int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
        img.setBounds(0, 0, width, height);
        btnRight.setCompoundDrawables(null, null, img, null);

	}
	
	public void setTitleLeft(int resId){
		btn_titleLeft.setText(resId);
	}
	
	public void setTitleRight(int resId){
		btn_titleRight.setText(resId);
	}
	
	@SuppressLint("NewApi")
	public void setPopWindow(PopupWindow mPopWindow, TitleBarView titleBaarView){
			mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E9E9E9")));
			mPopWindow.showAsDropDown(titleBaarView, 0,-15);
			mPopWindow.setAnimationStyle(R.style.popwin_anim_style);
			mPopWindow.setFocusable(true);
			mPopWindow.setOutsideTouchable(true);
			mPopWindow.update();
			
			setBtnRight(R.drawable.skin_conversation_title_right_btn_selected);
		}
	
	public void setTitleText(int txtRes){
		tv_center.setText(txtRes);
	}
	
	public void setBtnLeftOnclickListener(OnClickListener listener){
		btnLeft.setOnClickListener(listener);
	}
	
	public void setBtnRightOnclickListener(OnClickListener listener){
		btnRight.setOnClickListener(listener);
	}
	
	public Button getTitleLeft(){
		return btn_titleLeft;
	}
	
	public Button getTitleRight(){
		return btn_titleRight;
	}
	
	public void destoryView(){
		btnLeft.setText(null);
		btnRight.setText(null);
		tv_center.setText(null);
	}
	
	public Button getBtnRight() {
        return btnRight;
    }
	public Button getBtnLeft() {
		return btnLeft;
	}

	public View getTitleBarView() {
		return titleBarView;
	}
}
