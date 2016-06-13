package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by dzarrillo on 5/27/2016.
 */
public class MyMessageDlg extends DialogFragment {
    private Button btnOk;
    private TextView tvMessage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String msg;

        msg = this.getArguments().getString("MESSAGE");

        View v = inflater.inflate(R.layout.mymessagedlg,null);

//        getDialog().setTitle("Network Error");

        //  DZ - Make sure user clicks on ok to leave this screen
        setCancelable(false);
        tvMessage = (TextView) v.findViewById(R.id.textViewMessage);
        tvMessage.setText(msg);

        btnOk = (Button) v.findViewById(R.id.buttonOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}
