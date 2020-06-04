package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.FcmSendResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FcmSendApi {

    @POST("fcm/send")
    Call<FcmSendResponse> send(@Header("Authorization") String authorization, @Body Map<String, Object> body);
}
