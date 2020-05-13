package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pomac.benayty.R;
import com.pomac.benayty.view.interfaces.AppNavigator;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {

    private AppNavigator navigator;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();
        navigator.setTitle(getResources().getString(R.string.wish_list_page_title));
    }
}
