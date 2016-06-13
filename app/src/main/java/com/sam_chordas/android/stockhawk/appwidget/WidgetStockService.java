package com.sam_chordas.android.stockhawk.appwidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by dzarrillo on 5/29/2016.
 */
public class WidgetStockService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetStockFactory(getApplicationContext(), intent);
    }
}
