package jp.fujitaya.zunko.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.fujitaya.zunko.GameActivity;
import jp.fujitaya.zunko.SceneMenu;
import jp.fujitaya.zunko.jimmy.OnDraw;

import static jp.fujitaya.zunko.GameActivity.VIEW_WIDTH;
import static jp.fujitaya.zunko.GameActivity.VIEW_HEIGHT;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    public static final int FPS = 30;
    public static final long INTERVAL = (long)(Math.floor(
            (double) TimeUnit.SECONDS.toNanos(1L) / (double)FPS));

    protected float scale;
    protected Matrix scaler;
    protected Matrix invScaler;
    protected boolean wasOutside;
    private FpsCounter fpswatch;
    private ScheduledExecutorService scheduler;
    private OnTouchListener onTouchListener;
    private OnDraw onDraw;

    public GameView(Context context, OnTouchListener onTouchListener, OnDraw onDraw){
        super(context);
        fpswatch = new FpsCounter("draw");
        this.onTouchListener = onTouchListener;
        this.onDraw = onDraw;
        wasOutside = false;
        getHolder().addCallback(this);
    }

    private void doDraw(Canvas canvas){
        RectF mappedArea;

        fpswatch.update();
        //全範囲クリア
        canvas.drawColor(Color.BLACK);
        //拡縮指定
        canvas.setMatrix(scaler);
        //描画範囲限定
        canvas.clipRect(new Rect(0,0, VIEW_WIDTH,VIEW_HEIGHT));
        //描画範囲クリア
        //drawColorだと左側余白も描画される模様
        canvas.drawColor(Color.WHITE);

        onDraw.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float[] mapped = new float[2];
        int actionID = event.getAction();

        //拡縮を逆変換した座標の取得，再セット
        mapped[0] = event.getX();
        mapped[1] = event.getY();
        invScaler.mapPoints(mapped);
        event.setLocation(mapped[0], mapped[1]);

        //領域外タッチを除外
        if (!(new RectF(0,0,VIEW_WIDTH,VIEW_HEIGHT).contains(mapped[0],mapped[1]))){
            if (wasOutside){
                if (actionID == MotionEvent.ACTION_CANCEL || actionID == MotionEvent.ACTION_UP
                        || actionID == MotionEvent.ACTION_OUTSIDE){
                    wasOutside = false;
                }
                return true;
            }else {
                event.setAction(MotionEvent.ACTION_OUTSIDE);
                if (!(actionID == MotionEvent.ACTION_CANCEL || actionID == MotionEvent.ACTION_UP
                        || actionID == MotionEvent.ACTION_OUTSIDE)){
                    wasOutside = true;
                }
            }
        }

        onTouchListener.onTouch(this, event);
        return true;
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        //スケール
        setScale();
        //フルスクリーン
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.KITKAT)
        setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY | SYSTEM_UI_FLAG_FULLSCREEN
                | SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Canvas canvas = holder.lockCanvas();
                doDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        },100,INTERVAL,TimeUnit.NANOSECONDS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //スケール更新
        setScale();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        scheduler.shutdown();
        holder.removeCallback(this);
        fpswatch = null;
    }

    public void setScale(){
        //拡大率を取得，小さいほうに合わせる
        float scaleX = (float)getWidth() / (float)VIEW_WIDTH;
        float scaleY = ((float)getHeight()) /  (float)VIEW_HEIGHT;
        scale = scaleX > scaleY ? scaleY : scaleX;
        //scale = scaleX;

        //拡縮して中央にセット
        scaler = new Matrix();
        scaler.postScale(scale,scale);
        scaler.postTranslate((getWidth()-VIEW_WIDTH*scale)/2.0f, (getHeight()-VIEW_HEIGHT*scale)/2.0f);
        //タッチ座標用の逆変換も作成
        invScaler = new Matrix();
        invScaler.postTranslate(-(getWidth()-VIEW_WIDTH*scale)/2.0f, -(getHeight()-VIEW_HEIGHT*scale)/2.0f);
        invScaler.postScale(1.0f/scale, 1.0f/scale);
    }
}
