package com.pomac.benayty.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Globals.APP_STATUS, Globals.IS_RUNNING);

        Log.d(Globals.TAG, "FCM: " + sharedPreferences.getString(Globals.FCM_TOKEN, ""));
        if (editor.commit()) {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                intent.putExtra("flag", "main");
                startActivity(intent);
                finish();
            }, 3000);
        }
    }
}
