package com.pomac.benayty.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.pomac.benayty.apis.FcmTokenUpdateApi;
import com.pomac.benayty.apis.RegisterApi;
import com.pomac.benayty.model.response.FcmTokenUpdateResponse;
import com.pomac.benayty.model.response.RegisterResponse;
import com.pomac.benayty.view.interfaces.AppLoginNavigator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public class RegistrationFragment extends Fragment {

    private AppLoginNavigator navigator;

    private EditText signUpNameEditText;
    private EditText signUpEmailEditText;
    private EditText signUpPhoneEditText;
    private EditText signUpPasswordEditText;
    private TextView signUpButtonTextView;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        signUpNameEditText = view.findViewById(R.id.signUpNameEditText);
        signUpEmailEditText = view.findViewById(R.id.signUpEmailEditText);
        signUpPhoneEditText = view.findViewById(R.id.signUpPhoneEditText);
        signUpPasswordEditText = view.findViewById(R.id.signUpPasswordEditText);
        signUpButtonTextView = view.findViewById(R.id.signUpButtonTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        navigator = (AppLoginNavigator) getActivity();

//        navigator.navigateToRegistrationScreen();

        signUpButtonTextView.setOnClickListener(v -> {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (signUpNameEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل الاسم", Toast.LENGTH_LONG).show();
                return;
            } else if (signUpEmailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل البريد الإليكتروني", Toast.LENGTH_LONG).show();
                return;
            } else if (!signUpEmailEditText.getText().toString().trim().matches(emailPattern)) {
                Toast.makeText(getActivity(), "عفوا .. البريد الإلكتروني غير صحيح .. من فضلك أدخل بريد إليكتروني صحيح", Toast.LENGTH_LONG).show();
                return;
            } else if (signUpPhoneEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل رقم الجوال", Toast.LENGTH_LONG).show();
                return;
            } else if (signUpPasswordEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getActivity(), "من فضلك أدخل كلمة المرور", Toast.LENGTH_LONG).show();
                return;
            }

            String name = signUpNameEditText.getText().toString();
            String phone = signUpPhoneEditText.getText().toString();
            String email = signUpEmailEditText.getText().toString();
            String password = signUpPasswordEditText.getText().toString();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            RegisterApi api = retrofit.create(RegisterApi.class);

            Call<RegisterResponse> registerResponseCall = api.register(name, email, phone, password);

            registerResponseCall.enqueue(new Callback<RegisterResponse>() {

                @EverythingIsNonNull
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    assert response.body() != null;
                    assert getActivity() != null;
                    if (response.body().getStatus() == 200) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Globals.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Globals.USER_TOKEN, response.body().getToken());
                        editor.putString(Globals.USER_NAME, response.body().getUserData().getName());
                        editor.putString(Globals.USER_PHONE, response.body().getUserData().getPhone());
                        editor.putString(Globals.USER_IMAGE_PATH, response.body().getUserData().getImagePath());
                        if (editor.commit()) {
                            Toast.makeText(getContext(), "تم تسجيلك", Toast.LENGTH_LONG).show();
                            Globals.token = sharedPreferences.getString(Globals.USER_TOKEN, "");
                            Globals.phone = sharedPreferences.getString(Globals.USER_PHONE, "");

                            updateFcmToken();

                            Map<String, Object> newUser = new HashMap<>();
                            newUser.put("fcmToken", Globals.fcmToken);
                            newUser.put("name", response.body().getUserData().getName());
                            newUser.put("phone", response.body().getUserData().getPhone());
                            newUser.put("imagePath", response.body().getUserData().getImagePath());
                            FirebaseFirestore.getInstance().collection("users").add(newUser);
                            getActivity().finish();
                        }
                    } else {
                        Toast.makeText(getContext(), response.body().getErrors()[0], Toast.LENGTH_LONG).show();
                    }
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {

                }
            });
        });
    }

    private void updateFcmToken() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        FcmTokenUpdateApi api = retrofit.create(FcmTokenUpdateApi.class);

        Call<FcmTokenUpdateResponse> fcmTokenUpdateResponseCall = api.updateFcmToken(Globals.token, Globals.fcmToken);

        fcmTokenUpdateResponseCall.enqueue(new Callback<FcmTokenUpdateResponse>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<FcmTokenUpdateResponse> call, Response<FcmTokenUpdateResponse> response) {
                assert response.body() != null;
                Log.d(Globals.TAG, response.body().getMessage());
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<FcmTokenUpdateResponse> call, Throwable t) {
                Log.e(Globals.TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
