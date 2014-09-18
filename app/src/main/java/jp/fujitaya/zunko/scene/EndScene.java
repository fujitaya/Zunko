package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.field.Field;
import jp.fujitaya.zunko.field.FieldManager;
import jp.fujitaya.zunko.util.Image;
import jp.fujitaya.zunko.util.ImageLoader;

/**
 * Created by tetsu on 2014/09/18.
 */
public class EndScene extends GameScene{
    private FieldManager fm;
    private EndMessageWindowScene message;
    private Field field;

    private boolean[] messageflag;
    int randomMessageCount=1;
    int messageSpan=30*20;
    String[] randomMessage={
            "明らかに飛行できない形なのに","合体ロボのパーツは飛ぶ。飛ばねば",
            "「やったか？」はやってないし","「力が欲しいか」の力はろくでもない",
            "いいか！タイムトラベルしたら","その時代の人間とは接触するなよ！？",
            "全裸でも堂々としていれば","割と邪な気持ちにならない（通報はする)",
            "全国都道府県女子高生","スカート膝上ランキング、東北一位は宮城県"
    };

    public EndScene(GameView parent, String fieldName){
        super(parent);
        fm = FieldManager.getInstance();
        message = new EndMessageWindowScene(parent);


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

        field = fm.getField(fieldName);
    }

    @Override
    public void update(){
        fm.update();
        message.update();
        setMessage();

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
            y+=diffy*2;
            if(new RectF(x,y,x+dx*30,y+diffy).contains(event.getX(),event.getY())){
            return;
            }
        }

        field.interrupt(event);
    }
    @Override
    public void dispose(){
        field.dispose();
        message.dispose();
    }

    void setMessage(){
        if(randomMessageCount==1){
            message.appendMessage("ここはずんだが十分に広まっています");
            message.appendMessage("メニューからずん子を集め");
            message.appendMessage("他の場所にもずんだを広めましょう！");
        }
        setRandomMessage();
    }
    void setRandomMessage(){
        if(randomMessageCount%messageSpan==0){
            int rand=(int)(Math.random()*randomMessage.length/2);
            message.appendMessage(randomMessage[rand]);
            message.appendMessage(randomMessage[rand+1]);
        }
        randomMessageCount++;
    }
    void changeScene(){
        parent.changeScene(new SceneSelect(parent));
    }

}
