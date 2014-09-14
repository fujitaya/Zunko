package jp.fujitaya.zunko.util;

import android.util.Log;

import java.util.concurrent.TimeUnit;

public class FpsCounter {
    private long lastTimeBySec;
    private long frameCount;
    private float fps;

    public FpsCounter(){
        fps = 0f;
        frameCount = 0L;
        lastTimeBySec = System.nanoTime();
    }
    public void update() {
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTimeBySec;
        frameCount++;

        if (elapsedTime > TimeUnit.SECONDS.toNanos(1L)){
            fps = (float)((double)TimeUnit.SECONDS.toNanos(frameCount) / elapsedTime);
            lastTimeBySec = currentTime;
            frameCount = 0L;
            Log.d("FPS", Float.toString(fps));
        }
    }
    public float get(){
        return fps;
    }
}
