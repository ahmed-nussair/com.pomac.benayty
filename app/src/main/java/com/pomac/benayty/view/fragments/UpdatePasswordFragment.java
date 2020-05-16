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
import com.pomac.benayty.model.response.UpdatePasswordResponse;
import com.pomac.benayty.view.interfaces.AppLoginNavigator;

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
public class UpdatePasswordFragment extends Fragment {

    private EditText updatePasswordEditText;
    private EditText confirmPasswordEditText;
    private TextView changePasswordButtonTextView;

    private AppLoginNavigator navigator;

    private String resetCode;

    public UpdatePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_password, container, false);
        updatePasswordEditText = view.findViewById(R.id.updatePasswordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        changePasswordButtonTextView = view.findViewById(R.id.changePasswordButtonTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;

        resetCode = getArguments().getString(Globals.RESET_CODE);

        assert getActivity() != null;
        navigator = (AppLoginNavigator) getActivity();

        changePasswordButtonTextView.setOnClickListener(v -> {
            if (updatePasswordEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), "من فضلك أدخل كلمة المرور الجديدة", Toast.LENGTH_LONG).show();
                return;
            } else if (confirmPasswordEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), "من فضلك أعد إدخال كلمة المرور الجديدة", Toast.LENGTH_LONG).show();
                return;
            }

            String password = updatePasswordEditText.getText().toString();
            String passwordConfirm = confirmPasswordEditText.getText().toString();

            if (!passwordConfirm.equals(password)) {
                Toast.makeText(getContext(), "عفواً .. كلمة المرور لم تطابق كلمة المرور التي أدخلت", Toast.LENGTH_LONG).show();
                return;
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            ForgotPasswordApi forgotPasswordApi = retrofit.create(ForgotPasswordApi.class);

            Observable<UpdatePasswordResponse> observable = forgotPasswordApi.updatePassword(resetCode, password, passwordConfirm)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            Disposable disposable = observable.subscribe(
                    response -> {
                        Toast.makeText(getContext(), "لقد تم تغيير كلمة المرور .. يمكنك تسجيل الدخول", Toast.LENGTH_LONG).show();
                        navigator.navigateToLoginScreen();
                    }
            );

            Globals.compositeDisposable.add(disposable);

        });
    }
}
