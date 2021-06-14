package com.example.android.writenotes;

import android.os.Looper;
import android.os.Handler;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final Object LOCK = new Object();
    public static AppExecutors instance;
    public final Executor diskIO;
    public final Executor mainThread;
    public final Executor networkIO;

    public AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance() {
        if(instance == null) {
            synchronized (LOCK) {
                instance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        new MainThreadExecutor(),
                        Executors.newFixedThreadPool(3));
            }
        }
        return instance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
