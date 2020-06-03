package com.pomac.benayty;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pomac.benayty.view.activities.ChattingActivity;
import com.pomac.benayty.view.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class BenaytyMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFERENCES, MODE_PRIVATE);

        if (sharedPreferences.contains(Globals.APP_STATUS)) {
            Globals.notificationsData.add(remoteMessage.getData().toString());
        } else {
            try {
                String moreDataString = remoteMessage.getData().get("moredata");

                assert moreDataString != null;

                JSONObject moreData = new JSONObject(moreDataString);

                int type = Integer.parseInt(moreData.get("type").toString());

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "")
                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentText(Objects.requireNonNull(remoteMessage.getData().get("message")).toString())
//                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                Intent intent = null;
                switch (type) {
                    case 1:
                        intent = new Intent(this, MainActivity.class);
                        intent.putExtra("flag", "new_comment");
                        intent.putExtra("id", Integer.parseInt(moreData.get("advertisement_id").toString()));

                        builder.setContentTitle(remoteMessage.getData().get("message"));
                        break;
                    case 3:
                        String userName = remoteMessage.getData().get("userName");
                        String from = moreData.getString("phone");


                        String to = sharedPreferences.getString(Globals.USER_PHONE, "");

                        intent = new Intent(this, ChattingActivity.class);
                        intent.putExtra(Globals.USER_NAME, userName);
                        intent.putExtra(Globals.FROM, from);
                        intent.putExtra(Globals.TO, to);

                        builder.setContentTitle("رسالة من " + userName);
                        builder.setContentText(Objects.requireNonNull(remoteMessage.getData().get("message")));
                        break;
                }

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                builder.setContentIntent(pendingIntent);

                Log.d(Globals.TAG, "Type: " + type);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify((int) System.currentTimeMillis(), builder.build());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.d(Globals.TAG, "New FCM token: " + s);
//        sendRegistrationToServer(s);

        SharedPreferences sharedPreferences = getSharedPreferences(Globals.SHARED_PREFERENCES, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Globals.FCM_TOKEN, s);

        if (editor.commit()) {
            Log.d(Globals.TAG, "FCM Token: " + sharedPreferences.getString(Globals.FCM_TOKEN, ""));
        }
    }
}
