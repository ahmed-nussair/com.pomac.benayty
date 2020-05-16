package com.pomac.benayty.view.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.view.interfaces.AppLoginNavigator;

public class LoginActivity extends AppCompatActivity implements AppLoginNavigator {

    private ImageView backToMainScreen;
    private TextView loginTitleTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        initListeners();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initListeners() {
        backToMainScreen.setOnClickListener(v -> {
            finish();
        });
    }

    private void initViews() {

        backToMainScreen = findViewById(R.id.backToMainScreen);
        loginTitleTextView = findViewById(R.id.loginTitleTextView);
    }

    @Override
    public void navigateToLoginScreen() {
        Navigation.findNavController(findViewById(R.id.login_nav_host)).navigate(R.id.loginFragment);
        loginTitleTextView.setText(getString(R.string.login_title_login));
        backToMainScreen.setOnClickListener(v -> finish());
    }

    @Override
    public void navigateToRegistrationScreen() {
        Navigation.findNavController(findViewById(R.id.login_nav_host)).navigate(R.id.registrationFragment);
        loginTitleTextView.setText(getString(R.string.login_title_register));
        backToMainScreen.setOnClickListener(v -> navigateToLoginScreen());
    }

    @Override
    public void navigateToForgotPasswordScreen() {
        Navigation.findNavController(findViewById(R.id.login_nav_host)).navigate(R.id.forgotPasswordFragment);
        loginTitleTextView.setText(getString(R.string.login_title_forgot_password));
        backToMainScreen.setOnClickListener(v -> navigateToLoginScreen());
    }

    @Override
    public void navigateToUpdatePasswordScreen(String resetCode) {
        Bundle bundle = new Bundle();
        bundle.putString(Globals.RESET_CODE, resetCode);
        Navigation.findNavController(findViewById(R.id.login_nav_host)).navigate(R.id.updatePasswordFragment, bundle);
        loginTitleTextView.setText(getString(R.string.login_title_update_password));
        backToMainScreen.setOnClickListener(v -> navigateToForgotPasswordScreen());
    }
}
