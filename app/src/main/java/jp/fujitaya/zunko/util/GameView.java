package jp.fujitaya.zunko.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.fujitaya.zunko.SceneMenu;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    public static final int FPS = 60;
    public static final long INTERVAL = (long)(Math.floor(
            (double)TimeUnit.SECONDS.toNanos(1L) / (double)FPS));

    private ScheduledExecutorService scheduler;
    private FpsCounter fpswatch;
    private GameScene scene;

    public GameView(Context context){
        super(context);
        scheduler = null;
        fpswatch = new FpsCounter();
        scene = new SceneMenu(this.getContext());
        getHolder().addCallback(this);
    }

    private void update(){
        fpswatch.update();
        scene.update();
    }
    private void doDraw(Canvas canvas){
        scene.draw(canvas);
    }

    public void changeScene(GameScene next){
        if(scene != null) scene.dispose();
        scene = next;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        scene.interrupt(event);
        return true;
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                fpswatch.update();
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
        fpswatch = null;
        if(scene != null){
            scene.dispose();
            scene = null;
        }
    }
}
