package com.pomac.benayty.view.interfaces;

import android.os.Bundle;

public interface AppNavigator {

    void navigateToMainPage();

    void navigateToWishListPage();

    void navigateToNotificationsPage();

    void navigateToMessagesPage();

    void navigateToMyAdsPage();

    void navigateToContactUsPage();

    void navigateToMainCategoryPage(int mainCategoryId, String mainCategoryName);

    void navigateToSearchResults(int mainCategoryId, int secondaryCategoryId, int areaId, int cityId, Bundle bundle);

    void navigateToAdDetails(int adId, String adName);

    void navigateToAdDetails(int adId, String adName, Bundle bundle);

    void navigateToAddingAdPage();
}
