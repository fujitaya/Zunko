package jp.fujitaya.zunko.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Sound {
    public static final int MAX_SE_SIMULTANEOUS_PLAY_NUM = 5;

    public class SoundCard{
        public static final int TAG_SE = 1;
        public static final int TAG_BGM = 2;

        public int tag(){return tag;}
        public SoundCard lv(float v){leftVol = v; return this;}
        public SoundCard rv(float v){rightVol = v; return this;}
        public SoundCard pr(int v){priority = v; return this;}
        public SoundCard lp(int v){loop = v; return this;}
        public SoundCard rt(int v){rate = v; return this;}
        private SoundCard(){}

        private int tag = 0;
        private int soundId = 0;
        private int streamId = 0;       // non-zero streamID if playing was successful, zero if failed
        private float leftVol = 1f;     // left volume value (range = 0.0 to 1.0)
        private float rightVol = 1f;    // right volume value (range = 0.0 to 1.0)
        private int priority = 0;       // stream priority (0 = lowest priority)
        private int loop = 0;           // loop mode (0 = no loop, -1 = loop forever)
        private float rate = 1f;        // playback rate (1.0 = normal playback, range 0.5 to 2.0)
    }

    public static Sound getInstance(){
        if(instance == null) instance = new Sound();
        return instance;
    }
    public void setContext(Context context){
        this.context = context;
    }
    public void init(Context context){
        this.context = context;
        sp = new SoundPool(MAX_SE_SIMULTANEOUS_PLAY_NUM, AudioManager.STREAM_MUSIC, 0);
        mp = null;
    }
    public void release(){
        if(mp != null){
            mp.reset();
            mp.release();
            mp = null;
        }
        sp.release();
        sp = null;
        context = null;
    }
    public void unload(SoundCard card){
        if(card.tag==SoundCard.TAG_BGM){
            if(mp!=null && card.soundId==mp.getAudioSessionId()) {
                if (isBGMPlaying(card)) stopBGM();
                mp.release();
            }
        }else{
            sp.unload(card.soundId);
        }
    }

    public SoundCard loadBGM(int resourceId){
        SoundCard sc = new SoundCard();
        sc.tag = SoundCard.TAG_BGM;

        if(mp != null) mp.release();
        mp = MediaPlayer.create(context, resourceId);
        sc.soundId = mp.getAudioSessionId();
        sc.loop = -1;
        return sc;
    }
    public void playBGM(SoundCard card){
        if(mp == null || card.soundId != mp.getAudioSessionId()) return;

        mp.setVolume(card.leftVol, card.rightVol);
        mp.setLooping(card.loop == -1);
        mp.start();
    }
    public void pauseBGM(){
        mp.pause();
    }
    public void stopBGM(){
        mp.stop();
        try{
            mp.prepare();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean isBGMPlaying(SoundCard card){
        return mp!=null &&
                card.soundId==mp.getAudioSessionId() &&
                mp.isPlaying();
    }

    public SoundCard loadSE(int resourceId){
        SoundCard sc = new SoundCard();
        sc.tag = SoundCard.TAG_SE;

        sc.soundId = sp.load(context, resourceId, 0);
        sc.loop = 0;

        return sc;
    }
    public SoundCard playSE(SoundCard card){
        card.streamId = sp.play(card.soundId, card.leftVol, card.rightVol,
                card.priority, card.loop, card.rate);
        return card;
    }
    public SoundCard stopSE(SoundCard card){
        sp.stop(card.streamId);
        card.streamId = 0;
        return card;
    }

    private Context context;
    private SoundPool sp;
    private MediaPlayer mp;

    private static Sound instance = null;
    private Sound(){}
}
