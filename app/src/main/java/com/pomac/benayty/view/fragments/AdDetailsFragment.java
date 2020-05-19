package com.pomac.benayty.view.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.apis.AddToWishListApi;
import com.pomac.benayty.model.response.AddToWishListResponse;
import com.pomac.benayty.view.interfaces.AppNavigator;
import com.pomac.benayty.viewmodel.AdViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdDetailsFragment extends Fragment {

    private TextView adDetailsErrorConnectionTextView;
    private TextView adDescriptionTextView;
    private TextView adDetailsUserNameTextView;
    private TextView adAreaTextView;
    private TextView adDetailsPhoneTextView;
    private ProgressBar adDetailsProgressBar;
    private ScrollView adContentScrollView;
    private RoundedImageView adDetailsImageView;
    private FrameLayout messageFrameLayout;
    private FrameLayout commentFrameLayout;
    private FrameLayout addToWishListFrameLayout;

    private AppNavigator navigator;

    private int adId;

    public AdDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ad_details, container, false);
        adDetailsErrorConnectionTextView = view.findViewById(R.id.adDetailsErrorConnectionTextView);
        adDescriptionTextView = view.findViewById(R.id.adDescriptionTextView);
        adDetailsUserNameTextView = view.findViewById(R.id.adDetailsUserNameTextView);
        adAreaTextView = view.findViewById(R.id.adAreaTextView);
        adDetailsPhoneTextView = view.findViewById(R.id.adDetailsPhoneTextView);
        adDetailsProgressBar = view.findViewById(R.id.adDetailsProgressBar);
        adContentScrollView = view.findViewById(R.id.adContentScrollView);
        adDetailsImageView = view.findViewById(R.id.adDetailsImageView);
        addToWishListFrameLayout = view.findViewById(R.id.addToWishListFrameLayout);
        commentFrameLayout = view.findViewById(R.id.commentFrameLayout);
        messageFrameLayout = view.findViewById(R.id.messageFrameLayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;

        adId = getArguments().getInt(Globals.AD_ID);

        assert getActivity() != null;

        navigator = (AppNavigator) getActivity();

        AdViewModel adViewModel = ViewModelProviders.of(this).get(AdViewModel.class);

        adViewModel.showAdvertisement(adId).observe(
                getActivity(),
                response -> {
                    adDetailsUserNameTextView.setText(response.getData().getUser().getName());
                    adAreaTextView.setText(response.getData().getArea());
                    adDescriptionTextView.setText(response.getData().getDescription());
                    adDetailsPhoneTextView.setText(response.getData().getPhone());
                    Transformation transformation = new RoundedTransformationBuilder()
                            .cornerRadiusDp(10)
                            .oval(false)
                            .build();
                    Picasso.get()
                            .load(response.getData().getImagePath())
                            .fit()
                            .transform(transformation)
                            .into(adDetailsImageView);

                    adDetailsProgressBar.setVisibility(View.GONE);
                    adContentScrollView.setVisibility(View.VISIBLE);
                }
        );

        addToWishListFrameLayout.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            AddToWishListApi api = retrofit.create(AddToWishListApi.class);

            Observable<AddToWishListResponse> observable = api.addToWishList(Globals.token, adId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            Disposable disposable = observable.subscribe(
                    response -> Toast.makeText(
                            getActivity(),
                            response.getMessage() != null ?
                                    response.getMessage()
                                    : response.getErrors()[0],
                            Toast.LENGTH_LONG
                    ).show(),
                    error -> Toast.makeText(getActivity(), Objects.requireNonNull(error.getMessage()), Toast.LENGTH_LONG).show()
            );

            Globals.compositeDisposable.add(disposable);
        });
    }
}
