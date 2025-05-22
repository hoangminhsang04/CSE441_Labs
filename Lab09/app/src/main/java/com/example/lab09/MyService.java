package com.example.lab09;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    MediaPlayer mymedia;

    @Override
    public IBinder onBind(Intent intent) {
        // Service này không hỗ trợ bind
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mymedia = MediaPlayer.create(this, R.raw.tinhme); // Đảm bảo tên file đúng
        mymedia.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mymedia != null) {
            if (mymedia.isPlaying()) {
                mymedia.pause();
            } else {
                mymedia.start();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mymedia != null) {
            if (mymedia.isPlaying()) {
                mymedia.stop();
            }
            mymedia.release();
        }
        super.onDestroy();
    }
}
