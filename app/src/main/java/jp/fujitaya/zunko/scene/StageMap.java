package jp.fujitaya.zunko.scene;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.text.NumberFormat;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.Field;
import jp.fujitaya.zunko.field.FieldManager;
import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.util.InsideRectF;
import jp.fujitaya.zunko.util.InsideStrategy;
import jp.fujitaya.zunko.util.TouchableBitmap;
import jp.fujitaya.zunko.util.TouchableBitmapWithText;

import static android.view.GestureDetector.OnGestureListener;

public class StageMap implements OnGestureListener{
    public final RectF drawRectSendai = new RectF(600f,800f,850f,1050f);
    public final RectF stageDetailSize = new RectF(0f,0f,400f,400f);
    public final RectF changeSceneButtonDef = new RectF(50f,280f,350f,380f);
    public final Rect cutMiyagi = new Rect(1200,1900,2000,2800);

    protected Bitmap backGround;
    protected Map<String, TouchableBitmap> fieldButtons;
    protected StageGroup group;
    //本当はコールバックに変えたい
    protected GameView parentView;
    protected RectF backGroundDst;
    protected PointF offset;
    protected FieldManager fieldManager;
    //フィールド詳細ウィンドウ関連
    //本来は1クラスにまとめたい
    protected NoticeWindow stageDetail;
    protected String focus;
    protected TouchableBitmap changeSceneButton;
    protected Bitmap changeSceneImage;
    public  final float scale = 2.0f;

    public StageMap(StageGroup group, Resources res, GameView parentView){
        init(group,res,parentView);
    }

