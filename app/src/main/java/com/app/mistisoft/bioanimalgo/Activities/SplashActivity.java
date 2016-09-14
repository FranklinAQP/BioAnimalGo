package com.app.mistisoft.bioanimalgo.Activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.app.mistisoft.bioanimalgo.R;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private static final int mtime_s = 3;
    private static final int mtime_ms = mtime_s*1000;
    private static final int mdelay = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.progressBarSplash);
        progressBar.setMax(get_maxprogress());
        init_animation();
    }

    private void init_animation() {
        new CountDownTimer(mtime_ms, mdelay) {
            @Override
            public void onTick(long l) {
                progressBar.setProgress(get_progress(l));
            }

            @Override
            public void onFinish() {
                Intent nintent = new Intent(SplashActivity.this, MainActivity.class);
                //nintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(nintent);
                finish();
            }
        }.start();
    }

    private int get_progress(long rest_time) {
        return (int)((mtime_ms-rest_time)/mdelay);
    }

    public int get_maxprogress() {
        return (int)(mtime_ms/mdelay);
    }
}
