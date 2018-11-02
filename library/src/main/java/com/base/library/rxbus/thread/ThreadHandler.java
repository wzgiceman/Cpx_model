package com.base.library.rxbus.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 线程处理类
 */
public interface ThreadHandler {
    Executor getExecutor();

    Handler getHandler();

    static ThreadHandler DEFAULT = new ThreadHandler() {
        private Executor executor;
        private Handler handler;

        private final int corePoolSize = Runtime.getRuntime().availableProcessors();
        private static final int KEEP_ALIVE_TIME = 60;
        private BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();

        @Override
        public Executor getExecutor() {
            if(executor == null) {
                executor = new ThreadPoolExecutor(corePoolSize,corePoolSize*2,KEEP_ALIVE_TIME,
                        TimeUnit.SECONDS,workQueue,Executors.defaultThreadFactory());
            }
            return executor;
        }

        @Override
        public Handler getHandler() {
            if(handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            return handler;
        }
    };
}
