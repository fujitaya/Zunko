package jp.fujitaya.zunko.hayashima;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;
import jp.fujitaya.zunko.util.Image;
import jp.fujitaya.zunko.util.Sound;

public class MessageWindowScene extends GameScene{
    public static enum ImageName{
        Z01, Z05, Z07, Z08, Z09, Z15, Z_V,
    };
    public static final int LINE_NUM = 5;

    private Paint msgPaint, framePaint;
    private ArrayList<String> msgs;
    private static final int FONT_SIZE = 25;
    private HashMap<ImageName, Integer> zunkoImage;
    private Image img, wnd;
    private boolean show;

    private StatusWindow statusWindow;

    private Image bg;
    public MessageWindowScene(GameView parent){
        super(parent);

        show = true;
        
        msgs = new ArrayList<String>();
        msgPaint = new Paint();
        msgPaint.setColor(Color.BLACK);
        msgPaint.setTextSize(FONT_SIZE);

        framePaint = new Paint();

        wnd = new Image(R.drawable.window);

        zunkoImage = new HashMap<ImageName, Integer>();
        zunkoImage.put(ImageName.Z01, R.drawable.zunko01);
        zunkoImage.put(ImageName.Z05, R.drawable.zunko05);
        zunkoImage.put(ImageName.Z07, R.drawable.zunko07);
        zunkoImage.put(ImageName.Z08, R.drawable.zunko08);
        zunkoImage.put(ImageName.Z09, R.drawable.zunko09);
        zunkoImage.put(ImageName.Z15, R.drawable.zunko15);
        zunkoImage.put(ImageName.Z_V, R.drawable.zunko_v);

        img = new Image(zunkoImage.get(ImageName.Z09));
        img.setCenter(128, 138);
        img.setScale(2, 2);

//        statusScene = new StatusWindow(parent);

        bg = new Image(R.drawable.map_miyagi);

        Sound sound = Sound.getInstance();
        Sound.SoundCard sc = sound.loadBGM(R.raw.title_theme_02);
        sound.playBGM(sc);
    }

    public void appendMessage(String msg){
        if(msgs.size() == LINE_NUM) msgs.remove(0);
        msgs.add(msg);
    }
    public void clearMessage(){
        msgs.clear();
    }
    public void changeImage(ImageName name){
        Integer imgId = zunkoImage.get(name);
        if(imgId != null) img.changeImage(imgId);
    }
    public void show(boolean show){
        this.show = show;
    }

    @Override
    public void dispose(){
        zunkoImage.clear();
        img = null;
        wnd = null;
    }
    @Override
    public void update(){
    }
    @Override
    public void interrupt(MotionEvent event){}

    Rect canvasRect = new Rect();
    @Override
    public void draw(Canvas canvas){
        if(!show) return;

        canvas.getClipBounds(canvasRect);

        int width = canvasRect.right;
        int height = canvasRect.bottom;
// float scaleX = (float)width / (float)TARGET_WIDTH;
// float scaleY = (float)height / (float)TARGET_HEIGHT;
// float scale = scaleX > scaleY ? scaleY : scaleX;
//
        int drawX = 0;
        int drawY = height - height/5;
// int baseX = (int)(0 + (float)drawX * scale);
// int baseY = (int)(0 + (float)drawY * scale);
        int baseX = drawX;
        int baseY = drawY;

        bg.draw(canvas);

        wnd.draw(canvas, baseX+(width-wnd.getWidth())/2, baseY);
        img.draw(canvas, baseX, baseY);

        int msgX = (int)img.getWidth() + (int)img.getX();
        int msgY = baseY + FONT_SIZE*2;
        int diffY = FONT_SIZE * 3 / 2;
        for(int i=0; i < msgs.size(); ++i){
            canvas.drawText(msgs.get(i), msgX, msgY + i*diffY, msgPaint);
        }
    }
}