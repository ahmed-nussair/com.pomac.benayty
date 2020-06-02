package com.pomac.benayty.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.apis.LoginApi;
import com.pomac.benayty.model.response.LoginResponse;
import com.pomac.benayty.view.interfaces.AppLoginNavigator;

import java.util.HashMap;
import java.util.Map;
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
public class LoginFragment extends Fragment {

    private EditText loginPasswordEditText;
    private EditText loginPhoneEditText;
    private TextView loginButtonTextView;
    private TextView forgotPasswordLink;
    private TextView registerLink;

    private AppLoginNavigator navigator;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginPasswordEditText = view.findViewById(R.id.loginPasswordEditText);
        loginPhoneEditText = view.findViewById(R.id.loginPhoneEditText);
        loginButtonTextView = view.findViewById(R.id.loginButtonTextView);
        forgotPasswordLink = view.findViewById(R.id.forgotPasswordLink);
        registerLink = view.findViewById(R.id.registerLink);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        navigator = (AppLoginNavigator) getActivity();

//        navigator.navigateToLoginScreen();
        forgotPasswordLink.setOnClickListener(v -> navigator.navigateToForgotPasswordScreen());
        registerLink.setOnClickListener(v -> navigator.navigateToRegistrationScreen());

        loginButtonTextView.setOnClickListener(v -> {
            if (loginPhoneEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل رقم الجوال", Toast.LENGTH_LONG).show();
                return;
            } else if (loginPasswordEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل كلمة المرور", Toast.LENGTH_LONG).show();
                return;
            }

            String phone = loginPhoneEditText.getText().toString();
            String password = loginPasswordEditText.getText().toString();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            LoginApi api = retrofit.create(LoginApi.class);

            Observable<LoginResponse> observable = api.login(phone, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            Disposable disposable = observable.subscribe(
                    response -> {
                        if (response.getStatus() == 200) {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Globals.USER_TOKEN, response.getToken());
                            editor.putString(Globals.USER_NAME, response.getUserData().getName());
                            editor.putString(Globals.USER_PHONE, response.getUserData().getPhone());
                            editor.putString(Globals.USER_IMAGE_PATH, response.getUserData().getImagePath());
                            if (editor.commit()) {
                                Toast.makeText(getContext(), "تم تسجيل الدخول", Toast.LENGTH_LONG).show();
                                Globals.token = sharedPreferences.getString(Globals.USER_TOKEN, "");
                                Globals.phone = sharedPreferences.getString(Globals.USER_PHONE, "");

                                FirebaseFirestore.getInstance()
                                        .collection("users")
                                        .whereEqualTo("phone", response.getUserData().getPhone())
                                        .get()
                                        .addOnCompleteListener(task -> {
                                            if (Objects.requireNonNull(task.getResult()).size() > 0) {
                                                Map<String, Object> newData = new HashMap<>();

                                                newData.put("fcmToken", Globals.fcmToken);
                                                FirebaseFirestore.getInstance().collection("users")
                                                        .document(Objects.requireNonNull(task.getResult()).getDocuments().get(0).getId())
                                                        .update(newData);
                                            }

                                        });
                                getActivity().finish();
                            }
                        } else {
                            Toast.makeText(getContext(), response.getErrors()[0], Toast.LENGTH_LONG).show();
                        }


                    },
                    loginError -> Toast.makeText(getContext(), loginError.getMessage(), Toast.LENGTH_LONG).show()
            );

            Globals.compositeDisposable.add(disposable);
        });
    }
}
