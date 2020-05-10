package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.pomac.benayty.R;
import com.pomac.benayty.adapters.MainCategoriesAdapter;
import com.pomac.benayty.viewmodel.MainCategoriesViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView mainCategoriesRecyclerView;
    private ProgressBar progressBar;

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

        Animation progressBarAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.progress);
        progressBarAnimation.setDuration(1000);
        progressBar.startAnimation(progressBarAnimation);
        MainCategoriesViewModel viewModel = ViewModelProviders.of(this).get(MainCategoriesViewModel.class);

        viewModel.getMainCategoriesResponse().observe(getActivity(), response -> {
            MainCategoriesAdapter adapter = new MainCategoriesAdapter(getActivity(), response.getData());
            mainCategoriesRecyclerView.setAdapter(adapter);
            mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
            adapter.notifyDataSetChanged();
            mainCategoriesRecyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        });
    }
}
