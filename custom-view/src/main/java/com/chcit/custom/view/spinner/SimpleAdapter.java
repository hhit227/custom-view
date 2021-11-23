package com.chcit.custom.view.spinner;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleAdapter
 *
 * @author WrBug
 * @since 2017/2/25
 */
public class SimpleAdapter<T> extends BaseEditSpinnerAdapter implements EditSpinnerFilter {
    private Context mContext;
    private List<T> mSpinnerData;
    private List<String> mCacheData;
    private int[] indexs;

    public SimpleAdapter(Context context, List<T> spinnerData) {
        this.mContext = context;
        this.mSpinnerData = spinnerData;
        mCacheData = new ArrayList<>();
        for(T t :spinnerData){
            if(t instanceof String){
                List<String> list = (List<String>)spinnerData;
                mCacheData = new ArrayList<>(list);
                break;
            }
            mCacheData.add(t.toString());
        }

        indexs = new int[mSpinnerData.size()];
    }

    @Override
    public EditSpinnerFilter getEditSpinnerFilter() {
        return this;
    }

    @Override
    public String getItemString(int position) {
        return mSpinnerData.get(indexs[position]).toString();
    }

    @Override
    public int getCount() {
        return mCacheData == null ? 0 : mCacheData.size();
    }

    @Override
    public String getItem(int position) {
        return mCacheData == null ? "" : mCacheData.get(position) == null ? "" : mCacheData.get(position).toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = null;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(mContext).inflate(android.R.layout.simple_spinner_item, null);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(Html.fromHtml(getItem(position)));
        return textView;
    }

    @Override
    public boolean onFilter(String keyword) {
        mCacheData.clear();
        if (TextUtils.isEmpty(keyword)) {
            for(T t :mSpinnerData){
                if(t instanceof String){
                    List<String> list = (List<String>)mSpinnerData;
                    mCacheData.addAll(list);
                    break;
                }
                mCacheData.add(t.toString());
            }

            for (int i = 0; i < indexs.length; i++) {
                indexs[i] = i;
            }
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("[^\\s]*").append(keyword).append("[^\\s]*");
            for (int i = 0; i < mSpinnerData.size(); i++) {
                if (mSpinnerData.get(i).toString().replaceAll("\\s+", "|").matches(builder.toString())) {
                    indexs[mCacheData.size()] = i;
                    mCacheData.add((mSpinnerData.get(i).toString()).replaceFirst(keyword, "<font color=\"#aa0000\">" + keyword + "</font>"));
                }
            }
        }
        notifyDataSetChanged();
        return mCacheData.size() <= 0;
    }
}
