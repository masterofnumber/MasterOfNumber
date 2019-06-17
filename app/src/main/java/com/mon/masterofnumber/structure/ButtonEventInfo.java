package com.mon.masterofnumber.structure;

import android.widget.Button;

public class ButtonEventInfo {

    public Button mVisualButton;
    public NVButton mModelButton;

    public ButtonEventInfo( NVButton nv,Button b)
    {
        mModelButton=nv;
        mVisualButton =b;
    }

}
