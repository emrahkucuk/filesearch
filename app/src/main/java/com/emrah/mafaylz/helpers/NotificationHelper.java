package com.emrah.mafaylz.helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.emrah.mafaylz.R;
import com.emrah.mafaylz.activities.HomeActivity;
import com.emrah.mafaylz.model.FileSearchResult;

public class NotificationHelper {
    private static final String CHANNEL_ID = "CHANNEL_100";
    private static final String CHANNEL_NAME = "PROJECT MYFILES";
    private static NotificationHelper notificationHelperInstance;

    private NotificationHelper() {
    }

    public static NotificationHelper getInstance() {
        if (notificationHelperInstance == null) {
            notificationHelperInstance = new NotificationHelper();
        }
        return notificationHelperInstance;
    }


    public void showFileSearchResultNotification(Context context, FileSearchResult fileSearchResult) {
        /*
         * I used some code from github for notifications
         * @link https://github.com/projectmatris/antimalwareapp/blob/868beb6bc7322e9ee6334ea3938ef7dcb64ff459/app/src/main/java/tech/projectmatris/antimalwareapp/scanners/ScannerTask.java#L70
         * */
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(context, CHANNEL_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Test title")
                .setContentIntent(pIntent)
                .setContentText("Search Completed " + " with count: " + fileSearchResult.getFileCount())
                .build();

        notificationManager.notify(100, notification);
    }
}
