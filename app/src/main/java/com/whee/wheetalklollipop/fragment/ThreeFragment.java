package com.whee.wheetalklollipop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whee.wheetalklollipop.R;

/**
 * Created by wenmingvs on 2016/1/14.
 */
public class ThreeFragment extends Fragment {
    private Context mContext;
    private View mView;

    public ThreeFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_three, container, false);
        return mView;
    }
}
