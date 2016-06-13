package com.sam_chordas.android.stockhawk.network;

import com.sam_chordas.android.stockhawk.Stock_Const;
import com.sam_chordas.android.stockhawk.pojomodel.charts.Historic;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dzarrillo on 6/3/2016.
 */
public interface StockAPI {

    @GET("v1/public/yql?&format=json&diagnostics=true&env=store://datatables.org/alltableswithkeys&callback=")
    Call<Historic> getStockData(@Query("q") String q);

    class Factory{
        private static StockAPI service;

        public static StockAPI getInstance(){
            if(service==null){
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(Stock_Const.BASE_URL)
                        .build();
                service = retrofit.create(StockAPI.class);
                return service;
                } else{
                return service;
            }
        }
    }
}
