package com.whee.wheetalklollipop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
    private Button mButton1;
    private Button mButton2;

    public static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = (Button) findViewById(R.id.send1);
        mButton2 = (Button) findViewById(R.id.send2);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBaseNotification();
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCustomNotification();
            }
        });
    }


    private void createBaseNotification() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.baidu.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.qqsmallicon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.qqlargeicon))
                .setContentTitle("普通Notification")
                .setContentText("【辽宁被曝大面积违法种植转基因玉米】环保组织绿色和平近日发布调查报告称，在辽宁省多个中国玉米主产区出现了未经国家批准的转基因玉米。目前国内自产的转基因作物，只有转基因棉花和转基因木瓜，除此之外，非试验用途的转基因作物种植都是违法行为")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    /**
     * 要求Android版本大于等于4.1.2，才能使用折叠式通知的属性
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void createCustomNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.foldleft)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lanucher))
                .setContentTitle("折叠式Notification")
                .setContentText("【Git 协作流程】Git 作为一个源码管理系统，不可避免涉及到多人协作。在本文中，作者详细分享了目前三种广泛使用的协作流程——Git flow、Github flow、Gitlab flow.全文")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Notification notification = mBuilder.build();
        RemoteViews bigContentView =
                new RemoteViews(getPackageName(), R.layout.notification_expanded);
        notification.bigContentView = bigContentView;

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }






}