    private void init(StageGroup group, Resources res, final GameView parentView){
        this.group = group;
        fieldButtons = new HashMap<String, TouchableBitmap>();
        this.parentView = parentView;
        this.fieldManager = FieldManager.getInstance();
        this.changeSceneImage = BitmapFactory.decodeResource(res,R.drawable.btn_gostage);

        switch (group){
            case Miyagi:
                //Bitmap tmp1;
                //tmp1 = BitmapFactory.decodeResource(res, R.drawable.bg_tohoku);
                //backGround = Bitmap.createBitmap(tmp1,1200,1900,800,900);
                //tmp1.recycle();
                //tmp1 = null;
                backGround = getClippedImage(res,R.drawable.bg_tohoku,cutMiyagi);
                offset = new PointF(-400,-200);
                backGroundDst = new RectF(offset.x,
                        offset.y,
                        offset.x+backGround.getWidth()*scale,
                        offset.y+backGround.getHeight()*scale);

                addFieldButton(
                        "Sendai",new TouchableBitmapWithText(
                            BitmapFactory.decodeResource(res, R.drawable.mc_mig),
                            new RectF(drawRectSendai),
                            new InsideRectF(new RectF(drawRectSendai)),
                            res.getString(R.string.sendai),
                            60,
                            70f,
                            200f,
                            Color.WHITE,
                            Color.BLACK,
                            200,
                            new OnGestureListener() {
                                @Override
                                public boolean onDown(MotionEvent motionEvent) {
                                return false;
                            }
                                @Override
                                public void onShowPress(MotionEvent motionEvent) { }
                                @Override
                                public boolean onSingleTapUp(MotionEvent motionEvent) {
                                    openStageDetail("Sendai");
                                    return false;
                                }
                                @Override
                                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                                    return false;
                                }
                                @Override
                                public void onLongPress(MotionEvent motionEvent) { }
                                @Override
                                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                                    return false;
                                }
                            }
                ));
                break;
            case Tohoku:
                backGround = BitmapFactory.decodeResource(res, R.drawable.map_miyagi);
                break;
            case World:
                backGround = BitmapFactory.decodeResource(res, R.drawable.map_miyagi);
                break;
            default:
                break;
        }
    }
    public void update(){
        if (stageDetail != null){
            if (fieldManager.isWorking(focus)){
                Field focusField = fieldManager.getField(focus);

                String message = "進行中" +
                        "\n総パワー : " + Integer.toString(focusField.getTotalZunkoNum()) +
                        "\n攻略度 :" + NumberFormat.getPercentInstance().format(
                        1.0-((double)focusField.getNowHP()/focusField.getInitialHP()));
                stageDetail.setText(message);
            }
            else {
                stageDetail.setText("未開拓エリア");
            }
        }
    }

    public void openStageDetail(String fieldName){
        closeStageDetail();
        if (fieldName == "Sendai"){
            setFocus("Sendai");

            RectF windowRect = new RectF(stageDetailSize);
            windowRect.offset(offset.x, offset.y);
            windowRect.offset(drawRectSendai.left,drawRectSendai.top);
            stageDetail = new NoticeWindow(windowRect,"", Color.BLUE,150,Color.WHITE);

            RectF buttonRect = new RectF(changeSceneButtonDef);
            buttonRect.offset(stageDetail.getRect().left,stageDetail.getRect().top);
            changeSceneButton = new TouchableBitmap(
                    changeSceneImage,
                    buttonRect,
                    new OnGestureListener() {
                        @Override
                        public boolean onDown(MotionEvent motionEvent) {
                            return true;
                        }
                        @Override
                        public void onShowPress(MotionEvent motionEvent) {
                        }
                        @Override
                        public boolean onSingleTapUp(MotionEvent motionEvent) {
                            //parentView.changeScene(new MainScene(parentView,"Sendai"));
                            if(FieldManager.getInstance().getField("Sendai").getNowHP()!=0){
                                parentView.changeScene(new CaptureScene(parentView,"Sendai"));
                            }
                            else{
                                parentView.changeScene(new EndScene(parentView,"Sendai"));
                            }
                            //parentView.changeScene(new CaptureScene(parentView,"Sendai"));
                            return false;
                        }
                        @Override
                        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                            return true;
                        }
                        @Override
                        public void onLongPress(MotionEvent motionEvent) {
                        }
                        @Override
                        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
                            return true;
                        }
                    }
            );
        }
    }
    public void closeStageDetail(){
        if(stageDetail != null) {
            stageDetail.dispose();
            //changeSceneButton.dispose();
            //ボタン画像を再利用するため，dispose禁止
        }
        stageDetail = null;
        changeSceneButton = null;
    }

    private Bitmap getClippedImage(Resources res, int id, Rect region){
        try {
            InputStream stream = res.openRawResource(id);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(stream, true);
            return decoder.decodeRegion(region,null);
        }catch (IOException e){

        }
        return null;
    }

    public void setFocus(String fieldName){
        this.focus = fieldName;
    }

    public void addFieldButton(String id, TouchableBitmap button){
        button.move(offset.x, offset.y);
        fieldButtons.put(id,button);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(backGround,null,backGroundDst,null);

        for (Map.Entry<String,TouchableBitmap> entry : fieldButtons.entrySet()){
            entry.getValue().draw(canvas);
        }
        if (stageDetail != null){
            stageDetail.draw(canvas);
            changeSceneButton.draw(canvas);
        }
    }

    private void move(float distanceX, float distanceY){
        float moveX=0f,moveY=0f;
        if (offset.x - distanceX + backGround.getWidth()*scale > GameView.VIEW_WIDTH &&
                offset.x - distanceX < 0)
            moveX = distanceX;
        if (offset.y - distanceY + backGround.getHeight()*scale > GameView.VIEW_HEIGHT &&
                offset.y - distanceY < 0)
            moveY = distanceY;

        if (moveX != 0f || moveY != 0f){
            offset.x -= moveX;
            offset.y -= moveY;
            for (Map.Entry<String,TouchableBitmap> entry : fieldButtons.entrySet())
                entry.getValue().move(-moveX,-moveY);

            backGroundDst = new RectF(offset.x,
                    offset.y,
                    offset.x+backGround.getWidth()*scale,
                    offset.y+backGround.getHeight()*scale);
            if (stageDetail != null){
                stageDetail.move(-moveX,-moveY);
                changeSceneButton.move(-moveX,-moveY);
            }
        }
    }
    public void dispose(){
        if (backGround != null){
            backGround.recycle();
            backGround = null;
        }
        for (Map.Entry<String,TouchableBitmap> entry : fieldButtons.entrySet()){
            entry.getValue().dispose();
        }
        fieldButtons.clear();
        changeSceneImage.recycle();
        parentView = null;
    }

    public void changeGroup(StageGroup group, Resources res, GameView parentView){
        dispose();
        init(group, res, parentView);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if (stageDetail != null){
            return true;
        }

        for (Map.Entry<String,TouchableBitmap> entry : fieldButtons.entrySet()){
            if (entry.getValue().isInside(new PointF(x, y))){
                entry.getValue().getGestureListener().onDown(e);
                return true;
            }
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if (stageDetail != null){
            return;
        }

        for (Map.Entry<String,TouchableBitmap> entry : fieldButtons.entrySet()){
            if (entry.getValue().isInside(new PointF(x, y))){
                entry.getValue().getGestureListener().onShowPress(e);
                return;
            }
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        if (stageDetail != null){
            if (changeSceneButton.isInside(new PointF(x,y))){
                changeSceneButton.getGestureListener().onSingleTapUp(e);
            } else {
                closeStageDetail();
            }
            return true;
        }

        for (Map.Entry<String,TouchableBitmap> entry : fieldButtons.entrySet()){
            //Memo:isInside判定は呼び出し先で判定すればいいのではなかろうか…
            if (entry.getValue().isInside(new PointF(x, y))){
                entry.getValue().getGestureListener().onSingleTapUp(e);
                return false;
            }
        }
        return false;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
        move(distanceX,distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        for (Map.Entry<String,TouchableBitmap> entry : fieldButtons.entrySet()){
            if (entry.getValue().isInside(new PointF(x, y))){
                entry.getValue().getGestureListener().onLongPress(e);
                return;
            }
        }
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }
}
