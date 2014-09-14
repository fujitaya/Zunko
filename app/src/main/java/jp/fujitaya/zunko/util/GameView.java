package jp.fujitaya.zunko.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
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
    protected GameScene scene;
    private float scale;
    private Matrix scaler;
    private Matrix invScaler;

    public GameView(Context context){
        super(context);
        scheduler = null;
        fpswatch = new FpsCounter();
        scene = new SceneMenu(this);
        getHolder().addCallback(this);
    }

    private void update(){
        fpswatch.update();
        scene.update();
    }
    private void doDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(0f,0f,720f,1280f,paint);
        scene.draw(canvas);
    }

    public void changeScene(GameScene next){
        if(scene != null) scene.dispose();
        scene = next;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float[] mapped = new float[2];
        mapped[0] = event.getX();
        mapped[1] = event.getY();
        invScaler.mapPoints(mapped);

        event.setLocation(mapped[0], mapped[1]);
        scene.interrupt(event);
        return true;
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        //スケール
        setScale();

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();

                Canvas canvas = holder.lockCanvas();
//                canvas.setMatrix(scaler);

                canvas.drawColor(Color.WHITE);
                doDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        },100,INTERVAL,TimeUnit.NANOSECONDS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //スケール
        setScale();
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

    public void setScale(){
        float scaleX = (float)getWidth() / (float)VIEW_WIDTH;
        float scaleY = (float)getHeight() /  (float)VIEW_HEIGHT;
        scale = scaleX > scaleY ? scaleY : scaleX;

        scaler = new Matrix();
        scaler.postScale(scale,scale);
        scaler.postTranslate((getWidth()-VIEW_WIDTH*scale)/2.0f, (getHeight()-VIEW_HEIGHT*scale)/2.0f);
        invScaler = new Matrix();
        invScaler.postTranslate(-(getWidth()-VIEW_WIDTH*scale)/2.0f, -(getHeight()-VIEW_HEIGHT*scale)/2.0f);
        invScaler.postScale(1.0f/scale, 1.0f/scale);
    }
}
