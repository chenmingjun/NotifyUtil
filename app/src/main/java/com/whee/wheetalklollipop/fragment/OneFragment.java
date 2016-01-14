package com.whee.wheetalklollipop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.whee.wheetalklollipop.Features;
import com.whee.wheetalklollipop.R;
import com.whee.wheetalklollipop.notification.BackgroundMethod;


/**
 * Created by wenmingvs on 2016/1/14.
 */
public class OneFragment extends Fragment {

    private Context mContext;
    private View mView;

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    private TextView mTextView;
    public OneFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one, container, false);
        initCheckBox();
        initTextView();

        return mView;
    }

    private void initTextView() {
        mTextView = (TextView) mView.findViewById(R.id.reminder);
    }

    private void initCheckBox() {
        checkBox1 = (CheckBox) mView.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) mView.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) mView.findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) mView.findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox) mView.findViewById(R.id.checkbox5);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    deselectAll(R.id.checkbox1);
                    Features.BGK_METHOD = BackgroundMethod.BKGMETHOD_GETRUNNING_TASK;

                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    deselectAll(R.id.checkbox2);
                    Features.BGK_METHOD = BackgroundMethod.BKGMETHOD_GETRUNNING_PROCESS;
                }
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    deselectAll(R.id.checkbox3);
                    Features.BGK_METHOD = BackgroundMethod.BKGMETHOD_GETRUNNING_PROCESS_IN_ASYN;
                }
            }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    deselectAll(R.id.checkbox4);
                    Features.BGK_METHOD = BackgroundMethod.BKGMETHOD_GETAPPLICATION_VALUE;
                }
            }
        });
        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    deselectAll(R.id.checkbox5);
                    Features.BGK_METHOD = BackgroundMethod.BKGMETHOD_GETUSAGESTATS;
                }
            }
        });

    }

    private void deselectAll(int except) {

        switch (except) {
            case R.id.checkbox1:
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                break;
            case R.id.checkbox2:
                checkBox1.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                break;
            case R.id.checkbox3:
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                break;
            case R.id.checkbox4:
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox5.setChecked(false);
                break;
            case R.id.checkbox5:
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                break;
        }

    }

}
