package com.pomac.benayty.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.apis.AddingCommentApi;
import com.pomac.benayty.model.response.AddingCommentResponse;

import java.util.Objects;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddingCommentDialog extends Dialog {

    private EditText commentEditText;
    private TextView cancelButtonTextView;
    private TextView addCommentButtonTextView;

    private int adId;
    private Fragment fragment;

    public AddingCommentDialog(@NonNull Context context, int themeResId, int adId, Fragment fragment) {
        super(context, themeResId);
        this.adId = adId;
        this.fragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorDrawable background = new ColorDrawable(Color.BLACK);
        background.setAlpha(100);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(background);
        setContentView(R.layout.dialog_writing_comment);

        prepareTheViews();

        prepareTheListeners();
    }

    private void prepareTheViews() {
        commentEditText = findViewById(R.id.commentEditText);
        cancelButtonTextView = findViewById(R.id.cancelButtonTextView);
        addCommentButtonTextView = findViewById(R.id.addCommentButtonTextView);
    }

    private void prepareTheListeners() {
        cancelButtonTextView.setOnClickListener(v -> dismiss());

        addCommentButtonTextView.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            AddingCommentApi addingCommentApi = retrofit.create(AddingCommentApi.class);

            Observable<AddingCommentResponse> addingCommentResponseObservable =
                    addingCommentApi.addComment(
                            Globals.token,
                            adId,
                            commentEditText.getText().toString()
                    );

            Globals.compositeDisposable.add(addingCommentResponseObservable.subscribe(
                    addingCommentResponse -> {
                        Toast.makeText(fragment.getContext(),
                                addingCommentResponse.getMessage() != null ? addingCommentResponse.getMessage() : "null",
                                Toast.LENGTH_LONG)
                                .show();

                        dismiss();
                    },
                    addingCommentError -> {
                        Toast.makeText(fragment.getContext(),
                                "Error adding comment!",
                                Toast.LENGTH_LONG)
                                .show();

                        dismiss();
                    }
            ));
        });
    }
}
