package jp.fujitaya.zunko.hayashima;

import android.graphics.PointF;
import android.view.Menu;
import android.view.MotionEvent;

import jp.fujitaya.zunko.util.GameView;

;
public class MainMessageWindowScene extends MessageWindowScene {

    static MenuState nowMenuState=MenuState.None;
    String fieldName;
    public MainMessageWindowScene(GameView parent,String name){
        super(parent);
        img.moveCollision(0,1024);
        nowMenuState=MenuState.None;
        fieldName=name;
    }
    public boolean isInterruptStatus(MotionEvent event,String mode){
        if(statusWindow==null)return false;
        if(mode=="Gather") {
            return ((MenuWindow) (statusWindow)).isOnSumAttackMode(new PointF(event.getX(), event.getY()));
        }
        else if(mode=="BackToMenu"){
            return ((MenuWindow) (statusWindow)).isOnBackToSelect(new PointF(event.getX(), event.getY()));
        }
        return false;
    }
    public MenuState getMenuInterrupt(MotionEvent event){
        pi.update(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //for Selecting Menu,need to check hit in menubar
                if(img.isInside(new PointF(pi.x, pi.y)) ){
                if (statusWindow == null) {// && img.isInside(new PointF(pi.x, pi.y))){
                    statusWindow = new MenuWindow(fieldName);
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
