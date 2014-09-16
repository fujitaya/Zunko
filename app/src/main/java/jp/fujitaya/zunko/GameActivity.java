package jp.fujitaya.zunko;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jp.fujitaya.zunko.jimmy.OnDraw;
import jp.fujitaya.zunko.util.FpsCounter;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;
import jp.fujitaya.zunko.util.Sound;


public class GameActivity extends Activity implements View.OnTouchListener, OnDraw{
    //描画範囲指定
    //この範囲で描画し，画面解像度と異なる場合は拡大される
    //本来はResourcesあたりに書いておくべき
    public  static final int VIEW_WIDTH = 720;
    public  static final int VIEW_HEIGHT = 1280;
    //FPSの指定
    //やっぱり本来はResourcesに書きたい
    public static final int FPS = 30;
    public static final long INTERVAL = (long)(Math.floor(
            (double) TimeUnit.SECONDS.toNanos(1L) / (double)FPS));

    GameView gameView;
    private ScheduledExecutorService scheduler;
    private FpsCounter fpswatch;
    protected GameScene scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Sound.getInstance().setContext(getApplicationContext());

        scheduler = null;
        fpswatch = new FpsCounter("game");
        scene = new SceneMenu(this);

        gameView = new GameView(this, this, this);
        setContentView(gameView);

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                update();
            }
        },100,INTERVAL,TimeUnit.NANOSECONDS);
    }

    private void update(){
        fpswatch.update();
        synchronized (scene) {
            scene.update();
        }
    }

    public void draw(Canvas canvas){
        synchronized (scene) {
            scene.draw(canvas);
        }
    }

    public void changeScene(GameScene next){
        synchronized (scene) {
            if (scene != null) scene.dispose();
            scene = next;
        }
        if(scene == null) this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onPause(){
        super.onPause();
        Sound.getInstance().release();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        scene.interrupt(motionEvent);
        return true;
    }
}
