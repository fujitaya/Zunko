package jp.fujitaya.zunko;

import android.content.Context;

import jp.fujitaya.zunko.hayashima.MessageWindowScene;
import jp.fujitaya.zunko.hayashima.TitleScene;
import jp.fujitaya.zunko.util.GameView;

public class GameViewFront extends GameView {
    public GameViewFront(Context context){
        super(context);
        scene = new TitleScene(this);
    }
}
