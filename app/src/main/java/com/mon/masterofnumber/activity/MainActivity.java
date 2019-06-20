package com.mon.masterofnumber.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.mon.masterofnumber.engine.ArcadeModes;
import com.mon.masterofnumber.engine.GameData;
import com.mon.masterofnumber.engine.GameGenerator;
import com.mon.masterofnumber.structure.ButtonEventInfo;
import com.mon.masterofnumber.structure.CallBack;
import com.mon.masterofnumber.R;
import com.mon.masterofnumber.fragment.adapter.button_adapter;
import com.mon.masterofnumber.controller.fragment_main_controller;
import com.mon.masterofnumber.structure.NVButton;
import com.mon.masterofnumber.structure.enumeration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    fragment_main_controller mController;
    button_adapter mButtonAdapter;
    final ArrayList<NVButton> buttons = new ArrayList<NVButton>();


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mController = new fragment_main_controller();
        mController.register(enumeration.TEvent.click_button, new CallBack<Void, ButtonEventInfo>() {
                    @Override
                    public Void call(ButtonEventInfo val) {
                        clicked(val);
                        return null;
                    }
                });

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitleTextColor(Color.BLACK);
        tb.setTitle(getResources().getString(R.string.title));


        create_base_home();



    }

    public void clicked(ButtonEventInfo info)
    {
        switch (info.mModelButton.mId) {
            case 1: //arcade
                create_arcade();
                break;
            case 4: // lastbreathe
                Intent playground = new Intent(MainActivity.this,playground_last_breathe.class);
                startActivity(playground);
                break;
            case 6: //back
                create_base_home();
                break;
        }
    }

    private void create_arcade()
    {
        LinearLayout top = (LinearLayout)findViewById(R.id.parent_playground);
        top.removeAllViews();

        GridView playground= new GridView(this);

        playground.setLayoutParams(new GridView.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        top.addView(playground);

        buttons.clear();
        buttons.add( new NVButton(getResources().getString(R.string.lastbreathe),4));
        buttons.add(new NVButton(getResources().getString(R.string.round),5));
        buttons.add(new NVButton(getResources().getString(R.string.back),6));

        set_adapter(playground, 1,3,buttons);

    }

    private void create_base_home()
    {
        LinearLayout parent_play =(LinearLayout)findViewById(R.id.parent_playground);

        buttons.clear();
        buttons.add(new NVButton(getResources().getString(R.string.arcade),1));
        buttons.add( new NVButton(getResources().getString(R.string.challenge),2));
        buttons.add( new NVButton(getResources().getString(R.string.ranking),3));


        GridView playground = (GridView)findViewById(R.id.playground);

        set_adapter(playground, 4,3,buttons);

    }

    private void set_adapter(GridView playground, int rowCount, int colCount, ArrayList<NVButton> buttons)
    {
       /* playground.setNumColumns(colCount);
        playground.setGravity(Gravity.CENTER);
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);

        int h = (size.y/100)*70;
        h-=100;
        int w = size.x;
        int s = (w)/colCount ; //(h)/rowCount< (w)/colCount ? (h)/rowCount :(w)/colCount;
        if (rowCount==1 && h>300)
            s=300;

        mButtonAdapter = new button_adapter(this,s,s,buttons,mController);
        playground.setAdapter(mButtonAdapter);
        mButtonAdapter.notifyDataSetChanged();*/

        playground.setNumColumns(colCount);


        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);

        size.y -= 60;
        size.y -= (size.y/100)*20;
        size.y-=(10*rowCount);

        int w = size.x;
        //int s = (h)/lst.get(0).Rows< (w)/lst.get(0).Columns ? (h)/lst.get(0).Rows :(w)/lst.get(0).Columns;
        //if (lst.get(0).Columns==1 && h>300)
        //  s=300;
        int s = (w)/colCount ; //(h)/rowCount< (w)/colCount ? (h)/rowCount :(w)/colCount;
        int h = size.y / rowCount;
           h=400;

        mButtonAdapter = new button_adapter(this,h,s,buttons,mController);

        //mButtonAdapter = new button_adapter(this,s,s,buttons,mController);
        playground.setAdapter(mButtonAdapter);
        mButtonAdapter.notifyDataSetChanged();



    }

}
