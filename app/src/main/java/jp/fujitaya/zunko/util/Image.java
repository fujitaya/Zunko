package jp.fujitaya.zunko.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class Image {
    private Bitmap bitmap;

    private boolean show;
    private RectF textureRect, drawRect;
    private PointF pos, center;
    private InsideStrategy collision;

    public Image(int resId) {
        show = true;
        collision = null;
        drawRect = new RectF();
        textureRect = new RectF();
        pos = new PointF();
        center = new PointF();
        pos.set(0, 0);
        center.set(0, 0);
        textureRect.set(0, 0, 0, 0);

        ImageLoader loader = ImageLoader.getInstance();
        bitmap = loader.load(resId);
        if (bitmap != null) {
            textureRect.right = bitmap.getWidth();
            textureRect.bottom = bitmap.getHeight();
        }
    }

    public void show(boolean show){
        this.show = show;
    }
    public void changeImage(int resId){
        bitmap = ImageLoader.getInstance().load(resId);
    }
    public void setPosition(PointF pos){
        this.pos = pos;
    }
    public void moveTo(float x, float y) {
        pos.set(x, y);
        if(collision != null) collision.move(pos.x, pos.y);
    }
    public void moveOffset(float x, float y) {
        pos.offset(x, y);
        if(collision != null) collision.move(pos.x, pos.y);
    }
    public void moveOffset2(float x,float y){
        pos.set(x,y);
        if(collision != null) collision.moveOffSet(pos.x, pos.y);
    }
    public void setScale(float sx, float sy) {
        textureRect.right = bitmap.getWidth() * sx;
        textureRect.bottom = bitmap.getHeight() * sy;
        center.x *= sx;
        center.y *= sy;
    }
    public void setCenter(){
        center.x = bitmap.getWidth()/2;
        center.y = bitmap.getHeight()/2;
    }
    public void setCenter(float x, float y){
        center.x = x;
        center.y = y;
    }

    public float getX(){
        return pos.x;
    }
    public float getY(){
        return pos.y;
    }
    public PointF getPosition(){
        return new PointF(pos.x, pos.y);
    }
    public float getCenterX(){
        return center.x;
    }
    public float getCenterY(){
        return center.y;
    }
    public PointF getCenter(){
        return new PointF(center.x, center.y);
    }
    public float getWidth(){
        return textureRect.width();
    }
    public float getHeight(){
        return textureRect.height();
    }

    public void setCollision(InsideStrategy collision) {
        this.collision = collision;
        this.collision.move(pos.x-center.x, pos.y-center.y);
    }
    public void moveCollision(float x,float y){
        this.collision.move(x,y);
    }
    public boolean isInside(PointF point){
        if(collision == null) return false;
        return collision!=null && collision.isInside(point);
    }

    public void draw(Canvas canvas){
        draw(canvas, 0, 0);
    }
    public void draw(Canvas canvas, float baseX, float baseY){
        if(show && bitmap!=null){
            drawRect.left = pos.x - center.x + baseX;
            drawRect.top = pos.y - center.y + baseY;
            drawRect.right = pos.x + textureRect.width() - center.x + baseX;
            drawRect.bottom = pos.y + textureRect.height() - center.y + baseY;
            canvas.drawBitmap(bitmap, null, drawRect, null);
        }
    }
}
