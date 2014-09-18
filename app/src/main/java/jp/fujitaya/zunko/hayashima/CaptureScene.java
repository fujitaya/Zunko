package jp.fujitaya.zunko.hayashima;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.jimmy.FieldManager;
import jp.fujitaya.zunko.jimmy.SceneSelect;
import jp.fujitaya.zunko.sugaya.Field;
import jp.fujitaya.zunko.util.GameScene;
import jp.fujitaya.zunko.util.GameView;
import jp.fujitaya.zunko.util.ImageLoader;

public class CaptureScene extends GameScene {
    public enum PlayerOperation{
        SELECT,
    }

    private MessageWindowScene message;
    private CaptureField field;

    public CaptureScene(GameView parent, String fieldName){
        super(parent);
        message = new MessageWindowScene(parent);

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

//        field = FieldManager.getInstance().getField(fieldName);
//        field.init();
        field = new CaptureField(fieldName);

    };

    @Override
    public void update(){
        field.update();
        message.update();
    }
    @Override
    public void draw(Canvas canvas){
        field.draw(canvas);
        message.draw(canvas);
    }

    @Override
    public void interrupt(MotionEvent event) {
        message.interrupt(event);
        field.interrupt(event);
    }
    @Override
    public void dispose(){
        field.dispose();
    }

    void ChangeScene(){
        parent.changeScene(new SceneSelect(parent));
    }
}
