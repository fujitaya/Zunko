package jp.fujitaya.zunko.util;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SpriteNode{
    protected int x, y, z;
    protected int center_x, center_y;
    protected float scale_x, scale_y;
    protected boolean showFlag;

    protected SpriteNode parent;
    protected ArrayList<SpriteNode> children;
//    static ArrayList<ArrayList<SpriteNode>> _s_sort_buffer;

    public SpriteNode(){
        x = y = z = 0;
        center_x = center_y = 0;
        scale_x = scale_y = 1;
        showFlag = true;
        parent = null;
        children = new ArrayList<SpriteNode>();
    }

    public void show(boolean flag){showFlag = flag;}
    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void moveOffset(int x, int y){
        this.x += x;
        this.y += y;
    }
    public void setCenter(int x, int y){
        center_x = x;
        center_y = y;
    }
    public void setScale(float x, float y){
        scale_x = x;
        scale_y = y;
    }
    public void setZ(int z){
        this.z = z;
    }
    public int getX(){return x;}
    public int getY(){return y;}

    public void addChild(SpriteNode node){
        node.parent = this;
        children.add(node);
    }
    public boolean removeChild(SpriteNode node){
        return children.remove(node);
    }
    public boolean removeChild(int index){
        if(index < 0 || index >= children.size()) return false;
        children.remove(index);
        return true;
    }
    public boolean removeFromParent(){
        if(parent == null) return true;
        return parent.removeChild(this);
    }

    public int translateXbase(float base, float scale){
        return (int)(base + (float)this.x*this.scale_x*scale);
    }
    public int translateYbase(float base, float scale){
        return (int)(base + (float)this.y*this.scale_y*scale);
    }

    static public class ZSort implements Comparator<SpriteNode> {
        public int compare(SpriteNode a, SpriteNode b){
            if(a.z == b.z) return -1;
            else return a.z - b.z;
        }
    }

    public void draw(Canvas canvas, float xbase, float ybase, float xscale, float yscale, int level){
        if(!showFlag) return;
        if(children.size() == 0){
            drawThis(canvas, xbase, ybase, xscale, yscale);
        }else{
            SpriteNode mynode = new SpriteNode();
            mynode.setZ(0);

            SpriteNode[] buf = new SpriteNode[children.size()+1];
            buf[children.size()] = mynode;
            Arrays.sort(buf, new ZSort());

            for(int i=0; i < children.size()+1; ++i){
                SpriteNode node = buf[i];
                if(node.equals(mynode)) drawThis(canvas, xbase, ybase, xscale, yscale);
                else node.draw(canvas, xbase+x*xscale, ybase+y*yscale, xscale*scale_x, yscale*scale_y, level+1);
            }
        }
    }

    protected void drawThis(Canvas canvas, float xbase, float ybase, float xbasescale, float ybasescale){}
}