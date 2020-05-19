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
import com.pomac.benayty.adapters.WishListAdapter;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnAdItemSelected;
import com.pomac.benayty.viewmodel.WishListViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements OnAdItemSelected {

    private AppNavigator navigator;

    private RecyclerView wishListRecyclerView;
    private ProgressBar wishListProgressBar;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        wishListRecyclerView = view.findViewById(R.id.wishListRecyclerView);
        wishListProgressBar = view.findViewById(R.id.wishListProgressBar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();

        WishListViewModel viewModel = ViewModelProviders.of(this).get(WishListViewModel.class);

        viewModel.getWishList(Globals.token).observe(getActivity(), response -> {
            WishListAdapter adapter = new WishListAdapter(getContext(), response.getData(), this);
            wishListRecyclerView.setAdapter(adapter);
            wishListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            wishListProgressBar.setVisibility(View.GONE);
            wishListRecyclerView.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onItemSelected(int adId, String adName) {
        navigator.navigateToAdDetails(adId, adName);
    }
}
