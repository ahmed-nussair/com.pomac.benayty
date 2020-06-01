package com.pomac.benayty.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pomac.benayty.Globals;
import com.pomac.benayty.MessageItem;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.MessagesAdapter;
import com.pomac.benayty.view.activities.ChattingActivity;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnMessageItemSelected;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements OnMessageItemSelected {

    private AppNavigator navigator;

    private RecyclerView messagesRecyclerView;
    private TextView noMessageForYouTextView;
    private TextView messagesLoginFirstTextView;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        noMessageForYouTextView = view.findViewById(R.id.noMessageForYouTextView);
        messagesLoginFirstTextView = view.findViewById(R.id.messagesLoginFirstTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        navigator = (AppNavigator) getActivity();

        if (Globals.token.isEmpty()) {
            messagesLoginFirstTextView.setVisibility(View.VISIBLE);
            messagesRecyclerView.setVisibility(View.GONE);
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("messages")
                .whereEqualTo("read", false)
                .whereEqualTo("to", Globals.phone)
                .orderBy("time")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        if (Objects.requireNonNull(task.getResult()).size() <= 0) {
                            noMessageForYouTextView.setVisibility(View.VISIBLE);
                            messagesRecyclerView.setVisibility(View.GONE);
                            return;
                        }

                        List<MessageItem> messageItems = new ArrayList<>();

                        for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {

                            String phone = Objects.requireNonNull(documentSnapshot.get("from")).toString();

                            FirebaseFirestore.getInstance()
                                    .collection("users")
                                    .get()
                                    .addOnCompleteListener(task1 -> {
                                        for (DocumentSnapshot documentSnapshot1 : Objects.requireNonNull(task1.getResult())) {

                                            String userPhone = Objects.requireNonNull(documentSnapshot1.get("phone")).toString();

                                            if (userPhone.equals(phone)) {

                                                MessageItem messageItem = new MessageItem(
                                                        Objects.requireNonNull(documentSnapshot.get("message")).toString(),
                                                        Objects.requireNonNull(documentSnapshot1.get("imagePath")).toString(),
                                                        Objects.requireNonNull(documentSnapshot1.get("name")).toString(),
                                                        userPhone
                                                );

                                                if (!messageItem.getUserPhone().equals(Globals.phone)) {
                                                    messageItems.add(messageItem);
                                                }
                                            }
                                        }

                                        MessagesAdapter adapter = new MessagesAdapter(getActivity(), messageItems, this);
                                        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                                            @Override
                                            public void onChanged() {
                                                super.onChanged();
                                            }
                                        });
                                        messagesRecyclerView.setAdapter(adapter);
                                        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    });
                        }


                    } else {
                        Log.e(Globals.TAG, "Error", task.getException());
                    }
                });
    }

    @Override
    public void onMessageItemSelected(String userName, String from, String to) {
//        navigator.navigateToChattingPage(userName);
        Intent intent = new Intent(getActivity(), ChattingActivity.class);
        intent.putExtra(Globals.USER_NAME, userName);
        intent.putExtra(Globals.FROM, from);
        intent.putExtra(Globals.TO, to);
        startActivity(intent);
    }
}
