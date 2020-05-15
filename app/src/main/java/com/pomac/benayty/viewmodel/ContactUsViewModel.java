package com.pomac.benayty.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pomac.benayty.Globals;
import com.pomac.benayty.apis.ContactUsApi;
import com.pomac.benayty.model.response.ContactUsResponse;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactUsViewModel extends ViewModel {

    private MutableLiveData<ContactUsResponse> mutableLiveData;

    public LiveData<ContactUsResponse> send(Map<String, String> data) {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
            sendData(data);
        }
        return mutableLiveData;
    }

    private void sendData(Map<String, String> data) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Globals.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        ContactUsApi api = retrofit.create(ContactUsApi.class);

        Observable<ContactUsResponse> observable = api.send(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(
                response -> mutableLiveData.setValue(response),
                error -> mutableLiveData.setValue(null)
        );

        Globals.compositeDisposable.add(disposable);
    }
}
