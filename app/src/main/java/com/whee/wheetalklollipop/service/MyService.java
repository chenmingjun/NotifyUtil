package com.whee.wheetalklollipop.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.whee.wheetalklollipop.Features;
import com.whee.wheetalklollipop.R;
import com.whee.wheetalklollipop.activity.MainActivity;
import com.whee.wheetalklollipop.notification.BackgroundMethod;
import com.whee.wheetalklollipop.receiver.MyReceiver;

import java.util.ArrayList;

/**
 * Created by wenmingvs on 2016/1/13.
 */
public class MyService extends Service {

    private final float UPDATA_INTERVAL = 1;//in seconds
    private String status;
    private Context mContext;
    private ArrayList<String> mContentList;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        initContentData();
    }

    private void initContentData() {
        mContentList = new ArrayList<String>();
        mContentList.add("通过getRunningTask判断");
        mContentList.add("通过getRunningAppProcess判断");
        mContentList.add("通过ActivityLifecycleCallbacks判断");
        mContentList.add("通过UsageStatsManager判断");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        status = getAppStatus() ? "前台" : "后台";
        intent = new Intent(mContext, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.qqsmallicon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.myicon))
                .setContentText(mContentList.get(Features.BGK_METHOD))
                .setContentTitle("App处于" + status)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        startForeground(1, notification);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //这里是定时的,这里设置的是每隔1秒打印一次时间
        int anHour = (int) UPDATA_INTERVAL * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(mContext, MyReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }

    private boolean getAppStatus() {
        return BackgroundMethod.isForeground(mContext, BackgroundMethod.BKGMETHOD_GETAPPLICATION_VALUE, mContext.getPackageName());
    }

}
