package jp.fujitaya.zunko;

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

import jp.fujitaya.zunko.scene.CaptureScene;
import jp.fujitaya.zunko.scene.GameScene;
import jp.fujitaya.zunko.scene.SceneMenu;
import jp.fujitaya.zunko.scene.SceneSelect;
import jp.fujitaya.zunko.util.FpsCounter;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    //描画範囲指定
    //この範囲で描画し，画面解像度と異なる場合は拡大される
    //本来はResourcesあたりに書いておくべき
    public  static final int VIEW_WIDTH = 720;
    public  static final int VIEW_HEIGHT = 1280;

    //FPSの指定
    //やっぱり本来はResourcesに書きたい
    public static final int FPS = 30;
    public static final long INTERVAL = (long)(Math.floor(
            (double)TimeUnit.SECONDS.toNanos(1L) / (double)FPS));

    protected ScheduledExecutorService scheduler;
    protected FpsCounter fpswatch;
    protected GameScene scene;
    protected float scale;
    protected Matrix scaler;
    protected Matrix invScaler;
    protected boolean wasOutside;
    protected boolean drawFlag;

    public GameView(Context context){
        super(context);
        wasOutside = false;
        scheduler = null;
        scene = new SceneMenu(this);
//        scene = new CaptureScene(this, "Sendai");
        getHolder().addCallback(this);
    }

    private void update(){
        fpswatch.update();
        synchronized (scene) {
            if (scene == null) {
                ((MyActivity) getContext()).finish();
                return;
            }
            scene.update();
        }
    }
    private void doDraw(Canvas canvas){
        RectF mappedArea;

        //全範囲クリア
        canvas.drawColor(Color.BLACK);
        //拡縮指定
        canvas.setMatrix(scaler);
        //描画範囲限定
        canvas.clipRect(new Rect(0,0,VIEW_WIDTH,VIEW_HEIGHT));
        //描画範囲クリア
        //drawColorだと左側余白も描画される模様
        canvas.drawColor(Color.WHITE);

        synchronized (scene) {
            if(scene != null) {
                scene.draw(canvas);
            };
        }
    }

    public void setScale(){
        //拡大率を取得，小さいほうに合わせる
        float scaleX = (float)getWidth() / (float)VIEW_WIDTH;
        float scaleY = ((float)getHeight()) /  (float)VIEW_HEIGHT;
        scale = scaleX > scaleY ? scaleY : scaleX;
//        scale = scaleX;

        //拡縮して中央にセット
        scaler = new Matrix();
        scaler.postScale(scale,scale);
        scaler.postTranslate((getWidth()-VIEW_WIDTH*scale)/2.0f, (getHeight()-VIEW_HEIGHT*scale)/2.0f);
        //タッチ座標用の逆変換も作成
        invScaler = new Matrix();
        invScaler.postTranslate(-(getWidth()-VIEW_WIDTH*scale)/2.0f, -(getHeight()-VIEW_HEIGHT*scale)/2.0f);
        invScaler.postScale(1.0f / scale, 1.0f / scale);
    }

    public void changeScene(GameScene next){
        synchronized (scene) {
            if (scene != null) {
                scene.dispose();
                scene.setParent(null);
            }
            scene = next;
        }
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

        synchronized (scene) {
            scene.interrupt(event);
        }
        return true;
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        //スケール
        setScale();

        fpswatch = new FpsCounter();
        drawFlag = true;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();

                if (drawFlag == true) {
                    Canvas canvas = holder.lockCanvas();
                    doDraw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        },100,INTERVAL,TimeUnit.NANOSECONDS);

        setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                setFullScreen();
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //スケール更新
        setScale();
        //フルスクリーン
        setFullScreen();
    }

    public void setFullScreen(){
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.KITKAT)
            setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | SYSTEM_UI_FLAG_FULLSCREEN
                    | SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | SYSTEM_UI_FLAG_LAYOUT_STABLE );
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        fpswatch = null;
        drawFlag = false;
    }

    public void onDestroy(){
        scene.dispose();
        scheduler.shutdown();
        scene = null;
    }
}
