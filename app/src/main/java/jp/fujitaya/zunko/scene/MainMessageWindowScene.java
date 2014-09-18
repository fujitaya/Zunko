package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

;import jp.fujitaya.zunko.GameView;

public class MainMessageWindowScene extends MessageWindowScene {

    static MenuState nowMenuState=MenuState.None;
    public MainMessageWindowScene(GameView parent){
        super(parent);
        img.moveCollision(0,1024);
        nowMenuState=MenuState.None;
    }
    public MenuState getMenuInterrupt(MotionEvent event){
        pi.update(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //for Selecting Menu,need to check hit in menubar
                if(img.isInside(new PointF(pi.x, pi.y)) ){
                if (statusWindow == null) {// && img.isInside(new PointF(pi.x, pi.y))){
                    statusWindow = new MenuWindow();
                    nowMenuState=MenuState.On;
                    return MenuState.On;
                } else if (statusWindow != null) {
                    //statusWindow.interrupt(event);
                    statusWindow = null;
                    nowMenuState= MenuState.None;
                    return MenuState.Off;
                }
            }
                break;
            default: break;
        }
        if(nowMenuState==MenuState.On){
            return MenuState.On;
        }
        return MenuState.None;
    }
}
