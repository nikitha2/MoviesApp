package com.nikitha.android.movies.arch;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WorkerManagerScheduling extends Worker {
    private String log_TAG=WorkerManagerScheduling.class.getSimpleName();
    Context appContext;
    public WorkerManagerScheduling(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        appContext=context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Log.i(log_TAG,"--------------inside dowork()");
//            String uri = getInputData().getString("input");
            UpdateBDs.executeTask(appContext);
            return Result.success();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return Result.failure();
    }
}
