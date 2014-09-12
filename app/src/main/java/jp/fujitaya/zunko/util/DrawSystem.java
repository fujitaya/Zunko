package jp.fujitaya.zunko.util;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DrawSystem {
    private ArrayList<SpriteNode> nodes;
    private Canvas canvas;
    private SpriteNode.ZSort comparator;

    public DrawSystem(Canvas canvas){
        this.canvas = canvas;
        nodes = new ArrayList<SpriteNode>();
        comparator = new SpriteNode.ZSort();
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

    public void draw(){
        Collections.sort(nodes, comparator);

        SpriteNode node;
        Iterator iter = nodes.iterator();
        while(iter.hasNext()){
            node = (SpriteNode)iter.next();
            node.draw(canvas, 0, 0, 1, 1, 0);
        }
    }
}
