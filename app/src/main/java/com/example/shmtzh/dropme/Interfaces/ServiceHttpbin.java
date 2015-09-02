package com.example.shmtzh.dropme.Interfaces;

import com.example.shmtzh.dropme.DataModels.HistoryModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ServiceHttpbin {


    @POST("/post")
    public void sendModel(@Body String data, Callback<HistoryModel> callBack);

}
