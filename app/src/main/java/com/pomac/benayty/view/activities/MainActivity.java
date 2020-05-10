package com.pomac.benayty.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pomac.benayty.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FrameLayout menuItemHomeBackground = findViewById(R.id.menu_item_home_background);
        FrameLayout menuItemHeartBackground = findViewById(R.id.menu_item_heart_background);
        FrameLayout menuItemNotificationBackground = findViewById(R.id.menu_item_notification_background);
        FrameLayout menuItemSpeechBackground = findViewById(R.id.menu_item_speech_background);

        ImageView menuItemHome = findViewById(R.id.menu_item_home);
        ImageView menuItemHeart = findViewById(R.id.menu_item_heart);
        ImageView menuItemNotification = findViewById(R.id.menu_item_notification);
        ImageView menuItemSpeech = findViewById(R.id.menu_item_speech);

        menuItemHome.setSelected(true);
        menuItemHomeBackground.setSelected(true);
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
}
