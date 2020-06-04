package com.pomac.benayty.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.apis.ForgotPasswordApi;
import com.pomac.benayty.model.response.ForgotPasswordResponse;
import com.pomac.benayty.view.dialogs.CheckingCodeDialog;
import com.pomac.benayty.view.interfaces.AppLoginNavigator;

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
public class ForgotPasswordFragment extends Fragment {

    private AppLoginNavigator navigator;

    private EditText emailEditText;
    private TextView sendForPasswordButtonTextView;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        sendForPasswordButtonTextView = view.findViewById(R.id.sendForPasswordButtonTextView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        navigator = (AppLoginNavigator) getActivity();

//        navigator.navigateToForgotPasswordScreen();

        sendForPasswordButtonTextView.setOnClickListener(v -> {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (emailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل البريد الإليكتروني", Toast.LENGTH_LONG).show();
                return;
            } else if (!emailEditText.getText().toString().trim().matches(emailPattern)) {
                Toast.makeText(getActivity(), "البريد الإلكتروني غير صالح .. من فضلك أدخل بريد إلكتروني صالح", Toast.LENGTH_LONG).show();
                return;
            }

            String email = emailEditText.getText().toString();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            ForgotPasswordApi forgotPasswordApi = retrofit.create(ForgotPasswordApi.class);

            Call<ForgotPasswordResponse> forgotPasswordResponseCall = forgotPasswordApi.sendForPasswordRecovery(email);

            forgotPasswordResponseCall.enqueue(new Callback<ForgotPasswordResponse>() {

                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                    assert response.body() != null;
                    assert getActivity() != null;
                    if (response.body().getStatus() == 200) {
                        CheckingCodeDialog dialog =
                                new CheckingCodeDialog(getActivity(),
                                        android.R.style.Theme_Translucent_NoTitleBar, ForgotPasswordFragment.this, navigator, email);
                        dialog.setCancelable(false);
                        dialog.show();
                    } else {
                        Toast.makeText(getActivity(), "عفوًا .. البريد الإلكتروني الذي أدخلته غير مسجل", Toast.LENGTH_LONG).show();
                    }
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

                }
            });

//            Observable<ForgotPasswordResponse> observable = forgotPasswordApi.sendForPasswordRecovery(email)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//
//            Disposable disposable = observable.subscribe(
//                    response -> {
//                        if (response.getStatus() == 200) {
//                            CheckingCodeDialog dialog =
//                                    new CheckingCodeDialog(getActivity(),
//                                            android.R.style.Theme_Translucent_NoTitleBar, this, navigator, email);
//                            dialog.setCancelable(false);
//                            dialog.show();
//                        } else {
//                            Toast.makeText(getActivity(), "عفوًا .. البريد الإلكتروني الذي أدخلته غير مسجل", Toast.LENGTH_LONG).show();
//                        }
//
//                    },
//                    error -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show()
//            );
//
//            Globals.compositeDisposable.add(disposable);

        });
    }
}
