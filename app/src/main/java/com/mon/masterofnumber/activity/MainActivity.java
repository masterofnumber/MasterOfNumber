package com.mon.masterofnumber.activity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.widget.GridView;
import android.widget.LinearLayout;
import com.mon.masterofnumber.structure.CallBack;
import com.mon.masterofnumber.R;
import com.mon.masterofnumber.fragment.adapter.button_adapter;
import com.mon.masterofnumber.controller.fragment_main_controller;
import com.mon.masterofnumber.structure.NVButton;
import com.mon.masterofnumber.structure.enumeration;

public class MainActivity extends AppCompatActivity {

    fragment_main_controller mController;

    final int BTN_ARCADE=1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mController = new fragment_main_controller();
        mController.register(enumeration.TEvent.click_button, new CallBack<Void, NVButton>() {
                    @Override
                    public Void call(NVButton val) {
                        clicked(val);
                        return null;
                    }
                });

                Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitleTextColor(Color.WHITE);
        tb.setTitle(getResources().getString(R.string.title));


        Create_base_home();



    }

    public void clicked(NVButton button)
    {
        switch (button.mId) {
            case BTN_ARCADE:

                break;
        }
    }

    private void Create_base_home()
    {
        GridView playground = (GridView)findViewById(R.id.playground);
        LinearLayout parent_play =(LinearLayout)findViewById(R.id.parent_playground);


        int colCount = 2;
        int rowCount=2;
        playground.setNumColumns(colCount);


        NVButton[] buttons = new NVButton[3];
        buttons[0] = new NVButton(getResources().getString(R.string.arcade),BTN_ARCADE);
        buttons[1] = new NVButton(getResources().getString(R.string.challenge),2);
        buttons[2] = new NVButton(getResources().getString(R.string.ranking),3);

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);

        int h = (size.y/100)*70;
        h-=100;
        int w = size.x;
        int s = (h)/rowCount< (w)/colCount ? (h)/rowCount :(w)/colCount;
        if (colCount==1 && h>300)
            s=300;

        final button_adapter booksAdapter = new button_adapter(this,s,buttons,mController);
        playground.setAdapter(booksAdapter);

    }

}
