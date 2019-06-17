package com.mon.masterofnumber.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.mon.masterofnumber.R;
import com.mon.masterofnumber.controller.fragment_main_controller;
import com.mon.masterofnumber.engine.GameData;
import com.mon.masterofnumber.engine.GameGenerator;
import com.mon.masterofnumber.fragment.adapter.button_adapter;
import com.mon.masterofnumber.structure.ButtonEventInfo;
import com.mon.masterofnumber.structure.CallBack;
import com.mon.masterofnumber.structure.NVButton;
import com.mon.masterofnumber.structure.enumeration;

import java.util.ArrayList;
import java.util.List;

import static com.mon.masterofnumber.engine.ArcadeModes.LastBreath;

public class playground_last_breathe extends AppCompatActivity {

    GameGenerator gg=null;
    fragment_main_controller mController;
    button_adapter mButtonAdapter;
    List<GameData> lst;
    final ArrayList<NVButton> buttons = new ArrayList<NVButton>();
    int lev =0;
    int order = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground_last_breathe);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitleTextColor(Color.BLACK);
        tb.setTitle(getResources().getString(R.string.title));

        mController = new fragment_main_controller();
        mController.register(enumeration.TEvent.click_button, new CallBack<Void, ButtonEventInfo>() {
            @Override
            public Void call(ButtonEventInfo val) {
                clicked(val);
                return null;
            }
        });

        gg = new GameGenerator();
        lst = gg.CreateGame(lev,LastBreath,0);

        create_arcade();


    }

    public void clicked(ButtonEventInfo info)
    {
        int a = info.mModelButton.mOrder;

        if (a==order)
        {
            info.mVisualButton.setBackgroundColor(Color.argb(100,0,255,0));
            order++;
        }
        else
        {
            info.mVisualButton.setBackgroundColor(Color.argb(100,255,0,0));
            mButtonAdapter.reset();
            order=1;
        }
    }

    private void create_arcade()
    {
        LinearLayout top = (LinearLayout)findViewById(R.id.parent_playground);
        top.removeAllViews();

        GridView playground= new GridView(this);

        playground.setLayoutParams(new GridView.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        top.addView(playground);


        String s = lst.get(0).Sequance;
        String o = lst.get(0).OrderNumberSequence;

        String v[] = s.split(";");
        String v2[] = o.split(";");

        buttons.clear();

        for (int i=0;i<v.length;i++) {
            NVButton b = new NVButton(v[i], i+100);
            b.mOrder = Integer.parseInt(v2[i]);
            buttons.add(b);
        }
        set_adapter(playground,buttons);
    }


    private void set_adapter(GridView playground, ArrayList<NVButton> buttons)
    {


        playground.setNumColumns(lst.get(0).Columns);

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);

        int h = (size.y/100)*70;
        h-=100;
        int w = size.x;
        int s = (h)/lst.get(0).Rows< (w)/lst.get(0).Columns ? (h)/lst.get(0).Rows :(w)/lst.get(0).Columns;
        if (lst.get(0).Columns==1 && h>300)
            s=300;

        mButtonAdapter = new button_adapter(this,s,buttons,mController);
        playground.setAdapter(mButtonAdapter);
        mButtonAdapter.notifyDataSetChanged();


    }
}
