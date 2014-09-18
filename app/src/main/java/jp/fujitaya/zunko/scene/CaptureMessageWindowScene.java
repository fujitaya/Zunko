package jp.fujitaya.zunko.scene;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import jp.fujitaya.zunko.GameView;
import jp.fujitaya.zunko.R;
import jp.fujitaya.zunko.util.Image;

/**
 * Created by tetsu on 2014/09/18.
 */
public class CaptureMessageWindowScene extends MessageWindowScene {

    protected Image clearImg;
    MenuState menuState=MenuState.None;
    public CaptureMessageWindowScene(GameView parent){
        super(parent);
        img.moveCollision(0,1024);
        clearImg=new Image(R.drawable.fd_end);
    }
    public void interrupt(MotionEvent event){
        pi.update(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //for Selecting Menu,need to check hit in menubar
                if(img.isInside(new PointF(pi.x, pi.y)) ){
                    if (statusWindow == null) {// && img.isInside(new PointF(pi.x, pi.y))){
                        statusWindow=new CaptureMenuWindow();
                        menuState=MenuState.On;
                        return;
                    } else if (statusWindow != null) {
                        //statusWindow.interrupt(event);
                        statusWindow = null;
                        menuState=MenuState.Off;
                        return;
                    }
                }
                break;
            default: break;
        }
        if(menuState==MenuState.On){
            return;
        }
        menuState=MenuState.None;
    }
    public MenuState getMenuState(){return menuState;}
    public void drawClearMessage(Canvas canvas){
        super.draw(canvas);
        clearImg.draw(canvas);
    }
    @Override public void dispose(){
        super.dispose();
        clearImg=null;
    }
}
