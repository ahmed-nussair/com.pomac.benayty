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
import com.pomac.benayty.ChattingMessage;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.ChattingAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChattingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ((TextView) findViewById(R.id.chattingPageTitle)).setText(getIntent().getStringExtra(Globals.USER_NAME));
        ((ImageView) findViewById(R.id.backToMain)).setOnClickListener(v -> finish());


        String from = getIntent().getStringExtra(Globals.FROM);
        String to = getIntent().getStringExtra(Globals.TO);

        Log.d(Globals.TAG, "From: " + from);
        Log.d(Globals.TAG, "To: " + to);


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
                    chattingMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                });
    }
}
