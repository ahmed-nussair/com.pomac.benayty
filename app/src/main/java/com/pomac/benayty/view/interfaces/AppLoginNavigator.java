package com.pomac.benayty.view.interfaces;

public interface AppLoginNavigator {

    void navigateToLoginScreen();

    void navigateToRegistrationScreen();

    void navigateToForgotPasswordScreen();

    void navigateToUpdatePasswordScreen(String resetCode);
}
