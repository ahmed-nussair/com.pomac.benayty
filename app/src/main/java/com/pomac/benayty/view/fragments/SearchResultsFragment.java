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

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.AdsAdapter;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnAdItemSelected;
import com.pomac.benayty.viewmodel.AdsViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultsFragment extends Fragment implements OnAdItemSelected {

    private int mainCategoryId;
    private int secondaryCategoryId;
    private int areaId;
    private int cityId;

    private AppNavigator navigator;

    private RecyclerView searchResultsRecyclerView;
    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        searchResultsRecyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;

        mainCategoryId = getArguments().getInt(Globals.MAIN_CATEGORY_ID);
        secondaryCategoryId = getArguments().getInt(Globals.SECONDARY_CATEGORY_ID);
        areaId = getArguments().getInt(Globals.AREA_ID);
        cityId = getArguments().getInt(Globals.CITY_ID);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();

        AdsViewModel adsViewModel = ViewModelProviders.of(this).get(AdsViewModel.class);

        adsViewModel.getAdsList(mainCategoryId, secondaryCategoryId, areaId, cityId).observe(
                getActivity(), response -> {
                    AdsAdapter adapter = new AdsAdapter(getActivity(), response.getData(), this);
                    searchResultsRecyclerView.setAdapter(adapter);
                    searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
        );
    }

    @Override
    public void onItemSelected(int adId, String adName) {
        Bundle bundle = new Bundle();
        bundle.putInt(Globals.MAIN_CATEGORY_ID, mainCategoryId);
        bundle.putInt(Globals.SECONDARY_CATEGORY_ID, secondaryCategoryId);
        bundle.putInt(Globals.AREA_ID, areaId);
        bundle.putInt(Globals.CITY_ID, cityId);
        navigator.navigateToAdDetails(adId, adName, bundle);
    }
}
