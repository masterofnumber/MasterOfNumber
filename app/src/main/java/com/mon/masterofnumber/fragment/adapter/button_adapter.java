package com.mon.masterofnumber.fragment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mon.masterofnumber.R;
import com.mon.masterofnumber.controller.fragment_main_controller;
import com.mon.masterofnumber.structure.ButtonEventInfo;
import com.mon.masterofnumber.structure.NVButton;
import com.mon.masterofnumber.structure.enumeration;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

 class ButtonViewHolder extends RecyclerView.ViewHolder {
    Button qlk;
    RelativeLayout bk;
    public ButtonViewHolder(View itemView) {
        super(itemView);

    }
}

public class button_adapter extends BaseAdapter {

    private final int mSize;
    private final Context mContext;
    private final ArrayList<NVButton> mButtons;
    private fragment_main_controller mController = null;

    public button_adapter(Context context, int size, ArrayList<NVButton> buttons, fragment_main_controller controller) {
        this.mContext = context;
        this.mSize=size;
        this.mButtons = buttons;
        this.mController = controller;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder view;

        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(R.layout.activity_fragment_main, null);
        view = new ButtonViewHolder(convertView);

        ((ButtonViewHolder) view).bk = (RelativeLayout)convertView.findViewById(R.id.back);
        ((ButtonViewHolder) view).qlk = (Button)convertView.findViewById(R.id.qlk);
        convertView.setTag(view);


        LinearLayout.LayoutParams lp =  new LinearLayout.LayoutParams(mSize,mSize);
        ((ButtonViewHolder) view).bk.setLayoutParams(lp);
        ((ButtonViewHolder) view).qlk.setText(mButtons.get(position).mText);
        ((ButtonViewHolder) view).qlk.setWidth(mSize);
        ((ButtonViewHolder) view).qlk.setHeight(mSize);
        ((ButtonViewHolder) view).qlk.setTag(position);
        ((ButtonViewHolder) view).qlk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               int pos =  Integer.parseInt(v.getTag().toString());
                ButtonEventInfo info = new ButtonEventInfo(mButtons.get(pos), (Button)v);
                mController.notify(enumeration.TEvent.click_button,info );
            }
        });
        return convertView;

    }



    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    public void removeItem(int position){
        mButtons.remove(position);
        notifyDataSetChanged();
    }


}
