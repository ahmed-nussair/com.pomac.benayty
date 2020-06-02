package com.pomac.benayty;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pomac.benayty.apis.FcmTokenUpdateApi;
import com.pomac.benayty.model.response.FcmTokenUpdateResponse;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BenaytyMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(Globals.TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(Globals.TAG, "Message data payload: " + remoteMessage.getData());
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(Globals.TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.d(Globals.TAG, "New FCM token: " + s);
//        sendRegistrationToServer(s);

        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFERENCES, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Globals.FCM_TOKEN, s);

        if (editor.commit()) {
            Log.d(Globals.TAG, "FCM Token: " + sharedPreferences.getString(Globals.FCM_TOKEN, ""));
        }
    }

    private void sendRegistrationToServer(String s) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        FcmTokenUpdateApi api = retrofit.create(FcmTokenUpdateApi.class);

        Observable<FcmTokenUpdateResponse> observable = api.updateFcmToken(Globals.token, s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(
                response -> Log.d(Globals.TAG, response.getMessage()),
                error -> Log.e(Globals.TAG, Objects.requireNonNull(error.getMessage()))
        );

        Globals.compositeDisposable.add(disposable);
    }
}
