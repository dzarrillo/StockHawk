package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.rest.Utils;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {
    // DZ - To be able to send a toast message to the user
    Handler mHandler;

    public StockIntentService() {
        super(StockIntentService.class.getName());
        // DZ - To be able to send a toast message to the user
        mHandler = new Handler();
    }

    public StockIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
        StockTaskService stockTaskService = new StockTaskService(this);
        Bundle args = new Bundle();

        if (intent.getStringExtra("tag").equals("add")) {
            args.putString("symbol", intent.getStringExtra("symbol"));
        }
        // We can call OnRunTask from the intent service to force it to run immediately instead of
        // scheduling a task.
        stockTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
        // DZ - if symbol not found notify user with toast message
        if(Utils.SYMBOL_NOT_FOUND){
            Log.d("StockIntentService", " Symbol not found!!!!!");
            mHandler.post(new DisplayToast(this, "Symbol not found!"));
        }
    }
}
