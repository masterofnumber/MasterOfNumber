package com.mon.masterofnumber.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mon.masterofnumber.R;
import com.mon.masterofnumber.controller.fragment_main_controller;
import com.mon.masterofnumber.structure.NVButton;
import com.mon.masterofnumber.structure.enumeration;

public class button_adapter extends BaseAdapter {

    private final int mSize;
    private final Context mContext;
    private final NVButton[] mButtons;
    private fragment_main_controller mController = null;

    public button_adapter(Context context, int size, NVButton[] buttons, fragment_main_controller controller) {
        this.mContext = context;
        this.mSize=size;
        this.mButtons = buttons;
        this.mController = controller;
    }

    @Override
    public int getCount() {
        return mButtons.length;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.activity_fragment_main, null);
        }

        final LinearLayout bk = (LinearLayout)convertView.findViewById(R.id.back);
        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(mSize,mSize);
         bk.setLayoutParams(lp);

        // 3
        final Button qlk = (Button)convertView.findViewById(R.id.qlk);
        qlk.setText(mButtons[position].mText);
        qlk.setWidth(mSize);
        qlk.setHeight(mSize);

        qlk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mController.notify(enumeration.TEvent.click_button);
            }
        });
        return convertView;

    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }




}
