package com.fantasy.android.demo.android.job;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fantasy.android.demo.R;

public class JobServiceActivity extends Activity {

    private static final int JOB_INFO_ID = 10001;
    private static final long JOB_PERIODIC = 60 * 60 * 1000L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobservice_test);
        findViewById(R.id.job_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onJobStartClick();
            }
        });
    }

    private void onJobStartClick() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(this, MyJobService.class);
        JobInfo jobinfo = new JobInfo.Builder(JOB_INFO_ID, componentName)
                .setPeriodic(JOB_PERIODIC)
                .build();
        jobScheduler.schedule(jobinfo);
    }
}
