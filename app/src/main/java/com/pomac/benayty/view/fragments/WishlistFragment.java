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
import com.pomac.benayty.adapters.WishListAdapter;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.viewmodel.WishListViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {

    private AppNavigator navigator;

    private RecyclerView wishListRecyclerView;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        wishListRecyclerView = view.findViewById(R.id.wishListRecyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();
        navigator.setTitle(getResources().getString(R.string.wish_list_page_title));

        WishListViewModel viewModel = ViewModelProviders.of(this).get(WishListViewModel.class);

        viewModel.getWishList(Globals.token).observe(getActivity(), response -> {
            WishListAdapter adapter = new WishListAdapter(getContext(), response.getData());
            wishListRecyclerView.setAdapter(adapter);
            wishListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }
}
