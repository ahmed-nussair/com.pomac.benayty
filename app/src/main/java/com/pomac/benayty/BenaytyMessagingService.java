package com.pomac.benayty;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pomac.benayty.apis.FcmTokenUpdateApi;
import com.pomac.benayty.model.response.FcmTokenUpdateResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
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


        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.getApplication().openFileOutput("config.txt", Context.MODE_APPEND));
            outputStreamWriter.append(remoteMessage.getNotification().getBody());
            outputStreamWriter.append("\n");
            outputStreamWriter.close();
            Log.d(Globals.TAG, "Done writing");
        } catch (IOException e) {
            Log.e(Globals.TAG, "File write failed: " + e.toString());
        }

        String ret = "";

        List<String> list = new ArrayList<>();

        try {
            InputStream inputStream = this.getApplication().openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
//                    stringBuilder.append("\n").append(receiveString);
                    list.add(receiveString);
                }

                inputStream.close();
//                ret = stringBuilder.toString();

                Log.d(Globals.TAG, "" + list.size());
                for (String item : list) {
                    Log.d(Globals.TAG, item);
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.d(Globals.TAG, "New FCM token: " + s);
        sendRegistrationToServer(s);
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
