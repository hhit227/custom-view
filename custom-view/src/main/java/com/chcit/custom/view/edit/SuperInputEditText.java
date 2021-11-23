package com.chcit.custom.view.edit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.chcit.custom.view.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SuperInputEditText extends TextInputEditText {
    private Paint mPaint;
    private Drawable ic_delete;
    private Drawable ic_left_click;
    private Drawable ic_left_unclick;
    private int lineColor_click;
    private int lineColor_unclick;
    private int color;
    private int linePosition;
    private boolean ic_delete_state;
    private TypedArray typedArray;

    public SuperInputEditText(Context context) {
        this(context, (AttributeSet)null);
    }

    public SuperInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public SuperInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ic_delete_state = false;
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.SuperInputEditText);
        int ic_left_clickResID = typedArray.getResourceId(R.styleable.SuperInputEditText_ic_left_click,R.drawable.svg_click_key);
        int left_x = typedArray.getInteger(R.styleable.SuperInputEditText_left_x, 0);
        int left_y = typedArray.getInteger(R.styleable.SuperInputEditText_left_y, 0);
        int left_width = typedArray.getInteger(R.styleable.SuperInputEditText_left_width, 60);
        int left_height = typedArray.getInteger(R.styleable.SuperInputEditText_left_height, 60);
        this.ic_left_click = getBitmapFromVectorDrawable(context,ic_left_clickResID,left_x,left_y,left_width,left_height);
        int ic_left_unclickResID = typedArray.getResourceId(R.styleable.SuperInputEditText_ic_left_unclick, R.drawable.keyboard_unclick);
        this.ic_left_unclick = getBitmapFromVectorDrawable(context,ic_left_unclickResID,left_x,left_y,left_width,left_height);;
        int ic_deleteResID = typedArray.getResourceId(R.styleable.SuperInputEditText_ic_delete, R.drawable.delete);
        int delete_x = typedArray.getInteger(R.styleable.SuperInputEditText_delete_x, 0);
        int delete_y = typedArray.getInteger(R.styleable.SuperInputEditText_delete_y, 0);
        int delete_width = typedArray.getInteger(R.styleable.SuperInputEditText_delete_width, 60);
        int delete_height = typedArray.getInteger(R.styleable.SuperInputEditText_delete_height, 60);
        this.ic_delete = getBitmapFromVectorDrawable(context,ic_deleteResID,delete_x,delete_y,delete_width,delete_height);
        this.setCompoundDrawables(this.ic_left_unclick, (Drawable)null, (Drawable)null, (Drawable)null);
        int cursor = typedArray.getResourceId(R.styleable.SuperInputEditText_cursor, R.drawable.cursor);

        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(this, cursor);
        } catch (Exception var18) {
            var18.printStackTrace();
        }

        this.mPaint = new Paint();
        this.mPaint.setStrokeWidth(2.0F);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int lineColorClick_default = typedValue.resourceId;
        int lineColorunClick_default = getResources().getColor(R.color.opacity_9_5_gray_4c); // 默认 = 灰色#9b9b9b
        this.lineColor_click = typedArray.getColor(R.styleable.SuperInputEditText_lineColor_click, getResources().getColor(lineColorClick_default));
        this.lineColor_unclick = typedArray.getColor(R.styleable.SuperInputEditText_lineColor_unclick, lineColorunClick_default);
        this.color = this.lineColor_unclick;
        this.mPaint.setColor(this.color);
        this.linePosition = typedArray.getInteger(R.styleable.SuperInputEditText_linePosition, 1);
        this.setFocusableInTouchMode(true);
        this.setKeyState(false);
        this.closeKey();
    }
    public Drawable getBitmapFromVectorDrawable(Context context, int drawableId,int x,int y,int width,int height ) {
        Drawable drawable =  AppCompatResources.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(x, y, width, height);
        drawable.draw(canvas);

        return drawable;
    }
    private void closeKey() {
        InputMethodManager imm = (InputMethodManager)this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
    }

    private void setKeyState(boolean falg) {
        Class cls = EditText.class;

        try {
            Method method = cls.getMethod("setShowSoftInputOnFocus", Boolean.TYPE);
            method.setAccessible(true);
            method.invoke(this, falg);
        } catch (Exception var5) {
            ;
        }

    }

    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.setDeleteIconVisible(this.hasFocus() && text.length() > 0, this.hasFocus());
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        this.setDeleteIconVisible(focused && this.length() > 0, focused);
        if (!focused) {
            this.closeKey();
            if (this.ic_delete_state) {
                this.ic_delete_state = false;
                this.setKeyState(false);
            }
        } else if (focused && !this.ic_delete_state) {
            this.closeKey();
        }

    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case 1:
                Drawable drawable = this.ic_delete;
                Drawable ic_left = this.ic_left_click;
                if (drawable != null && event.getX() <= (float)(this.getWidth() - this.getPaddingRight()) && event.getX() >= (float)(this.getWidth() - this.getPaddingRight() - drawable.getBounds().width())) {
                    this.setText("");
                } else if (ic_left != null && event.getX() >= (float)this.getPaddingLeft() && event.getX() <= (float)ic_left.getBounds().width()) {
                    if (this.ic_delete_state) {
                        this.ic_delete_state = false;
                        this.setKeyState(false);
                        this.closeKey();
                    } else {
                        this.ic_delete_state = true;
                        this.setKeyState(true);
                        InputMethodManager imm = (InputMethodManager)this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(this, 2);
                        imm.toggleSoftInput(2, 1);
                    }
                }
            default:
                return super.onTouchEvent(event);
        }
    }

    public void setOnKeyListener(OnKeyListener l) {
        super.setOnKeyListener(l);
    }
    /**
     * 关注1
     * 作用：判断是否显示删除图标 & 设置分割线颜色
     */
    private void setDeleteIconVisible(boolean deleteVisible, boolean leftVisible) {
        this.setCompoundDrawables(leftVisible ? this.ic_left_click : this.ic_left_unclick, (Drawable)null, deleteVisible ? this.ic_delete : null, (Drawable)null);
        this.color = leftVisible ? this.lineColor_click : this.lineColor_unclick;
        this.invalidate();
    }
    /**
     * 作用：绘制分割线
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setColor(this.color);
        int x = this.getScrollX();
        int w = this.getMeasuredWidth();
        canvas.drawLine(0.0F, (float)(this.getMeasuredHeight() - this.linePosition), (float)(w + x), (float)(this.getMeasuredHeight() - this.linePosition), this.mPaint);
    }


}
