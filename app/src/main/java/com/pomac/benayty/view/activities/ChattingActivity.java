package com.pomac.benayty.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.ChattingAdapter;
import com.pomac.benayty.apis.FcmSendApi;
import com.pomac.benayty.model.response.FcmSendResponse;
import com.pomac.benayty.view.helperclasses.ChattingMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class ChattingActivity extends AppCompatActivity {

    private String from;
    private String to;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ((TextView) findViewById(R.id.chattingPageTitle)).setText(getIntent().getStringExtra(Globals.USER_NAME));
        ((ImageView) findViewById(R.id.backToMain)).setOnClickListener(v -> finish());


        from = getIntent().getStringExtra(Globals.FROM);
        to = getIntent().getStringExtra(Globals.TO);


        ((ImageView) findViewById(R.id.sendMessageButtonImageView)).setOnClickListener(v -> {
            EditText chattingEditText = findViewById(R.id.chattingEditText);
            if (chattingEditText.getText().toString().trim().equals("")) {
                return;
            }
            Map<String, Object> data = new HashMap<>();
            data.put("from", to);
            data.put("to", from);
            data.put("message", chattingEditText.getText().toString());
            data.put("read", false);
            data.put("time", new Date().getTime());
            FirebaseFirestore.getInstance().collection("messages").add(data);
            chattingEditText.setText("");

            sendNotificationToUser(chattingEditText.getText().toString());
        });

        FirebaseFirestore.getInstance().collection("messages")
                .whereIn("from", Arrays.asList(from, to))
                .orderBy("time")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    List<ChattingMessage> messages = new ArrayList<>();

                    assert queryDocumentSnapshots != null;
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        ChattingMessage chattingMessage = new ChattingMessage(
                                Objects.requireNonNull(documentSnapshot.get("from")).toString(),
                                Objects.requireNonNull(documentSnapshot.get("to")).toString(),
                                Objects.requireNonNull(documentSnapshot.get("message")).toString(),
                                (long) documentSnapshot.get("time"),
                                (boolean) documentSnapshot.get("read")
                        );

                        if (chattingMessage.getTo().equals(to) || chattingMessage.getTo().equals(from))
                            messages.add(chattingMessage);
                    }
                    RecyclerView chattingMessagesRecyclerView = findViewById(R.id.chattingMessagesRecyclerView);
                    chattingMessagesRecyclerView.setAdapter(new ChattingAdapter(this, messages, from));
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setStackFromEnd(true);
                    chattingMessagesRecyclerView.setLayoutManager(linearLayoutManager);
                });
    }

    private void sendNotificationToUser(String message) {
        FirebaseFirestore.getInstance().collection("users")
                .get()
                .addOnCompleteListener(task -> {

                    String fcmToken = "";
                    String userName = "";
                    for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        String phone = Objects.requireNonNull(documentSnapshot.get("phone")).toString();

                        if (phone.equals(from)) {
                            fcmToken = Objects.requireNonNull(documentSnapshot.get("fcmToken")).toString();
                            userName = Objects.requireNonNull(documentSnapshot.get("name")).toString();
                            break;
                        }
                    }

                    Log.d(Globals.TAG, "FCM: " + fcmToken);

                    Map<String, Object> moreData = new HashMap<>();

                    moreData.put("type", 3);
                    moreData.put("phone", to);

                    Map<String, Object> data = new HashMap<>();

                    data.put("moredata", moreData);
                    data.put("userName", userName);
                    data.put("message", message);

                    Map<String, Object> body = new HashMap<>();

                    body.put("to", fcmToken);
                    body.put("data", data);
                    body.put("priority", "high");

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Globals.FCM_BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();

                    FcmSendApi fcmSendApi = retrofit.create(FcmSendApi.class);

                    Call<FcmSendResponse> fcmSendResponseCall = fcmSendApi.send(Globals.FCM_AUTHORIZATION, body);

                    fcmSendResponseCall.enqueue(new Callback<FcmSendResponse>() {

                        @EverythingIsNonNull
                        @Override
                        public void onResponse(Call<FcmSendResponse> call, Response<FcmSendResponse> response) {
                            assert response.body() != null;
                            if (response.body().getSuccess() > 0)
                                Log.d(Globals.TAG, "" + response.body().getResults().get(0).getMessageId());
                            else
                                Log.d(Globals.TAG, "" + response.body().getResults().get(0).getError());
                        }

                        @EverythingIsNonNull
                        @Override
                        public void onFailure(Call<FcmSendResponse> call, Throwable t) {
                            Log.e(Globals.TAG, Objects.requireNonNull(t.getMessage()));
                        }
                    });

//                    Observable<FcmSendResponse> fcmSendResponseObservable = fcmSendApi.send(Globals.FCM_AUTHORIZATION, body)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread());
//
//                    Globals.compositeDisposable.add(fcmSendResponseObservable.subscribe(
//                            fcmSendResponse -> {
//                                if (fcmSendResponse.getSuccess() > 0)
//                                    Log.d(Globals.TAG, "" + fcmSendResponse.getResults().get(0).getMessageId());
//                                else
//                                    Log.d(Globals.TAG, "" + fcmSendResponse.getResults().get(0).getError());
//                            },
//                            fcmSendError -> Log.e(Globals.TAG, Objects.requireNonNull(fcmSendError.getMessage()))
//                    ));
                });
    }
}
