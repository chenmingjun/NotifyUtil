package com.whee.wheetalklollipop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends Activity {
    public static final int NOTIFICATION_ID = 1;
    private Button mButton0;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private TextView mTextView;
    private Context mContext;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton0 = (Button) findViewById(R.id.startactivity);
        mButton1 = (Button) findViewById(R.id.send1);
        mButton2 = (Button) findViewById(R.id.send2);
        mButton3 = (Button) findViewById(R.id.send3);
        mButton4 = (Button) findViewById(R.id.send4);
        mTextView = (TextView) findViewById(R.id.textview);
        mContext = this;
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
        mButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
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
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHeadUpNotification();
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(intent);
            }
        });
    }

    /**
     * 最基本的通知视图
     */
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

    /**
     * 只需要加入一行setFullScreenIntent即可完成悬浮式通知的显示
     * FLAG_CANCEL_CURRENT表示如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
     */
    private void createHeadUpNotification() {
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.foldleft)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.lanucher))
                .setContentTitle("折叠式Notification")
                .setContentText("【Git 协作流程】Git 作为一个源码管理系统，不可避免涉及到多人协作。在本文中，作者详细分享了目前三种广泛使用的协作流程——Git flow、Github flow、Gitlab flow.全文")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setFullScreenIntent(pendingIntent, true);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(2, mBuilder.build());
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void showAppTaskInfo(List<ActivityManager.AppTask> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (ActivityManager.AppTask appTask : list) {
            Log.d("wenming", appTask.getTaskInfo().topActivity.getPackageName());
            stringBuffer.append(appTask.getTaskInfo().topActivity.getPackageName());
            stringBuffer.append('\n');
        }
        mTextView.setText(stringBuffer);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean isAppOnForeground2(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTask = activityManager.getAppTasks();
        showAppTaskInfo(appTask);
        if (appTask == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.AppTask appTask1 : appTask) {
            if (appTask1.getTaskInfo().topActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在android5.0版本后获取栈顶应用的方法getRunningTask方法被google给屏蔽掉了，还有一个折中的办法，
     * 使用UsageStatsManager获取，但是这种获取方法需要用户在手机上赋予APP权限才可以使用，就是在安全-高级-有权查看使用情况的应用
     * 在这个模块中勾选上指定APP就可以获取到栈顶的应用名。那么现在问题来了，如何调用系统提供的常量打开“有权查看使用权限的应用”界面。
     */
    private void getTopPackageName() {
        String topPackageName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService("usagestats");
            long time = System.currentTimeMillis();
            // We get usage stats for the last 10 seconds
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 5, time);
            // Sort the stats by the last time used
            if (stats != null) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    mTextView.setText(topPackageName);
                }
            }
        }
    }



}
