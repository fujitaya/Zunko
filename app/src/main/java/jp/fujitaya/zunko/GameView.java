package jp.fujitaya.zunko;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    //Frame Per Second
    public static final int FPS = 60;
    //1フレームあたりの実行間隔
    public static final long INTERVAL = (long)(Math.floor(
            (double)TimeUnit.SECONDS.toNanos(1L) / (double)FPS));

    //FPS維持のための定期実行スケジューラ
    private ScheduledExecutorService scheduler;
    //FPSカウンタ
    private GameScene fpswatch;

    public GameView(Context context){
        super(context);
        init();
    }

    //初期化
    private void init(){
        scheduler = null;
        //コールバック登録
        getHolder().addCallback(this);
        fpswatch = new SceneFpsCounter();
    }

    //定期更新処理
    private void update(){
        if (fpswatch != null) fpswatch.update();
    }

    //定期描画処理
    //draw(Canvas)だと名前が被るので変更
    private void doDraw(Canvas canvas){
        if (fpswatch != null) fpswatch.draw(canvas);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();

                Canvas canvas = holder.lockCanvas();
                doDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        },100,INTERVAL,TimeUnit.NANOSECONDS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //TODO:解像度変更時の処理
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        scheduler.shutdown();
        holder.removeCallback(this);
        fpswatch.dispose();
        fpswatch = null;
    }
}
