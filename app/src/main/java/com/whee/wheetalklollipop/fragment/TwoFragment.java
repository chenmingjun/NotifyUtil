package com.whee.wheetalklollipop.fragment;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.whee.wheetalklollipop.R;
import com.whee.wheetalklollipop.activity.OtherActivity;
import com.whee.wheetalklollipop.notification.NotifyUtil;

import java.util.ArrayList;

/**
 * Created by wenmingvs on 2016/1/14.
 */
public class TwoFragment extends Fragment implements View.OnClickListener {
    private static int NOTIFICATION_ID = 13565400;
    private Context mContext;
    private View mView;
    private RelativeLayout button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private NotifyUtil currentNotify;


    public TwoFragment(Context context) {
        mContext = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_two, container, false);
        initClickListener();
        return mView;
    }

    private void initClickListener() {
        button1 = (RelativeLayout) mView.findViewById(R.id.button1);
        button2 = (RelativeLayout) mView.findViewById(R.id.button2);
        button3 = (RelativeLayout) mView.findViewById(R.id.button3);
        button4 = (RelativeLayout) mView.findViewById(R.id.button4);
        button5 = (RelativeLayout) mView.findViewById(R.id.button5);
        button6 = (RelativeLayout) mView.findViewById(R.id.button6);
        button7 = (RelativeLayout) mView.findViewById(R.id.button7);
        button8 = (RelativeLayout) mView.findViewById(R.id.button8);
        button9 = (RelativeLayout) mView.findViewById(R.id.button9);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                notify_normal_singLine();
                break;
            case R.id.button2:
                notify_normal_moreLine();
                break;
            case R.id.button3:
                notify_mailbox();
                break;
            case R.id.button4:
                notify_bigPic();
                break;
            case R.id.button5:
                notify_customview();
                break;
            case R.id.button6:
                notify_buttom();
                break;
            case R.id.button7:
                notify_progress();
                break;
            case R.id.button8:

                break;
            case R.id.button9:
                currentNotify.clear();
                break;
        }
    }


    /**
     * 高仿淘宝
     */
    private void notify_normal_singLine() {
        int smallIcon = R.drawable.tb_bigicon;
        Intent intent = new Intent(mContext, OtherActivity.class);
        String ticker = "您有一条新通知";
        String title = "双十一大推送";
        String content = "仿真皮肤充气娃娃，女朋友带回家！";
        NotifyUtil notify1 = new NotifyUtil(mContext, 1);
        notify1.notify_normal_singline(intent, smallIcon, ticker, title, content);
        currentNotify = notify1;
    }

    /**
     * 高仿网易新闻
     */
    private void notify_normal_moreLine() {
        Intent intent = new Intent(mContext, OtherActivity.class);
        int smallIcon = R.drawable.netease_bigicon;
        String ticker = "您有一条新通知";
        String title = "朱立伦请辞国民党主席 副主席黄敏惠暂代党主席";
        String content = "据台湾“中央社”报道，国民党主席朱立伦今天(18日)向中常会报告，为败选请辞党主席一职，他感谢各位中常委的指教包容，也宣布未来党务工作由副主席黄敏惠暂代，完成未来所有补选工作。";
        NotifyUtil notify2 = new NotifyUtil(mContext, 2);
        notify2.notify_normail_moreline(intent, smallIcon, ticker, title, content);
        currentNotify = notify2;
    }

    /**
     * 收件箱样式
     */
    private void notify_mailbox() {
        Intent intent = new Intent(mContext, OtherActivity.class);
        int largeIcon = R.drawable.fbb_largeicon;
        int smallIcon = R.drawable.wx_smallicon;
        String ticker = "您有一条新通知";
        String title = "冰冰";
        ArrayList<String> messageList = new ArrayList<String>();
        messageList.add("文明,今晚有空吗？");
        messageList.add("晚上跟我一起去玩吧?");
        messageList.add("怎么不回复我？？我生气了！！");
        messageList.add("文明，别不理我！！！");
        String content = "[" +messageList.size()+ "条]" + title + ": " + messageList.get(0);
        NotifyUtil notify3 = new NotifyUtil(mContext, 3);
        notify3.notify_mailbox(intent, smallIcon, largeIcon, messageList, ticker,
                title, content);
        currentNotify = notify3;
    }

    /**
     * 高仿系统截图通知
     */
    private void notify_bigPic() {
        NotifyUtil notify3 = new NotifyUtil(mContext, 4);
    }


    /**
     * 高仿应用宝
     */
    private void notify_customview() {
        Intent intent = new Intent(mContext, OtherActivity.class);
        String ticker = "您有一条新通知";

        //设置自定义布局中按钮的跳转界面
        Intent btnIntent = new Intent(mContext, OtherActivity.class);
        btnIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //如果是启动activity，那么就用PendingIntent.getActivity，如果是启动服务，那么是getService
        PendingIntent Pintent = PendingIntent.getActivity(mContext,
                (int) SystemClock.uptimeMillis(), btnIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 自定义布局
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.yyb_notification);
        remoteViews.setImageViewResource(R.id.image, R.drawable.yybao_bigicon);
        remoteViews.setTextViewText(R.id.title, "垃圾安装包太多");
        remoteViews.setTextViewText(R.id.text, "3个无用安装包，清理释放的空间");
        remoteViews.setOnClickPendingIntent(R.id.button, Pintent);//定义按钮点击后的动作
        int smallIcon = R.drawable.yybao_smaillicon;
        NotifyUtil notify2 = new NotifyUtil(mContext, 5);
        notify2.notify_customview(remoteViews, intent, smallIcon, ticker);
        currentNotify = notify2;
    }

    /**
     * 高仿Android更新提醒样式
     */
    private void notify_buttom() {
        int smallIcon = R.drawable.android_bigicon;
        int lefticon = R.drawable.android_leftbutton;
        String lefttext = "以后再说";
        Intent leftIntent = new Intent();
        int righticno = R.drawable.android_rightbutton;
        String righttext = "安装";
        Intent rightIntent = new Intent(mContext, OtherActivity.class);
        NotifyUtil notify3 = new NotifyUtil(mContext, 6);
        String ticker = "您有一条新通知";
        notify3.notify_button(smallIcon, lefticon, lefttext, leftIntent, righticno, righttext, rightIntent, ticker, "系统更新已下载完毕", "Android 6.0.1");
        currentNotify = notify3;
    }


    /**
     * 高仿Android系统下载样式
     */
    private void notify_progress() {
        Intent intent = new Intent(mContext, OtherActivity.class);
        int smallIcon = R.drawable.android_bigicon;
        String ticker = "您有一条新通知";
        NotifyUtil notify4 = new NotifyUtil(mContext, 7);
        notify4.notify_progress(intent, smallIcon, ticker, "Android 6.0.1 下载", "正在下载中");
        currentNotify = notify4;
    }


}
