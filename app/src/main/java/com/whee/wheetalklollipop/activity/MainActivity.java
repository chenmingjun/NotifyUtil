package com.whee.wheetalklollipop.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.whee.wheetalklollipop.R;
import com.whee.wheetalklollipop.adapter.ViewPagerAdapter;
import com.whee.wheetalklollipop.fragment.OneFragment;
import com.whee.wheetalklollipop.fragment.ThreeFragment;
import com.whee.wheetalklollipop.fragment.TwoFragment;
import com.whee.wheetalklollipop.service.MyService;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Intent intent = new Intent(mContext, MyService.class);
        startService(intent);
        initToolBar();
        initTabViewPager();

    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setVisibility(View.GONE);
    }

    private void initTabViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(mContext), getString(R.string.tab1));
        adapter.addFragment(new TwoFragment(mContext), getString(R.string.tab2));
        adapter.addFragment(new ThreeFragment(mContext), getString(R.string.tab3));
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

}
