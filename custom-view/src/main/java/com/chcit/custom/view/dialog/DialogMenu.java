package com.chcit.custom.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.chcit.custom.view.R;

import java.util.List;

public class DialogMenu {


    private Dialog mCameraDialog;
    private OnItemClickListener onItemClickListener;
    private ListView listView;
    private BaseAdapter adapter;
    private int selected;

    public DialogMenu(Context context, int gravity, List<String> mData) {

        init(context, gravity, mData);
    }

    private void init(Context context, int gravity, List<String> mData) {
        mCameraDialog = new Dialog(context, R.style.BottomDialog);
        LinearLayoutCompat root = (LinearLayoutCompat) LayoutInflater.from(context).inflate(
                R.layout.botton_dialog, null);
        //初始化视图
//        root.findViewById(R.id.btn_level_off).setOnClickListener(this);
//        root.findViewById(R.id.btn_level_info).setOnClickListener(this);
//        root.findViewById(R.id.btn_level_debug).setOnClickListener(this);
//        root.findViewById(R.id.btn_level_error).setOnClickListener(this);
//        root.findViewById(R.id.btn_level_warn).setOnClickListener(this);
//        root.findViewById(R.id.btn_level_verbose).setOnClickListener(this);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(gravity);
        //        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) context.getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        initListView(root, context, mData);
        //mCameraDialog.show();


    }

    private void initListView(LinearLayoutCompat root, final Context context, final List<String> mData) {
        //初始化ListView
        listView = root.findViewById(R.id.lv_single);
        //重点一：设置选择模式
        //☆☆☆:通过listView设置单选模式
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        //☆☆☆：通过listView设置多选模式
//        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        //重点二：设置单选与多选框的样式
        //☆☆☆:创建adapter,布局加载系统默认的单选框
        //adapter = new ArrayAdapter<>(context,R.layout.);
        //adapter.addAll(mData);
        adapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public Object getItem(int position) {
                return mData.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.dialog_menu_item,
                            null);
                }

                AppCompatCheckedTextView checkedTextView = convertView.findViewById(R.id.text1);
                checkedTextView.setGravity(Gravity.CENTER);
                checkedTextView.setText((CharSequence) getItem(position));
                if (selected == position) {
                    //说明该位置需要checked
                    checkedTextView.setChecked(true);
                } else {
                    checkedTextView.setChecked(false);
                }

                return convertView;
            }


        };
        //☆☆☆:创建adapter,布局加载系统默认的单选框
//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice);
        //将模拟数据添加到adapter适配器中

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             /*  if (position == 0) {
                    onItemClickListener.onItemClickListener("OFF", Integer.MAX_VALUE);
                } else {
                    onItemClickListener.onItemClickListener(LogLevel.getLevelName(position + 1), position + 1);
                }*/
              if(onItemClickListener != null){
                  onItemClickListener.onItemClickListener(position);
              }
              isSingle((AppCompatCheckedTextView) view.findViewById(R.id.text1));


                selected = position;
                dismiss();
            }
        });
    }


    public void show() {
        mCameraDialog.show();
    }

    public void dismiss() {
        mCameraDialog.dismiss();
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setSelected(int position) {
        selected = position;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }
    /*
    @Override
    public void onClick(View view) {
     int i = view.getId();
        if (i == R.id.btn_level_off) {
            if(onItemClickListener!= null){
                onItemClickListener.onItemClickListener("OFF",Integer.MAX_VALUE);
            }

        } else if (i == R.id.btn_level_info) {
            if(onItemClickListener!= null){
                onItemClickListener.onItemClickListener("INFO",4);
            }

        } else if (i == R.id.btn_level_debug) {
            if(onItemClickListener!= null){
                onItemClickListener.onItemClickListener("DEBUG",3);
            }
        }else if (i == R.id.btn_level_error) {
            if(onItemClickListener!= null){
                onItemClickListener.onItemClickListener("ERROR",6);
            }
        }else if (i == R.id.btn_level_warn) {
            if(onItemClickListener!= null){
                onItemClickListener.onItemClickListener("WARN",5);
            }
        }else if (i == R.id.btn_level_verbose) {
            if(onItemClickListener!= null){
                onItemClickListener.onItemClickListener("VERBOSE",2);
            }
        }
        dismiss();
    }*/

    /**
     * 确定按钮的监听事件
     *
     * @param v
     */
    public void isSingle(AppCompatCheckedTextView v) {
        //通过listView对象获取到当前listView中被选择的条目position
        int checkedItemPosition = listView.getCheckedItemPosition();
        //获取到对应条目的position后，我们可以do something....
        v.setChecked(true);
    }


    public interface OnItemClickListener {
        void onItemClickListener( int Position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
