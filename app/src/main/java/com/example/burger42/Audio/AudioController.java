package com.example.burger42.Audio;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.burger42.R;

public class AudioController {

    MediaPlayer mediaPlayer;
    Context context;
    boolean musicIsPlaying;
    private static AudioController INSTANCE;

    /**
     *
     * @param context
     */
    public AudioController(Context context){
        this.context = context;
    }

    /**
     *
     * @param context
     * @return
     */
    public static AudioController getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = new AudioController(context);
        }
        return INSTANCE;
    }

    /**
     *
     * @return
     */
    public boolean musicIsPlaying(){
        return musicIsPlaying;
    }

    /**
     *
     */
    public void startMusic(){
        mediaPlayer = MediaPlayer.create(context, R.raw.morningfunnybeat);
        mediaPlayer.start();
        musicIsPlaying = true;
        mediaPlayer.setLooping(true);
    }

    /**
     *
     */
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            musicIsPlaying = false;
        }
    }

    /**
     *
     * @param leftVolume
     * @param rightVolume
     */
    public void leftAndRightVolume(int leftVolume,  int rightVolume){
        mediaPlayer.setVolume(leftVolume/100f,rightVolume/100f);
    }

}
