package jp.fujitaya.zunko;

import android.graphics.Canvas;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class SceneFpsCounter extends GameScene {
    private long lastTimeBySec;
    private long frameCount;
    private float fps;

    @Override
    public void init() {
        lastTimeBySec = System.nanoTime();
        frameCount = 0L;
        fps = 0f;
    }

    @Override
    public void update() {
        //現在時刻，差分時刻を取得，フレームカウント
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTimeBySec;
        frameCount++;

        //差分が1秒を越えたらFPS再計算
        if (elapsedTime > TimeUnit.SECONDS.toNanos(1L)){
            fps = (float)((double)TimeUnit.SECONDS.toNanos(frameCount) / elapsedTime);
            lastTimeBySec = currentTime;
            frameCount = 0L;
            Log.d("FPS", Float.toString(fps));
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void dispose() {

    }
}
