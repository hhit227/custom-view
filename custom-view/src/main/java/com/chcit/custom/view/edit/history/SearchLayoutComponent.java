package com.chcit.custom.view.edit.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chcit.custom.view.R;
import com.chcit.custom.view.edit.ImageInputEditText;

/**
 * Date: 2019/3/31
 * Created by liuyang
 *
 * @author liuyang
 */

public class SearchLayoutComponent extends LinearLayoutCompat {

    private Context mContext;
    private int mSearchTextColor;
    private float mSearchTextSize;
    private String mSearchTextHint;
    private SearchCallback mSearchCallback;
    private int mSearchBoudColor;
    private int mSearchBoudHeight;
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private ImageInputEditText imageIputEditText;
    private TextView mClearHistory;
    private ResultListView mListView;
    public void setSearchCallback(SearchCallback mSearchCallback) {
        this.mSearchCallback = mSearchCallback;
    }

    public SearchLayoutComponent(Context context) {
        super(context);
        mContext = context;
        init();

    }

    public SearchLayoutComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        parseAttr(attrs);
        init();
    }


    private void init() {

        mSQLiteOpenHelper = new SearchHelper(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_layout, this);
        imageIputEditText = view.findViewById(R.id.et_search);
        mListView = view.findViewById(R.id.listView);
        mClearHistory = view.findViewById(R.id.tv_clear);
        imageIputEditText.setTextSize(mSearchTextSize);
        imageIputEditText.setHint(mSearchTextHint);
        imageIputEditText.setTextColor(mSearchTextColor);

        mClearHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????????????????
                SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
                        db.execSQL("delete from records");
                        db.close();
                        mClearHistory.setVisibility(INVISIBLE);
                        queryData("");
            }
        });


        imageIputEditText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN ){
                // ?????? ??????
                imageIputEditText.setFocusable(false);
                imageIputEditText.setFocusableInTouchMode(true);
                return  true;
            }

            return false;
        });
        imageIputEditText.setOnFocusChangeListener((v,hasFocus) -> {
            if(!hasFocus){
                String name = imageIputEditText.getText().toString().trim();
                if(!name.isEmpty()&&!hasData(name) ){
                    insertData(name);
                }
                if (mSearchCallback != null){
                    mSearchCallback.search(imageIputEditText.getText().toString().trim());
                }
            }
        });
        imageIputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //change before
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //changed
            }

            @Override
            public void afterTextChanged(Editable s) {
                //????????????
                queryData(imageIputEditText.getText().toString());

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //????????????item???????????????
                // ????????????????????????????????????,??????????????????????????????
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                imageIputEditText.setText(name);
                Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void insertData(String name) {

        SQLiteDatabase db = mSQLiteOpenHelper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + name + "')");
        db.close();
    }

    private boolean hasData(String name) {

        Cursor cursor = mSQLiteOpenHelper.getReadableDatabase().query(true,"records",new String[]{"name"},"name like ?",new String[]{name},null,null,null,null)
        ;
        boolean hasData = cursor.moveToNext();
        cursor.close();

        return hasData;

    }

    private void queryData(String name) {

            // 1. ????????????
            Cursor cursor = mSQLiteOpenHelper.getReadableDatabase().rawQuery(
                    "select id as _id,name from records where name like '%" + name + "%' order by id desc ", null);
            // 2. ??????adapter??????????????? & ???????????????????????????
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(mContext, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                    new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            // 3. ???????????????
            mListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            // ?????????????????? & ??????????????????????????????????????? "??????????????????"??????
            if ("".equals(name) && cursor.getCount() != 0){
                mClearHistory.setVisibility(VISIBLE);
            }
            else {
                mClearHistory.setVisibility(INVISIBLE);
            }

    }

    private void parseAttr(AttributeSet attrs) {

        @SuppressLint("CustomViewStyleable") TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.searchLayoutComponent);
        mSearchTextSize = typedArray.getDimension(R.styleable.searchLayoutComponent_textSizeSearch, 20);
        mSearchTextColor = typedArray.getInt(R.styleable.searchLayoutComponent_searchBlockColor, Color.BLACK);
        mSearchTextHint = typedArray.getString(R.styleable.searchLayoutComponent_textHintSearch);
        mSearchBoudColor = typedArray.getInt(R.styleable.searchLayoutComponent_searchBlockColor, Color.WHITE);
        mSearchBoudHeight = typedArray.getInt(R.styleable.searchLayoutComponent_searchBlockHeight, 100);
        typedArray.recycle();
    }

    public ImageInputEditText getImageEdit() {
        return imageIputEditText;
    }
}
