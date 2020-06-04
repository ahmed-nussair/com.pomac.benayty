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
import com.pomac.benayty.apis.ContactUsApi;
import com.pomac.benayty.model.response.ContactUsResponse;

import java.util.HashMap;
import java.util.Map;

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
public class ContactUsFragment extends Fragment {

    private EditText contactUsNameEditText;
    private EditText contactUsEmailEditText;
    private EditText contactUsPhoneEditText;
    private EditText contactUsSubjectEditText;
    private EditText contactUsMessageEditText;
    private TextView contactUsSendTextView;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        contactUsNameEditText = view.findViewById(R.id.contactUsNameEditText);
        contactUsEmailEditText = view.findViewById(R.id.contactUsEmailEditText);
        contactUsPhoneEditText = view.findViewById(R.id.contactUsPhoneEditText);
        contactUsSubjectEditText = view.findViewById(R.id.contactUsSubjectEditText);
        contactUsMessageEditText = view.findViewById(R.id.contactUsMessageEditText);
        contactUsSendTextView = view.findViewById(R.id.contactUsSendTextView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getActivity() != null;

        contactUsSendTextView.setOnClickListener(l -> {

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (contactUsNameEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), getString(R.string.enter_your_name), Toast.LENGTH_LONG).show();
                return;
            } else if (contactUsEmailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), getString(R.string.enter_your_email), Toast.LENGTH_LONG).show();
                return;
            } else if (!contactUsEmailEditText.getText().toString().trim().matches(emailPattern)) {
                Toast.makeText(getContext(), getString(R.string.enter_valid_email), Toast.LENGTH_LONG).show();
                return;
            } else if (contactUsPhoneEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), getString(R.string.enter_your_phone), Toast.LENGTH_LONG).show();
                return;
            } else if (contactUsSubjectEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), getString(R.string.enter_subject), Toast.LENGTH_LONG).show();
                return;
            } else if (contactUsMessageEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), getString(R.string.enter_message), Toast.LENGTH_LONG).show();
                return;
            }

            Map<String, String> data = new HashMap<>();

            data.put("name", contactUsNameEditText.getText().toString());
            data.put("email", contactUsEmailEditText.getText().toString());
            data.put("phone", contactUsPhoneEditText.getText().toString());
            data.put("subject", contactUsSubjectEditText.getText().toString());
            data.put("message", contactUsMessageEditText.getText().toString());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            ContactUsApi api = retrofit.create(ContactUsApi.class);

            Call<ContactUsResponse> contactUsResponseCall = api.send(data);

            contactUsResponseCall.enqueue(new Callback<ContactUsResponse>() {

                @EverythingIsNonNull
                @Override
                public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                    contactUsNameEditText.setText("");
                    contactUsEmailEditText.setText("");
                    contactUsPhoneEditText.setText("");
                    contactUsSubjectEditText.setText("");
                    contactUsMessageEditText.setText("");
                    assert response.body() != null;
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<ContactUsResponse> call, Throwable t) {

                }
            });

//            Observable<ContactUsResponse> observable = api.send(data)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//
//            Disposable disposable = observable.subscribe(
//                    response -> {
//                        contactUsNameEditText.setText("");
//                        contactUsEmailEditText.setText("");
//                        contactUsPhoneEditText.setText("");
//                        contactUsSubjectEditText.setText("");
//                        contactUsMessageEditText.setText("");
//                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//            );
//
//            Globals.compositeDisposable.add(disposable);
        });
    }
}
