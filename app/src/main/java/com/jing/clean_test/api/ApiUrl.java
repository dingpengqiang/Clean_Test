package com.jing.clean_test.api;


import com.jing.clean_test.model.JavaBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ding.pengqiang
 * on 2017/3/16.
 */

public interface ApiUrl {



    @GET("/facts.json")
    Call<JavaBean> getApiUrl();
}
