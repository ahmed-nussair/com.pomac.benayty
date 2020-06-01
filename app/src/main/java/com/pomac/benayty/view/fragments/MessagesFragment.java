package com.pomac.benayty.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.MessagesAdapter;
import com.pomac.benayty.view.activities.ChattingActivity;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnMessageItemSelected;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements OnMessageItemSelected {

    private AppNavigator navigator;

    private RecyclerView messagesRecyclerView;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        navigator = (AppNavigator) getActivity();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("messages")
                .whereEqualTo("read", false)
                .orderBy("time")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        QuerySnapshot result = task.getResult();

                        MessagesAdapter adapter = new MessagesAdapter(getActivity(), task.getResult(), this);
                        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                            @Override
                            public void onChanged() {
                                super.onChanged();
                            }
                        });
                        messagesRecyclerView.setAdapter(adapter);
                        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        for (QueryDocumentSnapshot document : result) {
                            Log.d(Globals.TAG, document.getId() + " => " + document.getData());
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
