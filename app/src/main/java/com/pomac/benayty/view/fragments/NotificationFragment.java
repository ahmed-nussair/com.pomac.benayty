package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.NotificationsAdapter;
import com.pomac.benayty.view.interfaces.AppNavigator;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private AppNavigator navigator;

    private ProgressBar notificationsProgressBar;
    private RecyclerView notificationsRecyclerView;
    private TextView noNotificationsTextView;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationsProgressBar = view.findViewById(R.id.notificationsProgressBar);
        notificationsRecyclerView = view.findViewById(R.id.notificationsRecyclerView);
        noNotificationsTextView = view.findViewById(R.id.noNotificationsTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();

        if (Globals.notificationsData.size() <= 0) {
            notificationsProgressBar.setVisibility(View.GONE);
            noNotificationsTextView.setVisibility(View.VISIBLE);
            return;
        }
        NotificationsAdapter adapter = new NotificationsAdapter(getContext(), Globals.notificationsData);
        notificationsRecyclerView.setAdapter(adapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsProgressBar.setVisibility(View.GONE);
        notificationsRecyclerView.setVisibility(View.VISIBLE);
    }
}
