package jp.fujitaya.zunko.hayashima;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.jimmy.InsideRectF;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;
import jp.fujitaya.zunko.util.Image;
import jp.fujitaya.zunko.util.PointerInfo;
import jp.fujitaya.zunko.util.Sound;

public class MessageWindowScene extends GameScene{
    public static enum ImageName{
        Z01, Z05, Z07, Z08, Z09, Z15, Z_V,
    };
    public static final int LINE_NUM = 5;

    protected Paint msgPaint, framePaint;
    protected ArrayList<String> msgs;
    protected static final int FONT_SIZE = 25;
    protected HashMap<ImageName, Integer> zunkoImage;
    protected Image img, wnd;
    protected boolean show;

    protected StatusWindow statusWindow;

    public MessageWindowScene(GameView parent){
        super(parent);

        show = true;
        
        msgs = new ArrayList<String>();
        msgPaint = new Paint();
        msgPaint.setColor(Color.BLACK);
        msgPaint.setTextSize(FONT_SIZE);

        framePaint = new Paint();

//        wnd = new Image(R.drawable.wnd_message);

        zunkoImage = new HashMap<ImageName, Integer>();
        zunkoImage.put(ImageName.Z01, R.drawable.tz_zunko01);
        zunkoImage.put(ImageName.Z05, R.drawable.tz_zunko05);
        zunkoImage.put(ImageName.Z07, R.drawable.tz_zunko07);
        zunkoImage.put(ImageName.Z08, R.drawable.tz_zunko08);
        zunkoImage.put(ImageName.Z09, R.drawable.tz_zunko09);
        zunkoImage.put(ImageName.Z15, R.drawable.tz_zunko15);
        zunkoImage.put(ImageName.Z_V, R.drawable.tz_zunko_v);

        img = new Image(zunkoImage.get(ImageName.Z09));
        img.setCenter(128, 138);
        img.setCollision(new InsideRectF(new RectF(
                0, 0, img.getWidth(), img.getHeight())));
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

    PointerInfo pi = new PointerInfo();
    @Override
    public void interrupt(MotionEvent event){
        /*
        pi.update(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(statusWindow==null){// && img.isInside(new PointF(pi.x, pi.y))){
                    statusWindow = new StatusWindow();
                }else if(statusWindow != null){
//                    statusWindow.interrupt(event);
                    statusWindow = null;
                }
                break;
            default: break;
        }
        */
    }

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

        int drawX = 0;
        int drawY = height - height/5;
// int baseX = (int)(0 + (float)drawX * scale);
// int baseY = (int)(0 + (float)drawY * scale);
        int baseX = drawX;
        int baseY = drawY;

//        wnd.draw(canvas, baseX+(width-wnd.getWidth())/2, baseY);
        img.moveTo(baseX, baseY);
        img.draw(canvas);

        int msgX = (int)img.getWidth() + (int)img.getX();
        int msgY = baseY + FONT_SIZE*2;
        int diffY = FONT_SIZE * 3 / 2;
        for(int i=0; i < msgs.size(); ++i){
            canvas.drawText(msgs.get(i), msgX, msgY + i*diffY, msgPaint);
        }

        if(statusWindow != null) statusWindow.draw(canvas, 0, 0);
    }
}