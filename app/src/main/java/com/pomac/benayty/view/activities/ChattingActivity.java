package com.pomac.benayty.view.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.ChattingAdapter;

import java.util.Arrays;

public class ChattingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ((TextView) findViewById(R.id.chattingPageTitle)).setText(getIntent().getStringExtra(Globals.USER_NAME));
        ((ImageView) findViewById(R.id.backToMain)).setOnClickListener(v -> finish());

        String from = getIntent().getStringExtra(Globals.FROM);
        String to = getIntent().getStringExtra(Globals.TO);

        // chattingMessagesRecyclerView
        FirebaseFirestore.getInstance().collection("messages")
//                .whereIn("from", Arrays.asList(from, to))
                .whereEqualTo("from", from)
                .whereEqualTo("to", to)
                .orderBy("time")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    RecyclerView chattingMessagesRecyclerView = findViewById(R.id.chattingMessagesRecyclerView);
                    chattingMessagesRecyclerView.setAdapter(new ChattingAdapter(this, queryDocumentSnapshots, from));
                    chattingMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                });
    }
}
