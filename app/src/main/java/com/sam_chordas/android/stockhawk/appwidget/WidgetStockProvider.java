package com.sam_chordas.android.stockhawk.appwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;

/**
 * Created by dzarrillo on 5/29/2016.
 */
public class WidgetStockProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int i : appWidgetIds){
            updateWidget(context, appWidgetManager, i);
            RemoteViews rv = new RemoteViews(context.getPackageName(),
                    R.layout.widget_stocks);
            Intent adapter = new Intent(context, WidgetStockService.class);
            adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);
            rv.setRemoteAdapter(R.id.listViewStocks, adapter);

            appWidgetManager.updateAppWidget(appWidgetIds, rv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,
                    R.id.listViewStocks);
        }


    }

    void updateWidget(Context context, AppWidgetManager appWidgetManager,
                      int appWidgetId) {
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.widget_stocks);
        setList(rv, context, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,
                R.id.listViewStocks);
    }

    void setList(RemoteViews rv, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, WidgetStockService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rv.setRemoteAdapter(R.id.listViewStocks, adapter);
    }
}
