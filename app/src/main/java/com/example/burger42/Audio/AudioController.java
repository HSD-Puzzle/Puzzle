package com.example.burger42.Audio;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.example.burger42.R;

public class AudioController {

    private static final String PREFERENCE_NAME = "MyPrefs";
    private static final String AUDIO_PROGRESS_KEY = "audioProgress";

    private SharedPreferences preferences;

    private int volume;

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
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        mediaPlayer = MediaPlayer.create(context, R.raw.morningfunnybeat);
        volume = preferences.getInt(AUDIO_PROGRESS_KEY, 0);
        leftAndRightVolume(volume);
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
     * @param volume
     */
    public void leftAndRightVolume(int volume){
        this.volume = volume;
        mediaPlayer.setVolume(volume/100f,volume/100f);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AUDIO_PROGRESS_KEY, volume);
        editor.apply();
    }

    public int volume(){
        return volume;
    }



}
