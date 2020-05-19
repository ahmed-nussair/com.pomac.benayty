package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.MyAdsAdapter;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnAdItemSelected;
import com.pomac.benayty.viewmodel.MyAdsViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdsFragment extends Fragment implements OnAdItemSelected {

    private RecyclerView myAdsRecyclerView;
    private ProgressBar myAdsProgressBar;

    private AppNavigator navigator;

    public MyAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_ads, container, false);
        myAdsRecyclerView = view.findViewById(R.id.myAdsRecyclerView);
        myAdsProgressBar = view.findViewById(R.id.myAdsProgressBar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        navigator = (AppNavigator) getActivity();

        MyAdsViewModel myAdsViewModel = ViewModelProviders.of(this).get(MyAdsViewModel.class);

        myAdsViewModel.getMyAds(Globals.token).observe(getActivity(), response -> {
            MyAdsAdapter adapter = new MyAdsAdapter(getActivity(), response.getData(), this);
            myAdsRecyclerView.setAdapter(adapter);
            myAdsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myAdsProgressBar.setVisibility(View.GONE);
            myAdsRecyclerView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onItemSelected(int adId, String adName) {
        navigator.navigateToAdDetails(adId, adName);
    }
}
