package jp.fujitaya.zunko.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.fujitaya.zunko.SceneMenu;
import jp.fujitaya.zunko.sugaya.MainScene;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    public  static final int VIEW_WIDTH = 720;
    public  static final int VIEW_HEIGHT = 1280;

    public static final int FPS = 60;
    public static final long INTERVAL = (long)(Math.floor(
            (double)TimeUnit.SECONDS.toNanos(1L) / (double)FPS));

    private ScheduledExecutorService scheduler;
    private FpsCounter fpswatch;
    private GameScene scene;
    private float scale;

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
        event.setLocation(event.getX()/scale, event.getY()/scale);
        scene.interrupt(event);
        return true;
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        //スケール
        float scaleX = (float)getWidth() / (float)VIEW_WIDTH;
        float scaleY = (float)getHeight() /  (float)VIEW_HEIGHT;
        scale = scaleX > scaleY ? scaleY : scaleX;

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();

                Canvas canvas = holder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                canvas.translate((getWidth() - VIEW_WIDTH)/2.0f*scale,
                        (getHeight() - VIEW_HEIGHT)/2.0f*scale);
                canvas.scale(scale, scale);
                doDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        },100,INTERVAL,TimeUnit.NANOSECONDS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //スケール
        float scaleX = (float)getWidth() / VIEW_WIDTH;
        float scaleY = (float)getHeight() /  VIEW_HEIGHT;
        scale = scaleX > scaleY ? scaleY : scaleX;
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
