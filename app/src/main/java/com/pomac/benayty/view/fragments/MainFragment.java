package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.MainCategoriesAdapter;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnMainCategorySelected;
import com.pomac.benayty.viewmodel.MainCategoriesViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements OnMainCategorySelected {

    private RecyclerView mainCategoriesRecyclerView;
    private ProgressBar progressBar;
    private AppNavigator navigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mainCategoriesRecyclerView = view.findViewById(R.id.mainCategoriesRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        navigator = (AppNavigator) getActivity();

        Animation progressBarAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.progress);
        progressBarAnimation.setDuration(1000);
        progressBar.startAnimation(progressBarAnimation);
        MainCategoriesViewModel viewModel = ViewModelProviders.of(this).get(MainCategoriesViewModel.class);

        viewModel.getMainCategoriesResponse().observe(getActivity(), response -> {
            if(response != null) {
                MainCategoriesAdapter adapter = new MainCategoriesAdapter(getActivity(), response.getData(), this);
                mainCategoriesRecyclerView.setAdapter(adapter);
                mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                adapter.notifyDataSetChanged();
                mainCategoriesRecyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                Log.e(Globals.TAG, "Error");
                TextView errorTextView = getActivity().findViewById(R.id.errorTextView);
                errorTextView.setVisibility(View.VISIBLE);
                mainCategoriesRecyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onMainCategorySelected(int mainCategoryId, String mainCategoryName) {

        navigator.navigateToMainCategoryPage(mainCategoryId, mainCategoryName);
    }
}
