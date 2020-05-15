package com.pomac.benayty.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.pomac.benayty.R;
import com.pomac.benayty.viewmodel.ContactUsViewModel;

import java.util.HashMap;
import java.util.Map;


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
            ContactUsViewModel contactUsViewModel = ViewModelProviders.of(this).get(ContactUsViewModel.class);

            Map<String, String> data = new HashMap<>();

            data.put("name", contactUsNameEditText.getText().toString());
            data.put("email", contactUsEmailEditText.getText().toString());
            data.put("phone", contactUsPhoneEditText.getText().toString());
            data.put("subject", contactUsSubjectEditText.getText().toString());
            data.put("message", contactUsMessageEditText.getText().toString());

            contactUsViewModel.send(data).observe(getActivity(),
                    response -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(false);
                        builder.setTitle(response.getMessage());
                        builder.setPositiveButton("إغلاق", (dialog, which) -> {
                            contactUsNameEditText.setText("");
                            contactUsEmailEditText.setText("");
                            contactUsPhoneEditText.setText("");
                            contactUsSubjectEditText.setText("");
                            contactUsMessageEditText.setText("");
                            dialog.dismiss();
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
            );
        });
    }
}
