package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;
import jp.fujitaya.zunko.util.SpriteNodeImage;

public class MessageWindowScene extends GameScene{
    public static enum ImageName{
        Z01, Z05, Z07, Z08, Z09, Z15, Z_V,
    };
    public static final int LINE_NUM = 5;

    private Paint msgPaint, framePaint;
    private ArrayList<String> msgs;
    private static final int FONT_SIZE = 25;

    private HashMap<ImageName, Bitmap> zunkoImage;
    private SpriteNodeImage img, bg, wnd;
    public MessageWindowScene(GameView parent){
        super(parent);
        msgs = new ArrayList<String>();
        msgPaint = new Paint();
        msgPaint.setColor(Color.BLACK);
        msgPaint.setTextSize(FONT_SIZE);
        framePaint = new Paint();

        wnd = new SpriteNodeImage(BitmapFactory.decodeResource(parent.getResources(), R.drawable.window));

        bg = new SpriteNodeImage(BitmapFactory.decodeResource(parent.getResources(), R.drawable.map_tohoku_layered_x2));
        bg.moveTo(-872, -90);

        zunkoImage = new HashMap<ImageName, Bitmap>();
        zunkoImage.put(ImageName.Z01, (BitmapFactory.decodeResource(parent.getResources(), R.drawable.zunko01)));
        zunkoImage.put(ImageName.Z05, (BitmapFactory.decodeResource(parent.getResources(), R.drawable.zunko05)));
        zunkoImage.put(ImageName.Z07, (BitmapFactory.decodeResource(parent.getResources(), R.drawable.zunko07)));
        zunkoImage.put(ImageName.Z08, (BitmapFactory.decodeResource(parent.getResources(), R.drawable.zunko08)));
        zunkoImage.put(ImageName.Z09, (BitmapFactory.decodeResource(parent.getResources(), R.drawable.zunko09)));
        zunkoImage.put(ImageName.Z15, (BitmapFactory.decodeResource(parent.getResources(), R.drawable.zunko15)));
        zunkoImage.put(ImageName.Z_V, (BitmapFactory.decodeResource(parent.getResources(), R.drawable.zunko_v)));

        img = new SpriteNodeImage(null);
        img.changeImage(zunkoImage.get(ImageName.Z09));
        img.moveTo(-128, -60);
//        img.moveTo(-175, -175);   // hdpi?

//        Typeface typeFace = Typeface.createFromAsset(parent.getContext().getAssets(), "myfont.ttf");
//        msgPaint.setTypeface(Typeface.);

    }

    public void appendMessage(String msg){
        if(msgs.size() == LINE_NUM) msgs.remove(0);
        msgs.add(msg);
    }
    public void clearMessage(){
        msgs.clear();
    }
    public void changeImage(ImageName name){
        Bitmap image = zunkoImage.get(name);
        if(image != null) img.changeImage(image);
    }

    @Override
    public void dispose(){}

    int counter = 0;
    int msgCounter = 0;
    @Override
    public void update(){
        ++counter;
        if(counter%60 == 0){
            if(msgCounter == 20){
                clearMessage();
                changeImage(ImageName.Z08);
                msgCounter = 0;
            }
            ++msgCounter;
            appendMessage(String.format("%2d番目のメッセージです。", msgCounter));
        }
    }

    boolean eventOn = false;
    float ex, ey;
    float th = 10f;
    @Override
    public void interrupt(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                eventOn = true;
                ex = event.getX();
                ey = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - ex;
                float dy = event.getY() - ey;
                if(dx < -th){
                    img.moveOffset(-1, 0);
                    ex = event.getX();
                }else if(dx > th){
                    img.moveOffset(1, 0);
                    ex = event.getX();
                }
                if(dy < -th){
                    img.moveOffset(0, -1);
                    ey = event.getY();
                }else if(dy > th){
                    img.moveOffset(0, 1);
                    ey = event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                eventOn = false;
                break;
            default: break;
        }
    }

    @Override
    public void draw(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();

//        float scaleX = (float)width / (float)TARGET_WIDTH;
//        float scaleY = (float)height /  (float)TARGET_HEIGHT;
//        float scale = scaleX > scaleY ? scaleY : scaleX;
//
        int drawX = 0;
        int drawY = height - height/5;

//        int baseX = (int)(0 + (float)drawX * scale);
//        int baseY = (int)(0 + (float)drawY * scale);
        int baseX = drawX;
        int baseY = drawY;

        drawBg(canvas, 0, 0);

        wnd.draw(canvas, baseX+(width-wnd.getWidth())/2, baseY, 1, 1, 0);

        img.draw(canvas, baseX, baseY, 1, 1, 0);

        int msgX = img.getWidth() + img.getX();
        int msgY = baseY + FONT_SIZE*2;
        int diffY = FONT_SIZE * 3 / 2;
        for(int i=0; i < msgs.size(); ++i){
            canvas.drawText(msgs.get(i), msgX, msgY + i*diffY, msgPaint);
        }
    }
    private void drawBg(Canvas canvas, int bx, int by){
        bg.draw(canvas, bx, by, 1, 1, 0);
    }
}
