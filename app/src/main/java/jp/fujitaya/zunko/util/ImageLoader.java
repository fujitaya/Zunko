package jp.fujitaya.zunko.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

public class ImageLoader {
    public static ImageLoader getInstance(){
        if(instance == null) instance = new ImageLoader();
        return instance;
    }

    public void setResource(Resources res){
        this.res = res;
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
        loader.clear();
    }

    private static ImageLoader instance = null;
    private SparseArray<Bitmap> loader;
    private Resources res;
    private ImageLoader(){
        loader = new SparseArray< Bitmap>();
    }
}
