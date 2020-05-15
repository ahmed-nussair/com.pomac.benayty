package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.ContactUsResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContactUsApi {

    @POST("benaity/public/api/contact_ud")
    Observable<ContactUsResponse> send(@Body Map<String, String> data);
}
