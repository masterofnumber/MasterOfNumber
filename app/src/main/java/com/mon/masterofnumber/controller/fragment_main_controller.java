package com.mon.masterofnumber.controller;

import com.mon.masterofnumber.structure.CallBack;
import com.mon.masterofnumber.structure.NVButton;
import com.mon.masterofnumber.structure.enumeration;

import java.util.ArrayList;
import java.util.List;


class itemCallback
{
    public enumeration.TEvent ev;
    public CallBack<Void,NVButton> cb;
};

public class fragment_main_controller {



    private List<itemCallback> list = new ArrayList<itemCallback>();;


    public void register(enumeration.TEvent event, CallBack<Void,NVButton>  cb)
    {
        itemCallback it = new itemCallback();
        it.ev = event;
        it.cb= cb;
        list.add(it);
    }

    public void notify(enumeration.TEvent event, NVButton identity)
    {
        for (itemCallback it : list) {
            if (it.ev == event) {
                try {
                    it.cb.call(identity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
