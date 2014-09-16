package jp.fujitaya.zunko.util;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DrawSystem {
    private ArrayList<SpriteNode> nodes;
    private SpriteNode.ZSort comparator;

    private int width, height;
    private float scale;

    public DrawSystem(){
        nodes = new ArrayList<SpriteNode>();
        comparator = new SpriteNode.ZSort();

        width = 720;
        height = 1280;
    }

    public void setScreenSize(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void add(SpriteNode node){
        nodes.add(node);
    }
    public SpriteNode remove(int index){
        return nodes.remove(index);
    }
    public boolean remove(SpriteNode node){
        return nodes.remove(node);
    }

    public void draw(Canvas canvas){
        float scaleX = (float)canvas.getWidth() / (float)width;
        float scaleY = (float)canvas.getHeight() /  (float)height;
        float scale = scaleX > scaleY ? scaleY : scaleX;

        Collections.sort(nodes, comparator);

        SpriteNode node;
        Iterator iter = nodes.iterator();
        while(iter.hasNext()){
            node = (SpriteNode)iter.next();
            node.draw(canvas, 0, 0, scale, scale, 0);
        }
    }
}
