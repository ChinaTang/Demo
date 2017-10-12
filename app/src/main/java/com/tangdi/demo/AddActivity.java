package com.tangdi.demo;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tangdi on 10/8/17.
 */

public class AddActivity extends AppCompatActivity {

    private Button bn01;

    private TextView tv, tv1;

    private LinearLayout linearLayout, line, line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        bn01 = (Button)findViewById(R.id.bn);
        tv = (TextView)findViewById(R.id.tv);
        tv1 = (TextView)findViewById(R.id.tv1);
        linearLayout = (LinearLayout)findViewById(R.id.root);
        line = (LinearLayout)findViewById(R.id.line1) ;
        line2 = (LinearLayout)findViewById(R.id.line2);
        bn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {                //转为竖屏了。


        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {                //转到横屏了。
            line.removeAllViews();
            linearLayout.removeAllViews();
            //linearLayout.addView(tv1);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.addView(line2, layoutParams);
            //linearLayout.addView(tv, layoutParams);
        }
    }
}
