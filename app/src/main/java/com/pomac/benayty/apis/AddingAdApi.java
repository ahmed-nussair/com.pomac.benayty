package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.AddingAdResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AddingAdApi {

    @Multipart
    @POST("benaity/public/api/advertisements/add")
    Observable<AddingAdResponse> addAdvertisement(
            @Body RequestBody data
    );

    @Multipart
    @POST("benaity/public/api/advertisements/add")
    Observable<AddingAdResponse> addAdvertisement(
            @Part("token") RequestBody token,
            @Part("main_id") RequestBody mainCategoryId,
            @Part("secondary_id") RequestBody secondaryCategoryId,
            @Part("area_id") RequestBody areaId,
            @Part("city_id") RequestBody cityId,
            @Part("title") RequestBody title,
            @Part("phone") RequestBody phone,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part image
    );
}
