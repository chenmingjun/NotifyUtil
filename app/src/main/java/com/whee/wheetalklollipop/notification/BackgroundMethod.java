package com.whee.wheetalklollipop.notification;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.whee.wheetalklollipop.MyApplication;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wenmingvs on 2016/1/14.
 */
public class BackgroundMethod {
    public static final int BKGMETHOD_GETRUNNING_TASK = 0;
    public static final int BKGMETHOD_GETRUNNING_PROCESS = 1;
    public static final int BKGMETHOD_GETRUNNING_PROCESS_IN_ASYN = 2;
    public static final int BKGMETHOD_GETAPPLICATION_VALUE = 3;
    public static final int BKGMETHOD_GETUSAGESTATS = 4;


    /**
     * 判断前后台的方法
     *
     * @param context  上下文参数
     * @param methodID 方法参数
     * @return
     */
    public static boolean isForeground(Context context, int methodID) {
        switch (methodID) {
            case BKGMETHOD_GETRUNNING_TASK:
                return getRunningTask(context);
            case BKGMETHOD_GETRUNNING_PROCESS:
                return getRunningAppProcesses(context);
            case BKGMETHOD_GETRUNNING_PROCESS_IN_ASYN:
                Toast.makeText(context, "请到LogCat进行观察！！！", Toast.LENGTH_SHORT).show();
            case BKGMETHOD_GETAPPLICATION_VALUE:
                return getApplicationValue((Service) context);
            case BKGMETHOD_GETUSAGESTATS:
                return queryUsageStats(context);
            default:
                return false;
        }
    }

    /**
     * 方法1：通过getRunningTasks判断App是否位于前台，此方法在5.0以上失效
     */
    private static boolean getRunningTask(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        return !TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName());
    }


    /**
     * 方法2：通过getRunningAppProcesses的IMPORTANCE_FOREGROUND属性判断是否位于前台，当service需要常驻后台时候，此方法失效,
     * 在小米 Note上此方法无效，在Nexus上正常
     */
    private static boolean getRunningAppProcesses(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 方法4：通过ActivityLifecycleCallbacks来批量统计Activity的生命周期，来做判断，此方法在API 14以上均有效，但是需要在Application中注册此回调接口
     * 必须：
     * 1. 自定义Application并且注册ActivityLifecycleCallbacks接口
     * 2. AndroidManifest.xml中更改默认的Application为自定义
     * 3. 当Application因为内存不足而被Kill掉时，这个方法仍然能正常使用。虽然全局变量的值会因此丢失，但是再次进入App时候会重新统计一次的
     */
    private static boolean getApplicationValue(Service service) {
        return ((MyApplication) service.getApplication()).getAppCount() > 0;
    }

    private static boolean getApplicationValue(Activity activity) {
        return ((MyApplication) activity.getApplication()).getAppCount() > 0;
    }

    /**
     * 方法5：通过使用UsageStatsManager获取，此方法是ndroid5.0A之后提供的API
     * 必须：
     * 1. 此方法只在android5.0以上有效
     * 2. AndroidManifest中加入此权限<uses-permission xmlns:tools="http://schemas.android.com/tools" android:name="android.permission.PACKAGE_USAGE_STATS"
     * tools:ignore="ProtectedPermissions" />
     * 3. 打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾
     */

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static boolean queryUsageStats(Context context) {
        class RecentUseComparator implements Comparator<UsageStats> {

            @Override
            public int compare(UsageStats lhs, UsageStats rhs) {
                return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs.getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;
            }
        }
        RecentUseComparator mRecentComp = new RecentUseComparator();
        long ts = System.currentTimeMillis();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts - 1000 * 10, ts);
        if (usageStats == null || usageStats.size() == 0) {
            if (HavaPermissionForTest(context) == false) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context, "权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        Collections.sort(usageStats, mRecentComp);
        String currentTopPackage = usageStats.get(0).getPackageName();
        String appPackage = context.getPackageName();
        if (currentTopPackage.equals(appPackage)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否拥有PACKAGE_USAGE_STATS权限
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean HavaPermissionForTest(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    /**
     * 方法3：在异步线程中使用方法2，判断正常，在小米Note上此方法依然有效，Nexus也能正常显示
     * 1. 请注意查看LogCat信息！！！！
     */

    public static class ProcessTask extends AsyncTask<String, Integer, Boolean> {
        private Context context;

        public ProcessTask(Context context) {
            super();
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return getRunningAppProcesses(context);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            String string = result ? "前台" : "后台";
            Log.d("wenming", "前后台判断：" + string);
        }
    }

}
