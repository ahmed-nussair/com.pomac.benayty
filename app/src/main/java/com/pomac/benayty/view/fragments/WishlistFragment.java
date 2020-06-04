package com.pomac.benayty.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.WishListAdapter;
import com.pomac.benayty.apis.DeleteFromWishListApi;
import com.pomac.benayty.model.response.DeleteFromWishListResponse;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnAdItemSelected;
import com.pomac.benayty.view.interfaces.OnItemDeleted;
import com.pomac.benayty.viewmodel.WishListViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements OnAdItemSelected, OnItemDeleted {

    private AppNavigator navigator;

    private RecyclerView wishListRecyclerView;
    private ProgressBar wishListProgressBar;
    private TextView wishlistLoginFirstTextView;
    private TextView noWishListItemTextView;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        wishListRecyclerView = view.findViewById(R.id.wishListRecyclerView);
        wishListProgressBar = view.findViewById(R.id.wishListProgressBar);
        wishlistLoginFirstTextView = view.findViewById(R.id.wishlistLoginFirstTextView);
        noWishListItemTextView = view.findViewById(R.id.noWishListItemTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;
        navigator = (AppNavigator) getActivity();

        if (Globals.token.isEmpty()) {
            wishListProgressBar.setVisibility(View.GONE);
            wishlistLoginFirstTextView.setVisibility(View.VISIBLE);
        } else {
            WishListViewModel viewModel = ViewModelProviders.of(this).get(WishListViewModel.class);

            viewModel.getWishList(Globals.token).observe(getActivity(), response -> {

                if (response.getData().size() <= 0) {
                    wishListProgressBar.setVisibility(View.GONE);
                    noWishListItemTextView.setVisibility(View.VISIBLE);
                    return;
                }
                WishListAdapter adapter = new WishListAdapter(getContext(), response.getData(), this, this);
                wishListRecyclerView.setAdapter(adapter);
                wishListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                wishListProgressBar.setVisibility(View.GONE);
                wishListRecyclerView.setVisibility(View.VISIBLE);
            });
        }


    }

    @Override
    public void onItemSelected(int adId, String adName) {
        navigator.navigateToAdDetails(adId, adName);
    }

    @Override
    public void onItemDeleted(int adId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        DeleteFromWishListApi deleteFromWishListApi = retrofit.create(DeleteFromWishListApi.class);

        Call<DeleteFromWishListResponse> deleteFromWishListResponseCall = deleteFromWishListApi.deleteFromWishList(Globals.token, adId);

        deleteFromWishListResponseCall.enqueue(new Callback<DeleteFromWishListResponse>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<DeleteFromWishListResponse> call, Response<DeleteFromWishListResponse> response) {
                assert response.body() != null;
                if (response.body().getStatus() == 200) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                    navigator.navigateToWishListPage();
                } else {
                    Toast.makeText(getContext(), response.body().getErrors()[0], Toast.LENGTH_LONG).show();
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<DeleteFromWishListResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        Observable<DeleteFromWishListResponse> deleteFromWishListResponseObservable =
//                deleteFromWishListApi.deleteFromWishList(
//                        Globals.token,
//                        adId
//                )
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//
//        Globals.compositeDisposable.add(deleteFromWishListResponseObservable.subscribe(
//                deleteFromWishListResponse -> {
//                    if (deleteFromWishListResponse.getStatus() == 200) {
//                        Toast.makeText(getContext(), deleteFromWishListResponse.getMessage(), Toast.LENGTH_LONG).show();
//
//                        navigator.navigateToWishListPage();
//                    } else {
//                        Toast.makeText(getContext(), deleteFromWishListResponse.getErrors()[0], Toast.LENGTH_LONG).show();
//                    }
//                },
//                deleteError -> Toast.makeText(getContext(), deleteError.getMessage(), Toast.LENGTH_LONG).show()
//        ));
    }
}
