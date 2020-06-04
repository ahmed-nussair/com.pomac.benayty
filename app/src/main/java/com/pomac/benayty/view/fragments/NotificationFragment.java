package com.pomac.benayty.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.NotificationsAdapter;
import com.pomac.benayty.view.activities.ChattingActivity;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnNotificationsItemSelected;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements OnNotificationsItemSelected {

    private AppNavigator navigator;

    private ProgressBar notificationsProgressBar;
    private RecyclerView notificationsRecyclerView;
    private TextView noNotificationsTextView;
    private TextView notificationsLoginFirstTextView;

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
        notificationsLoginFirstTextView = view.findViewById(R.id.notificationsLoginFirstTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();

        if (Globals.token.isEmpty()) {
            notificationsProgressBar.setVisibility(View.GONE);
            notificationsLoginFirstTextView.setVisibility(View.VISIBLE);
            return;
        }

        if (Globals.notificationsData.size() <= 0) {
            notificationsProgressBar.setVisibility(View.GONE);
            noNotificationsTextView.setVisibility(View.VISIBLE);
            return;
        }
        NotificationsAdapter adapter = new NotificationsAdapter(getContext(), Globals.notificationsData, this);
        notificationsRecyclerView.setAdapter(adapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationsProgressBar.setVisibility(View.GONE);
        notificationsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNotificationsItemSelected(Map<String, String> notificationsItem) {
        Log.d(Globals.TAG, notificationsItem.toString());

        String moreDataString = notificationsItem.get("moredata");

        assert moreDataString != null;

        try {
            JSONObject moreData = new JSONObject(moreDataString);

            int type = Integer.parseInt(moreData.get("type").toString());

            switch (type) {
                case 1:
                    int adId = Integer.parseInt(moreData.get("advertisement_id").toString());

                    navigator.navigateToAdDetails(adId, "");
                    break;
                case 3:
                    String userName = notificationsItem.get("userName");
                    String from = moreData.getString("phone");

                    assert getActivity() != null;
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);

                    String to = sharedPreferences.getString(Globals.USER_PHONE, "");

                    Intent intent = new Intent(getActivity(), ChattingActivity.class);
                    intent.putExtra(Globals.USER_NAME, userName);
                    intent.putExtra(Globals.FROM, from);
                    intent.putExtra(Globals.TO, to);

                    startActivity(intent);

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
