package com.jiaohe.wygamsdk.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;

import com.jiaohe.wygamsdk.R;

/**
 * @package: com.jiaohe.wygamsdk.widget
 * @user:xhkj
 * @date:2019/7/30
 * @description:可下拉选择的输入框
 **/
public class EditableSpinner extends LinearLayout implements AdapterView.OnItemClickListener {

    private ImageButton mImgBtnDown;
    private EditText mEtInput;
    private ListPopupWindow mListPopupWindow;
    private OnItemClickListener mOnItemClickListener;
    private ArrayAdapter mArrayAdapter;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public EditableSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.wygamesdk_editable_spinner, this);

        mImgBtnDown = findViewById(R.id.bsgamesdk_edit_username_drop);
        mEtInput = findViewById(R.id.bsgamesdk_edit_username_login);
        mListPopupWindow = new ListPopupWindow(context);
        LayoutParams lp = (LayoutParams) mImgBtnDown.getLayoutParams();
        mImgBtnDown.setLayoutParams(lp);

        mImgBtnDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPopupWindow.show();
            }
        });
    }

    public void setText(String text) {
        if (!TextUtils.isEmpty(text)) {
            mEtInput.setText(text);
        }
    }

    public EditableSpinner setAdapter(ArrayAdapter<String> adapter) {

        mArrayAdapter = adapter;

        mListPopupWindow.setAdapter(adapter);
        mListPopupWindow.setAnchorView(mEtInput);
        mListPopupWindow.setModal(true);
        mListPopupWindow.setOnItemClickListener(this);
        return this;
    }

    public EditableSpinner setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        return this;
    }

    public String getSelectedItem() {
        return mEtInput.getText().toString();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListPopupWindow.dismiss();
        mEtInput.setText((CharSequence) mArrayAdapter.getItem(position));
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(position);
        }
    }
}
