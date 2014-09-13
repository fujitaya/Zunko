package jp.fujitaya.zunko.hayashima;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.GameScene;

public class MessageWindowScene extends GameScene{
    public static final int LINE_NUM = 4;

    SpriteNodeImage img;
    private Paint p;

    private ArrayList<String> msgs;

    public MessageWindowScene(Context viewContext){
        super(viewContext);

        img = new SpriteNodeImage(BitmapFactory.decodeResource(viewContext.getResources(), R.drawable.zunko09));
        p = new Paint();

        img.moveTo(-230, -150);
//        img.moveTo(-128, -300);   // bitmap no naibu de scale dekite nai node -128 de shiri ni awanai (720p nara tabun au)

        msgs = new ArrayList<String>();
        msgs.add(viewContext.getResources().getString(R.string.msg_test1));
        msgs.add(viewContext.getResources().getString(R.string.msg_test2));
        msgs.add(viewContext.getResources().getString(R.string.msg_test3));
        msgs.add(viewContext.getResources().getString(R.string.msg_test4));
    }
    @Override
    public void update(){
    }

    private void drawArea(Canvas canvas){
        canvas.drawColor(Color.GRAY);
        p.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, TARGET_WIDTH, TARGET_HEIGHT, p);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        float scaleX = (float)width / (float)TARGET_WIDTH;
        float scaleY = (float)height /  (float)TARGET_HEIGHT;
        float scale = scaleX > scaleY ? scaleY : scaleX;

        int drawX = 0;
        int drawY = TARGET_HEIGHT - TARGET_HEIGHT/5;

        int baseX = (int)(0 + (float)drawX * scale);
        int baseY = (int)(0 + (float)drawY * scale);

        p.setColor(Color.GREEN);
        canvas.drawRect(baseX, baseY, width, height, p);

        p.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, TARGET_WIDTH, TARGET_HEIGHT, p);

        if(!drawflag) {
            Log.d("HsLog", String.format("Canvas TW:%d TH:%d", TARGET_WIDTH, TARGET_HEIGHT));
            Log.d("HsLog", String.format("Canvas W:%d H:%d Sc:%f", width, height, scale));
            Log.d("HsLog", String.format("Canvas baseX:%d baseY:%d", baseX, baseY));
            drawflag = true;
        }
    }

    private boolean drawflag = false;
    private static final int TARGET_WIDTH = 720;
    private static final int TARGET_HEIGHT = 1280;
    @Override
    public void draw(Canvas canvas){
        drawArea(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        float scaleX = (float)width / (float)TARGET_WIDTH;
        float scaleY = (float)height /  (float)TARGET_HEIGHT;
        float scale = scaleX > scaleY ? scaleY : scaleX;

        int drawX = 0;
        int drawY = TARGET_HEIGHT - TARGET_HEIGHT/5;

        int baseX = (int)(0 + (float)drawX * scale);
        int baseY = (int)(0 + (float)drawY * scale);

        p.setColor(Color.RED);
        canvas.drawCircle(baseX, baseY, 100*scale, p);

        int frameSize = 5;
        p.setColor(Color.BLACK);
        canvas.drawRect(baseX+frameSize, baseY, width-frameSize, height-frameSize*2, p);

        p.setColor(Color.rgb(177, 242, 203));
        canvas.drawRect(baseX+frameSize*2, baseY+frameSize, width-frameSize*2, height-frameSize*3, p);

        img.draw(canvas, baseX, baseY, 1, 1, 0);

        p.setColor(Color.BLACK);
        p.setTextSize(40);
        int msgX = img.getWidth() + img.getX() - 60;
        int msgY = baseY + frameSize + 40/2 + 40;
        int diffY = 50;
        for(int i=0; i < LINE_NUM; ++i){
            canvas.drawText(msgs.get(i), msgX, msgY + i*diffY, p);
        }
    }
    @Override
    public void dispose(){

    }
    @Override
    public void interrupt(MotionEvent event){

    }
}
