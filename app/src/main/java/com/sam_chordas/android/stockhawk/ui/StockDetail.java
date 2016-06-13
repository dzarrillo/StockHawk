package com.sam_chordas.android.stockhawk.ui;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.Stock_Const;
import com.sam_chordas.android.stockhawk.network.StockAPI;
import com.sam_chordas.android.stockhawk.pojomodel.StockQuote;
import com.sam_chordas.android.stockhawk.pojomodel.charts.Historic;
import com.sam_chordas.android.stockhawk.pojomodel.charts.Quote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dzarrillo on 6/1/2016.
 */
public class StockDetail extends Fragment implements TabHost.OnTabChangeListener{
    private final String TAG = StockDetail.class.getSimpleName();
    private ArrayList<StockQuote> mStockQuoteList = new ArrayList<>();
    private String mStartDate, mEndDate;
    private TextView mNameView, mSymbolView, mChange;
    private TextView mBidPriceView;
    private TabHost mTabHost;
    private LineChartView mChart;
    private View mTabContent;
    private String mSelectedTab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stock_detail, container, false);
        initializeWidgets(v);

        getStockMonthDate();

        String q = "select * from yahoo.finance.historicaldata where symbol = \'"
                + Stock_Const.mySymbol + "\' and startDate = \'"
                + mStartDate + "\' and endDate = \'"
                + mEndDate + "\'";

        getAPIData(q);
        return v;
    }

    private void getStockMonthDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = new Date();
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(currentDate);
        calEnd.add(Calendar.DATE, 0);
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(currentDate);
        calStart.add(Calendar.MONTH, -1);
        mStartDate = dateFormat.format(calStart.getTime());
        mEndDate = dateFormat.format(calEnd.getTime());
    }

    private void initializeWidgets(View v){
        mNameView = (TextView) v.findViewById(R.id.stock_name);
        mSymbolView = (TextView) v.findViewById(R.id.stock_symbol);
        mBidPriceView = (TextView) v.findViewById(R.id.stock_price);
        mChange = (TextView) v.findViewById(R.id.stock_change);
        mChart = (LineChartView) v.findViewById(R.id.stock_chart);
        mTabHost = (TabHost) v.findViewById(android.R.id.tabhost);
    }

    private void setupTabs() {
        mTabHost.setup();

        TabHost.TabSpec tabSpec;
        tabSpec = mTabHost.newTabSpec(getString(R.string.stock_detail_tab1));
        tabSpec.setIndicator(getString(R.string.stock_detail_tab1));
        tabSpec.setContent(android.R.id.tabcontent);
        mTabHost.addTab(tabSpec);

        tabSpec = mTabHost.newTabSpec(getString(R.string.stock_detail_tab2));
        tabSpec.setIndicator(getString(R.string.stock_detail_tab2));
        tabSpec.setContent(android.R.id.tabcontent);
        mTabHost.addTab(tabSpec);

        tabSpec = mTabHost.newTabSpec(getString(R.string.stock_detail_tab3));
        tabSpec.setIndicator(getString(R.string.stock_detail_tab3));
        tabSpec.setContent(android.R.id.tabcontent);
        mTabHost.addTab(tabSpec);

        mTabHost.setOnTabChangedListener(this);

        if (mSelectedTab.equals(getString(R.string.stock_detail_tab2))) {
            mTabHost.setCurrentTab(1);
        } else if (mSelectedTab.equals(getString(R.string.stock_detail_tab3))) {
            mTabHost.setCurrentTab(2);
        } else {
            mTabHost.setCurrentTab(0);
        }
    }


    protected void getAPIData(String q) {
//        StockAPI.Factory.getInstance().getStockData("YHOO","2016-06-03","2016-06-03").enqueue(new callback<Historic>(){
        StockAPI.Factory.getInstance().getStockData(q).enqueue(new Callback<Historic>(){

            @Override
            public void onResponse(Call<Historic> call, Response<Historic> response) {
                if(response.isSuccessful()){
                    if(response.body().getQuery().getResults().getQuote().size() > 0){
                        int count = 1;
                        for (Quote quote : response.body().getQuery().getResults().getQuote()){
                            StockQuote stockquote = new StockQuote();
                            stockquote.setId(count);
                            stockquote.setClose(quote.getClose());
                            stockquote.setDate(quote.getDate());
                            stockquote.setHigh(quote.getHigh());
                            stockquote.setLow(quote.getLow());
                            stockquote.setOpen(quote.getOpen());
                            stockquote.setSymbol(quote.getSymbol());
                            stockquote.setVolume(quote.getVolume());
                            mStockQuoteList.add(stockquote);
                            count++;
                        }
                        //mAdapter.notifyDataSetChanged();
                        updateDisplay(count);
                        updateChart(count);
                    }
                } else{
                    ResponseBody errBody = response.errorBody();
                    Log.d(TAG, errBody.toString());
                    showMyToast(errBody.toString());
                }
            }

            @Override
            public void onFailure(Call<Historic> call, Throwable t) {
                showMyToast("Failed to get Data!");
            }
        });
    }

    public void showMyToast(String msg) {
        TextView tv = new TextView(getActivity());
        tv.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.dark_grey));
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        tv.setPadding(25, 25, 25, 25);
        tv.setTextSize(19);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setText(msg);

        Toast tt = new Toast(getActivity());
        tt.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 150);
        tt.setView(tv);
        tt.show();
    }

    @Override
    public void onTabChanged(String tabId) {
        mSelectedTab = tabId;

    }

    private void updateChart(int count){
        List<AxisValue> axisValuesX = new ArrayList<>();
        List<PointValue> pointValues = new ArrayList<>();

        int counter = -1;

        for (StockQuote stockQuote : mStockQuoteList){
            counter++;

            String date = stockQuote.getDate();
            String bidPrice = String.format("%.2f", Float.parseFloat(stockQuote.getClose()));

            int x = count - 1 - counter;

            // Point for line chart (date, price).
            PointValue pointValue = new PointValue(x, Float.valueOf(bidPrice));
            pointValue.setLabel(date);
            pointValues.add(pointValue);

            // Set labels for x-axis (we have to reduce its number to avoid overlapping text).
            if (counter != 0 && counter % (count / 3) == 0) {
                AxisValue axisValueX = new AxisValue(x);
                axisValueX.setLabel(date);
                axisValuesX.add(axisValueX);
            }
        }

        // Prepare data for chart
        Line line = new Line(pointValues).setColor(Color.WHITE).setCubic(false);
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        // Init x-axis
        Axis axisX = new Axis(axisValuesX);
        axisX.setHasLines(true);
        axisX.setMaxLabelChars(4);
        lineChartData.setAxisXBottom(axisX);

        // Init y-axis
        Axis axisY = new Axis();
        axisY.setAutoGenerated(true);
        axisY.setHasLines(true);
        axisY.setMaxLabelChars(4);
        lineChartData.setAxisYLeft(axisY);

        // Update chart with new data.
        mChart.setInteractive(false);
        mChart.setLineChartData(lineChartData);

        // Show chart
        mChart.setVisibility(View.VISIBLE);
//        mTabContent.setVisibility(View.VISIBLE);
    }

    private void updateDisplay(int count){
        String symbol = Stock_Const.mySymbol;
        mSymbolView.setText(getString(R.string.stock_detail_tab_header, symbol));

        String value = Stock_Const.myBidPrice;
        mBidPriceView.setText(value);

        mNameView.setText(symbol);

        String change = Stock_Const.myChange;
        String percentChange = Stock_Const.myPercentChange;
        String mixedChange = change + " (" + percentChange + ")";
        mChange.setText(mixedChange);
    }
}
