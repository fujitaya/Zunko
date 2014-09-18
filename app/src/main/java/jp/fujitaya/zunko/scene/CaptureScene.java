package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.FieldManager;
import jp.fujitaya.zunko.field.Field;
import jp.fujitaya.zunko.util.ImageLoader;

public class CaptureScene extends GameScene {
    private FieldManager fm;
    private CaptureMessageWindowScene message;
    private Field field;
    private int clearCount=0;
    private boolean[] messageflag;

    public CaptureScene(GameView parent, String fieldName){
        super(parent);
        fm = FieldManager.getInstance();
        message = new CaptureMessageWindowScene(parent);
        messageflag=new boolean[5];
        for(boolean f:messageflag)f=false;


        ImageLoader ld = ImageLoader.getInstance();
        ld.load(R.drawable.cz_tatsu);
        ld.load(R.drawable.cz_tatsu_s);
        ld.load(R.drawable.cz_aruku01);
        ld.load(R.drawable.cz_aruku01_s);
        ld.createHrevImage(R.drawable.cz_aruku01, R.drawable.cz_aruku01_r);
        ld.createHrevImage(R.drawable.cz_aruku01_s, R.drawable.cz_aruku01_s_r);
        ld.load(R.drawable.cz_aruku02);
        ld.load(R.drawable.cz_aruku02_s);
        ld.createHrevImage(R.drawable.cz_aruku02, R.drawable.cz_aruku02_r);
        ld.createHrevImage(R.drawable.cz_aruku02_s, R.drawable.cz_aruku02_s_r);
        ld.load(R.drawable.cz_miwatasu01);
        ld.load(R.drawable.cz_miwatasu02);
        ld.load(R.drawable.cz_mochi01);
        ld.load(R.drawable.cz_mochi02);
        ld.load(R.drawable.cz_mochi03);
        ld.load(R.drawable.cz_mochi04);
        ld.load(R.drawable.cz_mochi05);
        ld.createHrevImage(R.drawable.cz_mochi01, R.drawable.cz_mochi01_r);
        ld.createHrevImage(R.drawable.cz_mochi02, R.drawable.cz_mochi02_r);
        ld.createHrevImage(R.drawable.cz_mochi03, R.drawable.cz_mochi03_r);
        ld.createHrevImage(R.drawable.cz_mochi04, R.drawable.cz_mochi04_r);
        ld.createHrevImage(R.drawable.cz_mochi05, R.drawable.cz_mochi05_r);
        ld.load(R.drawable.ef_kemuri1);
        ld.load(R.drawable.ef_kemuri2);
        ld.load(R.drawable.ef_kemuri3);
        ld.load(R.drawable.ef_hikari1);
        ld.load(R.drawable.ef_hikari2);
        ld.load(R.drawable.ef_hikari3);

        field = fm.getField(fieldName);
    }

    @Override
    public void update(){
        fm.update();
        message.update();
        stageClear();
        setMessage();
        if(field.getNowHP()<=0)clearCount++;
    }
    @Override
    public void draw(Canvas canvas){
        field.draw(canvas);
        message.draw(canvas);
    }

    @Override
    public void interrupt(MotionEvent event) {
        message.interrupt(event);
        if(message.getMenuState()==MenuState.On){

            float dx = (720 - 500) / 2;
            float dy = (1280 - 600) / 2;
            float diffy = 35;
            float x = dx + 30;
            float y = dy + diffy*4;
            if(new RectF(x,y,x+dx*30,y+diffy).contains(event.getX(),event.getY())){
                changeScene();
                return;
            }
        }

        field.interrupt(event);
    }
    @Override
    public void dispose(){
        field.dispose();
    }
    void stageClear(){
        if(field.getNowHP()<=0){
            if(clearCount==60) {
                    changeScene();
                }
        }
    }
    void setMessage(){
        //clear message
        if(field.getNowHP()<=0&&clearCount==0){
            message.appendMessage("ずんだ,広まりましたっ！！");
        }
        else if(field.getNowHP()*3<=field.getInitialHP()*2 &&messageflag[0]==false){
            message.appendMessage("ずんだが注目されています");
            messageflag[0]=true;
        }
        else if(field.getNowHP()*2<=field.getInitialHP() &&messageflag[1]==false){
            message.appendMessage("あと半分、がんばれがんばれ");
            messageflag[1]=true;
        }
        else if(field.getNowHP()*4<=field.getInitialHP() &&messageflag[2]==false){
            message.appendMessage("");
            messageflag[2]=true;
        }
    }


    void changeScene(){
        parent.changeScene(new SceneSelect(parent));
    }
}
