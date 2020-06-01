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
import android.widget.TextView;
import android.widget.Toast;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.MyAdsAdapter;
import com.pomac.benayty.apis.DeleteMyAdApi;
import com.pomac.benayty.model.response.DeleteMyAdResponse;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.view.interfaces.OnAdItemSelected;
import com.pomac.benayty.view.interfaces.OnItemDeleted;
import com.pomac.benayty.viewmodel.MyAdsViewModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdsFragment extends Fragment implements OnAdItemSelected, OnItemDeleted {

    private RecyclerView myAdsRecyclerView;
    private ProgressBar myAdsProgressBar;
    private TextView myAdsLoginFirstTextView;
    private TextView noAdForYourTextView;

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
        myAdsLoginFirstTextView = view.findViewById(R.id.myAdsLoginFirstTextView);
        noAdForYourTextView = view.findViewById(R.id.noAdForYourTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        navigator = (AppNavigator) getActivity();

        if (Globals.token.isEmpty()) {
            myAdsProgressBar.setVisibility(View.GONE);
            myAdsLoginFirstTextView.setVisibility(View.VISIBLE);
            return;
        }

        MyAdsViewModel myAdsViewModel = ViewModelProviders.of(this).get(MyAdsViewModel.class);

        myAdsViewModel.getMyAds(Globals.token).observe(getActivity(), response -> {
            if (response.getData().size() <= 0) {
                myAdsProgressBar.setVisibility(View.GONE);
                noAdForYourTextView.setVisibility(View.VISIBLE);
                return;
            }
            MyAdsAdapter adapter = new MyAdsAdapter(getActivity(), response.getData(), this, this);
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

    @Override
    public void onItemDeleted(int adId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        DeleteMyAdApi deleteMyAdApi = retrofit.create(DeleteMyAdApi.class);

        Observable<DeleteMyAdResponse> deleteMyAdResponseObservable =
                deleteMyAdApi.deleteAd(
                        Globals.token,
                        adId
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        Globals.compositeDisposable.add(deleteMyAdResponseObservable.subscribe(
                deleteMyAdResponse -> {
                    if (deleteMyAdResponse.getStatus() == 200) {
                        Toast.makeText(getContext(), deleteMyAdResponse.getMessage(), Toast.LENGTH_LONG).show();
                        navigator.navigateToMyAdsPage();
                    } else {
                        Toast.makeText(getContext(), deleteMyAdResponse.getErrors()[0], Toast.LENGTH_LONG).show();
                    }
                },
                deleteError -> {
                    Toast.makeText(getContext(), deleteError.getMessage(), Toast.LENGTH_LONG).show();
                }
        ));
    }
}
