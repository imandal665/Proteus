package com.example.proteus.managers;

import com.flipkart.android.proteus.Styles;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProteusApi {

    @GET("user.json")
    Call<ObjectValue> getUserData();

    @GET("styles.json")
    Call<Styles> getStyles();

    @GET("layout.json")
    Call<Layout> getLayout();

    @GET("layouts.json")
    Call<Map<String, Layout>> getLayouts();

}
