package com.sam_chordas.android.stockhawk.pojomodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dzarrillo on 6/4/2016.
 */
public class StockQuote implements Parcelable{
    private int id;
    private String symbol;
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;


    public StockQuote(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // The following has to be read in the same order in Parcelable.Creator
        dest.writeInt(id);
        dest.writeString(symbol);
        dest.writeString(date);
        dest.writeString(open);
        dest.writeString(high);
        dest.writeString(low);
        dest.writeString(close);
        dest.writeString(volume);
    }

    public StockQuote(Parcel input){
        id=input.readInt();
        symbol=input.readString();
        date=input.readString();
        open=input.readString();
        high=input.readString();
        low=input.readString();
        close=input.readString();
        volume=input.readString();
    }

    public static final Creator<StockQuote> CREATOR
            = new Creator<StockQuote>() {
        public StockQuote createFromParcel(Parcel in) {
            return new StockQuote(in);
        }

        public StockQuote[] newArray(int size) {
            return new StockQuote[size];
        }
    };
}
