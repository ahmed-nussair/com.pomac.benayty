package com.pomac.benayty.apis;

import com.pomac.benayty.model.response.AddingCommentResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddingCommentApi {

    @FormUrlEncoded
    @POST("benaity/public/api/comment/add")
    Call<AddingCommentResponse> addComment(
            @Field("token") String token,
            @Field("advertisement_id") int adId,
            @Field("comment") String comment
    );
}
