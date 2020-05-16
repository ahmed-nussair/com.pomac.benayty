package com.pomac.benayty.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.apis.ForgotPasswordApi;
import com.pomac.benayty.model.response.CodeCheckingResponse;
import com.pomac.benayty.model.response.ForgotPasswordResponse;
import com.pomac.benayty.view.interfaces.AppLoginNavigator;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckingCodeDialog extends Dialog {

    private TextView checkingCodeDialogTitle;
    private EditText codeEditText;
    private TextView checkCodeButtonTextView;
    private TextView wrongCodeTextView;
    private TextView noCodeEnteredTextView;
    private TextView checkCodeCancelingButtonTextView;
    private TextView resendCodeButtonTextView;

    private Fragment fragment;
    private AppLoginNavigator navigator;
    private String email;

    public CheckingCodeDialog(@NonNull Context context, int themeResId,
                              Fragment fragment, AppLoginNavigator navigator,
                              String email) {
        super(context, themeResId);
        this.fragment = fragment;
        this.navigator = navigator;
        this.email = email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorDrawable background = new ColorDrawable(Color.BLACK);
        background.setAlpha(100);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(background);
        setContentView(R.layout.checking_code_dialog_layout);

        prepareTheViews();

        prepareTheListeners();
    }

    private void prepareTheListeners() {

        codeEditText.setOnClickListener(v -> {
            noCodeEnteredTextView.setVisibility(View.GONE);
            wrongCodeTextView.setVisibility(View.GONE);
        });


        checkCodeButtonTextView.setOnClickListener(v -> {
            if (codeEditText.getText().toString().trim().equals("")) {
                noCodeEnteredTextView.setVisibility(View.VISIBLE);
                return;
            }


            String code = codeEditText.getText().toString();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            ForgotPasswordApi forgotPasswordApi = retrofit.create(ForgotPasswordApi.class);


            Observable<CodeCheckingResponse> observable = forgotPasswordApi.checkCode(code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            Disposable disposable = observable.subscribe(
                    response -> {
                        noCodeEnteredTextView.setVisibility(View.GONE);
                        if (response.getMessage() == null) {

                            wrongCodeTextView.setVisibility(View.VISIBLE);
                            return;
                        }
                        wrongCodeTextView.setVisibility(View.GONE);
                        dismiss();
                        navigator.navigateToUpdatePasswordScreen(code);
                    }
            );

            Globals.compositeDisposable.add(disposable);


        });


        checkCodeCancelingButtonTextView.setOnClickListener(v -> dismiss());

        resendCodeButtonTextView.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            ForgotPasswordApi forgotPasswordApi = retrofit.create(ForgotPasswordApi.class);

            Observable<ForgotPasswordResponse> observable = forgotPasswordApi.sendForPasswordRecovery(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            Disposable disposable = observable.subscribe(
                    response -> {
                        Toast.makeText(fragment.getContext(), "تم إرسال كود جديد على بريدك الإلكتروني", Toast.LENGTH_LONG).show();

                    }
            );

            Globals.compositeDisposable.add(disposable);
        });
    }

    private void prepareTheViews() {
        checkingCodeDialogTitle = findViewById(R.id.checkingCodeDialogTitle);
        codeEditText = findViewById(R.id.codeEditText);
        checkCodeButtonTextView = findViewById(R.id.checkCodeButtonTextView);
        wrongCodeTextView = findViewById(R.id.wrongCodeTextView);
        noCodeEnteredTextView = findViewById(R.id.noCodeEnteredTextView);
        resendCodeButtonTextView = findViewById(R.id.resendCodeButtonTextView);
        checkCodeCancelingButtonTextView =
                findViewById(R.id.checkCodeCancelingButtonTextView);
    }
}
