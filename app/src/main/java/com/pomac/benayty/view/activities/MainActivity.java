package com.pomac.benayty.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import com.pomac.benayty.Globals;
import com.pomac.benayty.R;
import com.pomac.benayty.adapters.DrawerAdapter;
import com.pomac.benayty.view.DrawerItem;
import com.pomac.benayty.view.interfaces.AppNavigator;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AppNavigator {

    private TextView pageTitle;
    private ImageView drawerButton;
    private ImageView back;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;

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

    }

    private void attachListenersForViews() {
        menuItemHome.setOnClickListener(l -> {
            menuItemHome.setSelected(true);
            menuItemHeart.setSelected(false);
            menuItemNotification.setSelected(false);
            menuItemSpeech.setSelected(false);

            menuItemHomeBackground.setSelected(true);
            menuItemHeartBackground.setSelected(false);
            menuItemNotificationBackground.setSelected(false);
            menuItemSpeechBackground.setSelected(false);

            Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.mainFragment);
        });

        menuItemHeart.setOnClickListener(l -> {
            menuItemHome.setSelected(false);
            menuItemHeart.setSelected(true);
            menuItemNotification.setSelected(false);
            menuItemSpeech.setSelected(false);

            menuItemHomeBackground.setSelected(false);
            menuItemHeartBackground.setSelected(true);
            menuItemNotificationBackground.setSelected(false);
            menuItemSpeechBackground.setSelected(false);

            Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.wishlistFragment);
        });

        menuItemNotification.setOnClickListener(l -> {

            menuItemHome.setSelected(false);
            menuItemHeart.setSelected(false);
            menuItemNotification.setSelected(true);
            menuItemSpeech.setSelected(false);

            menuItemHomeBackground.setSelected(false);
            menuItemHeartBackground.setSelected(false);
            menuItemNotificationBackground.setSelected(true);
            menuItemSpeechBackground.setSelected(false);

            Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.notificationFragment);
        });

        menuItemSpeech.setOnClickListener(l -> {
            menuItemHome.setSelected(false);
            menuItemHeart.setSelected(false);
            menuItemNotification.setSelected(false);
            menuItemSpeech.setSelected(true);

            menuItemHomeBackground.setSelected(false);
            menuItemHeartBackground.setSelected(false);
            menuItemNotificationBackground.setSelected(false);
            menuItemSpeechBackground.setSelected(true);

            Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.messagesFragment);
        });
    }

    private void initViews() {
        pageTitle = findViewById(R.id.pageTitle);
        drawerButton = findViewById(R.id.drawerButton);
        back = findViewById(R.id.back);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerListView = findViewById(R.id.drawerListView);

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

                menuItemHome.setSelected(true);
                menuItemHeart.setSelected(false);
                menuItemNotification.setSelected(false);
                menuItemSpeech.setSelected(false);

                menuItemHomeBackground.setSelected(true);
                menuItemHeartBackground.setSelected(false);
                menuItemNotificationBackground.setSelected(false);
                menuItemSpeechBackground.setSelected(false);

                Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.mainFragment);

            } else if(item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_my_ads))) {
                drawerLayout.closeDrawer(Gravity.RIGHT);

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
            } else if(item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_wish_list))) {
                drawerLayout.closeDrawer(Gravity.RIGHT);

                menuItemHome.setSelected(false);
                menuItemHeart.setSelected(true);
                menuItemNotification.setSelected(false);
                menuItemSpeech.setSelected(false);

                menuItemHomeBackground.setSelected(false);
                menuItemHeartBackground.setSelected(true);
                menuItemNotificationBackground.setSelected(false);
                menuItemSpeechBackground.setSelected(false);

                Navigation.findNavController(findViewById(R.id.nav_host)).navigate(R.id.wishlistFragment);
            } else if(item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_register))) {
                Log.d(Globals.TAG, getResources().getString(R.string.drawer_item_register));
                drawerLayout.closeDrawer(Gravity.RIGHT);
            } else if(item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_contact_us))) {
                drawerLayout.closeDrawer(Gravity.RIGHT);

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
            } else if(item.getDrawerItemTitle().equals(getResources().getString(R.string.drawer_item_login_logout))) {
                Log.d(Globals.TAG, getResources().getString(R.string.drawer_item_login_logout));
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
        });
        drawerButton.setOnClickListener(l -> drawerLayout.openDrawer(Gravity.RIGHT));
    }

    @Override
    public void setTitle(String title) {
        pageTitle.setText(title);
    }

    @Override
    public void navigateToOtherPage() {
        FrameLayout menuItemHomeBackground = findViewById(R.id.menu_item_home_background);
        FrameLayout menuItemHeartBackground = findViewById(R.id.menu_item_heart_background);
        FrameLayout menuItemNotificationBackground = findViewById(R.id.menu_item_notification_background);
        FrameLayout menuItemSpeechBackground = findViewById(R.id.menu_item_speech_background);

        ImageView menuItemHome = findViewById(R.id.menu_item_home);
        ImageView menuItemHeart = findViewById(R.id.menu_item_heart);
        ImageView menuItemNotification = findViewById(R.id.menu_item_notification);
        ImageView menuItemSpeech = findViewById(R.id.menu_item_speech);

        menuItemHome.setSelected(false);
        menuItemHeart.setSelected(false);
        menuItemNotification.setSelected(false);
        menuItemSpeech.setSelected(false);

        menuItemHomeBackground.setSelected(false);
        menuItemHeartBackground.setSelected(false);
        menuItemNotificationBackground.setSelected(false);
        menuItemSpeechBackground.setSelected(false);
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
