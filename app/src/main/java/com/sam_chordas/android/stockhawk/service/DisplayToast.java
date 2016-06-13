package com.sam_chordas.android.stockhawk.service;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by dzarrillo on 5/27/2016.
 */
public class DisplayToast implements Runnable {
    private final Context mContext;
    private final String mText;

    public DisplayToast(Context mContext, String text){
        this.mContext = mContext;
        mText = text;
    }
    @Override
    public void run() {
        showMyToast(mText);
    }

    public void showMyToast(String msg) {
        TextView tv = new TextView(mContext);
        tv.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_grey));
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        tv.setPadding(35, 35, 35, 35);
        tv.setTextSize(19);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setText(msg);

        Toast tt = new Toast(mContext);
        tt.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 150);
        tt.setView(tv);
        tt.show();
    }
}
