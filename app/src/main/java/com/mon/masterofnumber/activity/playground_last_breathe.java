package com.mon.masterofnumber.activity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.Timer;
import java.util.TimerTask;

import static com.mon.masterofnumber.engine.ArcadeModes.LastBreath;

public class playground_last_breathe extends AppCompatActivity {

    GameGenerator gg=null;
    fragment_main_controller mController;
    button_adapter mButtonAdapter;
    List<GameData> lst;
    TextView text_to_start;
    TextView text_timer;
    Timer timer_to_reset;
    Timer timer_to_play;

    final ArrayList<Button> allVisualbuttons = new ArrayList<Button>();
    final ArrayList<Button> visualbuttons = new ArrayList<Button>();
    final ArrayList<NVButton> buttons = new ArrayList<NVButton>();
    int lev =0;
    int order = 1;
    int countdown_to_start=6;
    int seconds=0;
    int millisecon = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground_last_breathe);

        text_timer = (TextView) findViewById(R.id.timertext);
        text_timer.setText("");
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


        countdown_to_start();

       /*
        gg = new GameGenerator();
        lst = gg.CreateGame(lev,LastBreath,0);
        create_arcade();
        seconds = lst.get(0).Time;
        millisecon = 0;

        timer_to_play = new Timer();
        timer_to_play. schedule(new PlayTask(), seconds,1);
*/
    }

    public void clicked(ButtonEventInfo info)
    {
        int a = info.mModelButton.mOrder;
        visualbuttons.add(info.mVisualButton);

        if (a==order)
        {
            info.mVisualButton.setBackgroundColor(Color.argb(100,0,255,0));
            order++;

            String v[] = lst.get(0).Sequance.split(";");
            if (order>v.length)
            {
                timer_to_play.cancel();
                you_win();
            }
        }
        else
        {
           for (int i=0;i<visualbuttons.size();i++)
                visualbuttons.get(i).setBackgroundColor(Color.argb(100,255,0,0));

            timer_to_reset = new Timer();
            timer_to_reset.schedule(new RemindTask(), 200);

            order=1;
        }
    }

    void countdown_to_start()
    {
        countdown_to_start=6;

        LinearLayout top = (LinearLayout)findViewById(R.id.parent_playground);
        top.removeAllViews();

        text_timer.setText("Livello: " + lev);
        text_to_start = new TextView(this);
        text_to_start.setTextSize(250);
        text_to_start.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        text_to_start.setLayoutParams(new GridView.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        top.addView(text_to_start);
        text_to_start.setTextColor(Color.parseColor("#000000"));
        text_to_start.setText("5");

        timer_to_play = new Timer();
        timer_to_play. schedule(new StartTask(), countdown_to_start,1000);
    }

    //'region timer cb'
    class StartTask extends TimerTask {
        public void run() {
            countdown_to_start--;
            String s = Integer.toString(countdown_to_start);
            text_to_start.setText(s);

            if (countdown_to_start==0) {
                timer_to_play.cancel();

                runOnUiThread(new Runnable() {
                    public void run() {
                        start();
                    }
                });

            }
        }
    }

    class RemindTask extends TimerTask {
        public void run() {
            for (int i=0;i<visualbuttons.size();i++)
                visualbuttons.get(i).setBackgroundColor(Color.parseColor("#3355FF"));
        }
    }

    class PlayTask extends TimerTask {
        public void run() {
            if(millisecon==0)
            {
                millisecon=999;
                seconds--;
            }
            else
            {
                millisecon--;
            }


            if (seconds<=10 && seconds>5) {
                if (millisecon == 500)
                    text_timer.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                else if (millisecon == 0)
                    text_timer.setTextColor(Color.parseColor("#000000"));
            }
            if (seconds<=5)
                text_timer.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

            text_timer.setText(seconds + ":" + millisecon);
            if (seconds==0 && millisecon==0) {
                timer_to_play.cancel();
                you_loose();
            }
        }
    }


    //'region timer cb'


    private void start()
    {
        gg = new GameGenerator();
        lst = gg.CreateGame(lev,LastBreath,0);
        create_arcade();
        seconds = lst.get(0).Time;
        millisecon = 0;

        timer_to_play = new Timer();
        timer_to_play. schedule(new PlayTask(), seconds,1);

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


    public void you_win()
    {
        lev++;
        countdown_to_start();

    }

    public void you_loose()
    {

    }

}
