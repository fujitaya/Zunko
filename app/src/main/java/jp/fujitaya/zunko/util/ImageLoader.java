package jp.fujitaya.zunko.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    public static ImageLoader getInstance(){
        if(instance == null) instance = new ImageLoader();
        return instance;
    }

    public void init(Resources res){
        this.res = res;
        loader = new SparseArray< Bitmap>();
//        loader = new HashMap<Integer, Bitmap>();
    }
    public Bitmap load(int resId){
        Bitmap b = loader.get(resId);
        if(b == null){
            b = BitmapFactory.decodeResource(res, resId);
            loader.put(resId, b);
        }
        return b;
    }
    public void recycle(int resId){
        Bitmap b = loader.get(resId);
        if(b != null){
            b.recycle();
            loader.remove(resId);
        }
    }
    public void release(){
        for(int i=0; i < loader.size(); ++i){
            loader.valueAt(i).recycle();
        }
//        for(Map.Entry<Integer, Bitmap> e : loader.entrySet()){
//            e.getValue().recycle();
//        }
        loader.clear();
        loader = null;
        res = null;
    }

    private static ImageLoader instance = null;
//    private HashMap<Integer, Bitmap> loader;
    private SparseArray<Bitmap> loader;
    private Resources res;
    private ImageLoader(){}
}
