package com.pomac.benayty.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.DrawerAdapter;
import com.pomac.benayty.view.DrawerItem;
import com.pomac.benayty.view.interfaces.AppNavigator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AppNavigator {

    private TextView pageTitle;
    private ImageView drawerButton;
    private ImageView back;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private FloatingActionButton addingAdFloatingButton;

    private FrameLayout menuItemHomeBackground;
    private FrameLayout menuItemHeartBackground;
    private FrameLayout menuItemNotificationBackground;
    private FrameLayout menuItemSpeechBackground;

    private ImageView menuItemHome;
    private ImageView menuItemHeart;
    private ImageView menuItemNotification;
    private ImageView menuItemSpeech;

    @SuppressLint("RtlHardcoded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        prepareDrawer();

        attachListenersForViews();

        navigateToMainPage();

    }

    private void attachListenersForViews() {
        menuItemHome.setOnClickListener(l -> navigateToMainPage());

        menuItemHeart.setOnClickListener(l -> navigateToWishListPage());

        menuItemNotification.setOnClickListener(l -> navigateToNotificationsPage());

        menuItemSpeech.setOnClickListener(l -> navigateToMessagesPage());

        addingAdFloatingButton.setOnClickListener(l -> navigateToAddingAdPage());
    }

    private void initViews() {
        pageTitle = findViewById(R.id.pageTitle);
        drawerButton = findViewById(R.id.drawerButton);
        back = findViewById(R.id.back);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerListView = findViewById(R.id.drawerListView);
        addingAdFloatingButton = findViewById(R.id.addingAdFloatingButton);

        menuItemHomeBackground = findViewById(R.id.menu_item_home_background);
        menuItemHeartBackground = findViewById(R.id.menu_item_heart_background);
        menuItemNotificationBackground = findViewById(R.id.menu_item_notification_background);
        menuItemSpeechBackground = findViewById(R.id.menu_item_speech_background);

        menuItemHome = findViewById(R.id.menu_item_home);
        menuItemHeart = findViewById(R.id.menu_item_heart);
        menuItemNotification = findViewById(R.id.menu_item_notification);
        menuItemSpeech = findViewById(R.id.menu_item_speech);

        menuItemHome.setSelected(true);
        menuItemHomeBackground.setSelected(true);
    }

    @SuppressLint("RtlHardcoded")
    private void prepareDrawer() {
        ArrayList<DrawerItem> drawerItems = new ArrayList<>();
        drawerItems.add(new DrawerItem("الصفحة الرئيسية", R.drawable.home));
        drawerItems.add(new DrawerItem("إعلاناتي", R.drawable.offer));
        drawerItems.add(new DrawerItem("التسجيل", R.drawable.persona));
        drawerItems.add(new DrawerItem("المفضلة", R.drawable.favourite));
        drawerItems.add(new DrawerItem("اتصل بنا", R.drawable.contact));
        drawerItems.add(new DrawerItem("تسجيل الدخول / الخروج", R.drawable.loginout));

        DrawerAdapter adapter = new DrawerAdapter(this, drawerItems);
        drawerListView.setAdapter(adapter);

        drawerListView.setOnItemClickListener((parent, view, position, id) -> {
            DrawerItem item = (DrawerItem) adapter.getItem(position);

            if(item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_main))) {

                drawerLayout.closeDrawer(Gravity.RIGHT);

                navigateToMainPage();

            } else if (item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_my_ads))) {
                drawerLayout.closeDrawer(Gravity.RIGHT);

                navigateToMyAdsPage();
            } else if (item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_wish_list))) {
                drawerLayout.closeDrawer(Gravity.RIGHT);

                navigateToWishListPage();
            } else if (item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_register))) {
                drawerLayout.closeDrawer(Gravity.RIGHT);

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else if (item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_contact_us))) {
                drawerLayout.closeDrawer(Gravity.RIGHT);

                navigateToContactUsPage();
            } else if (item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_login_logout))) {
                Log.d(Globals.TAG, getResources().getString(R.string.drawer_item_login_logout));
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });
        drawerButton.setOnClickListener(l -> drawerLayout.openDrawer(Gravity.RIGHT));
    }

    @Override
    public void navigateToMainCategoryPage(int mainCategoryId, String mainCategoryName) {

        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        pageTitle.setText(mainCategoryName);

        Bundle mainCategoryBundle = new Bundle();
        mainCategoryBundle.putInt(Globals.MAIN_CATEGORY_ID, mainCategoryId);
        mainCategoryBundle.putString(Globals.MAIN_CATEGORY_NAME, mainCategoryName);

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.mainCategoryFragment, mainCategoryBundle);

        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v -> {
            navigateToMainPage();
            back.setVisibility(View.GONE);
        });
    }

    @Override
    public void navigateToSearchResults(int mainCategoryId, int secondaryCategoryId, int areaId, int cityId, Bundle bundle) {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        pageTitle.setText("نتائج البحث");

        Bundle searchResultsBundle = new Bundle();
        searchResultsBundle.putInt(Globals.MAIN_CATEGORY_ID, mainCategoryId);
        searchResultsBundle.putInt(Globals.SECONDARY_CATEGORY_ID, secondaryCategoryId);
        searchResultsBundle.putInt(Globals.AREA_ID, areaId);
        searchResultsBundle.putInt(Globals.CITY_ID, cityId);

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.searchResultsFragment, searchResultsBundle);

        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v -> {

            int id = bundle.getInt(Globals.MAIN_CATEGORY_ID);
            String name = bundle.getString(Globals.MAIN_CATEGORY_NAME);

            navigateToMainCategoryPage(id, name);

        });
    }

    @Override
    public void navigateToAdDetails(int adId, String adName) {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        Bundle detailsFragmentBundle = new Bundle();

        detailsFragmentBundle.putInt(Globals.AD_ID, adId);
        pageTitle.setText(adName);

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.adDetailsFragment, detailsFragmentBundle);
    }

    @Override
    public void navigateToAdDetails(int adId, String adName, Bundle bundle) {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        Bundle detailsFragmentBundle = new Bundle();

        detailsFragmentBundle.putInt(Globals.AD_ID, adId);
        pageTitle.setText(adName);

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.adDetailsFragment, detailsFragmentBundle);

        back.setVisibility(View.GONE);
