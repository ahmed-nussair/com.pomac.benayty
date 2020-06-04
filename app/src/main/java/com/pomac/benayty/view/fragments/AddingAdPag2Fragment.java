package com.pomac.benayty.view.fragments;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.apis.AddingAdApi;
import com.pomac.benayty.model.response.AddingAdResponse;
import com.pomac.benayty.view.interfaces.AppNavigator;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
public class AddingAdPag2Fragment extends Fragment {

    private static final int PICK_IMAGE = 1;
    private View resizer;
    private EditText addingAdNameEditText;
    private EditText addingAdMobileEditText;
    private EditText addingAdDescriptionEditText;
    private ImageView addingImageButtonImageView;
    private ImageView adProposedImageView;
    private TextView addingAddSubmitButton;
    private int mainCategoryId;
    private int secondaryCategoryId;
    private int areaId;
    private int cityId;
    private Bitmap adImageBitmap;
    private File adImageFile;

    private AppNavigator navigator;

    public AddingAdPag2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_ad_pag2, container, false);
        resizer = view.findViewById(R.id.resizer);
        addingAdNameEditText = view.findViewById(R.id.addingAdNameEditText);
        addingAdMobileEditText = view.findViewById(R.id.addingAdMobileEditText);
        addingAdDescriptionEditText = view.findViewById(R.id.addingAdDescriptionEditText);
        addingImageButtonImageView = view.findViewById(R.id.addingImageButtonImageView);
        adProposedImageView = view.findViewById(R.id.adProposedImageView);
        addingAddSubmitButton = view.findViewById(R.id.addingAddSubmitButton);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;

        mainCategoryId = getArguments().getInt(Globals.MAIN_CATEGORY_ID);
        secondaryCategoryId = getArguments().getInt(Globals.SECONDARY_CATEGORY_ID);
        areaId = getArguments().getInt(Globals.AREA_ID);
        cityId = getArguments().getInt(Globals.CITY_ID);

        assert getActivity() != null;

        navigator = (AppNavigator) getActivity();

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        final int level = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getBottom();

        addingAdNameEditText.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            int contentViewBottom = getActivity().getWindow().findViewById(Window.ID_ANDROID_CONTENT).getBottom();

            if (contentViewBottom < level) {
                if (getActivity().getCurrentFocus() == addingAdNameEditText)
                    resizer.setVisibility(View.VISIBLE);
                else
                    resizer.setVisibility(View.GONE);
            } else {
                resizer.setVisibility(View.GONE);
            }
        });

        addingImageButtonImageView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });

        addingAddSubmitButton.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Globals.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            AddingAdApi addingAdApi = retrofit.create(AddingAdApi.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), adImageFile);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", adImageFile.getName(), requestFile);

            Call<AddingAdResponse> addingAdResponseCall = addingAdApi.addAdvertisement(
                    RequestBody.create(MediaType.parse("multipart/form-data"), Globals.token),
                    RequestBody.create(MediaType.parse("multipart/form-data"), "" + mainCategoryId),
                    RequestBody.create(MediaType.parse("multipart/form-data"), "" + secondaryCategoryId),
                    RequestBody.create(MediaType.parse("multipart/form-data"), "" + areaId),
                    RequestBody.create(MediaType.parse("multipart/form-data"), "" + cityId),
                    RequestBody.create(MediaType.parse("multipart/form-data"), addingAdNameEditText.getText().toString()),
                    RequestBody.create(MediaType.parse("multipart/form-data"), addingAdMobileEditText.getText().toString()),
                    RequestBody.create(MediaType.parse("multipart/form-data"), addingAdDescriptionEditText.getText().toString()),
                    body
            );

            addingAdResponseCall.enqueue(new Callback<AddingAdResponse>() {

                @EverythingIsNonNull
                @Override
                public void onResponse(Call<AddingAdResponse> call, Response<AddingAdResponse> response) {
                    assert response.body() != null;
                    Log.d(Globals.TAG, response.body().getMessage());
                    if (response.body().getStatus() == 200) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        navigator.navigateToMainPage();
                    } else {
                        Toast.makeText(getContext(), response.body().getErrors()[0], Toast.LENGTH_LONG).show();
                    }
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<AddingAdResponse> call, Throwable t) {

                }
            });

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {

//            adProposedImageView.setImageURI(data.getData());

            assert data != null;
            assert getActivity() != null;
            Uri uri = data.getData();
            try {
                adImageBitmap = BitmapFactory.decodeStream(new BufferedInputStream(getActivity().getContentResolver().openInputStream(uri)));
                adProposedImageView.setImageBitmap(adImageBitmap);

                String fileName = "";
                Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    //local filesystem
                    int index = cursor.getColumnIndex("_data");
                    if (index == -1)
                    //google drive
                    {
                        index = cursor.getColumnIndex("_display_name");
                    }
                    fileName = cursor.getString(index);
                }

                if (adImageFile != null && adImageFile.exists()) {
                    adImageFile.delete();
                }

                adImageFile = new File(Environment.getExternalStorageDirectory() + "/" + fileName);

                adImageFile.createNewFile();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                adImageBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream outStream;
                outStream = new FileOutputStream(adImageFile);
                outStream.write(bitmapdata);
                outStream.flush();
                outStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
