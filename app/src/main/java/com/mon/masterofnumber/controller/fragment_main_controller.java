package com.mon.masterofnumber.controller;

import com.mon.masterofnumber.structure.enumeration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.security.auth.callback.CallbackHandler;

class itemCallback
{
    public enumeration.TEvent ev;
    public Callable<Void> cb;
};

public class fragment_main_controller {



    private List<itemCallback> list = new ArrayList<itemCallback>();;


    public void register(enumeration.TEvent event, Callable<Void>  cb)
    {
        itemCallback it = new itemCallback();
        it.ev = event;
        it.cb= cb;
        list.add(it);
    }

    public void notify(enumeration.TEvent event)
    {
        for (itemCallback it : list) {
            if (it.ev == event) {
                try {
                    it.cb.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