//
//        back.setVisibility(View.VISIBLE);
//        back.setOnClickListener(v -> {
//            n;
//            back.setVisibility(View.GONE);
//        });
    }

    @Override
    public void navigateToMainPage() {

        menuItemHome.setSelected(true);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(true);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        pageTitle.setText(getString(R.string.home_page_title));

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.mainFragment);

    }

    @Override
    public void navigateToWishListPage() {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(true);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(true);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        pageTitle.setText(getResources().getString(R.string.drawer_item_wish_list));

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.wishlistFragment);
    }

    @Override
    public void navigateToNotificationsPage() {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(true);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(true);
        menuItemSpeechBackground.setSelected(false);

        pageTitle.setText(getResources().getString(R.string.notifications_page_title));

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.notificationFragment);
    }

    @Override
    public void navigateToMessagesPage() {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(true);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(true);

        pageTitle.setText(getResources().getString(R.string.messages_page_title));

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.messagesFragment);
    }

    @Override
    public void navigateToMyAdsPage() {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        pageTitle.setText(getResources().getString(R.string.drawer_item_my_ads));

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.myAdsFragment);
    }

    @Override
    public void navigateToContactUsPage() {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        pageTitle.setText(getResources().getString(R.string.drawer_item_contact_us));

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.contactUsFragment);
    }

    @Override
    public void navigateToAddingAdPage() {
        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);

        pageTitle.setText(getString(R.string.adding_ad));

        Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.addingAdPag1Fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Globals.compositeDisposable.dispose();
        if(Globals.compositeDisposable.isDisposed()) {
            Log.d(Globals.TAG, "Disposed successfully");
        }
    }
}
